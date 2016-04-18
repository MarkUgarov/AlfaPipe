#!/usr/bin/env perl
# A simple script to extract all the statistics data needed for
# a quick evaluation of the assembled data 

use strict;
use Getopt::Std;

our($opt_l);

getopts('l');

my $path = $ARGV[0];
$path = '.' unless $path;


die "Invalid path $path!\n" unless (-e "$path/454NewblerMetrics.txt");

# Parse the NewblerMetrics data
open NEWBLER, "$path/454NewblerMetrics.txt" 
    || die "Cannot open NewblerMetrics file $path/454NewblerMetrics.txt!\n";
my @NewblerData = (<NEWBLER>);
chomp @NewblerData;
close NEWBLER;

# Throw away everything until the Consensus Results are reached 
die "Incomplete record. Is the assembly still in progress?\n" unless (grep(/consensusResults/, @NewblerData));
$_ = shift (@NewblerData) until ($_ =~ m/consensusResults/);

my %data;
foreach my $datum ('numAlignedReads',
		   'numAlignedBases',
		   'numberWithBothMapped',
		   'numberOfScaffolds',
		   'numberOfBases',
		   'avgScaffoldSize',
		   'N50ScaffoldSize',
		   'AverageScaffoldedContigSize',
		   'largestScaffoldSize',
		   'avgContigSize',
		   'N50ContigSize',
		   'largestContigSize') {
    
    my $entry = (grep(/$datum/, @NewblerData))[0];
    ($entry)  = ($entry =~ /$datum\s+=\s+(\d+)/);
    $data{$datum} = $entry;
}

my (@NumContigs)=(grep(/numberOfContigs/, @NewblerData))[0,1];
($data{NumLargeContigs}) = ($NumContigs[0] =~ /=\s+(\d+)/);
($data{NumAllContigs})   = ($NumContigs[1] =~ /=\s+(\d+)/);

# Calculate the coverage
my $Coverage = sprintf('%2.2f',($data{numAlignedBases}/$data{numberOfBases}));
my $fasta_file = "454AllContigs.fna";
my $PE_flag;

# Calculate the mean PE distance
my (@PDLibs)=(grep(/airDistance/, @NewblerData));

if ($#PDLibs >= 0) {
    
    $fasta_file = "454Scaffolds.fna";
    $PE_flag = 1;
    
    foreach my $entry (@PDLibs) {
	my($field, $value) = ($entry =~ /([P|p]air\w+)\s+=\s+(.+);/);
	$field =~ s/pair/Pair/;
	next if ($field eq 'PairDistanceRangeUsed');
	$value = sprintf('%0.0f', $value);
	my $length = length($value);

	$value = reverse($value);
	for (my $i = 3; $i < $length; $i +=4) {
	    substr($value, $i, 0) = ','; 
	    $length++;
	}
	$value = reverse($value);
	push(@{$data{$field}}, $value);
    }

    for (my $i = 0; $data{PairDistanceAvg}[$i]; $i++) {
	push (@{$data{PairDistanceStats}},  "$data{PairDistanceAvg}[$i] +/- $data{PairDistanceDev}[$i]");
	if ($data{PairDistanceAvg}[$i] > $data{PairDistanceMaxAvg}) {
	    $data{PairDistanceMaxAvg} = $data{PairDistanceAvg}[$i];
	    $data{PairDistanceMaxDev} = $data{PairDistanceDev}[$i];
	}
    }
	 
# Now parse the Scaffold data
    open SCAFFOLD, "$path/454Scaffolds.txt" 
	|| die "Cannot open Scaffold file $path/454Scaffolds.txt!\n";
    my @ScaffoldData = (<SCAFFOLD>);
    chomp @ScaffoldData;
    close SCAFFOLD;
    
    my %scaffolds;
    my $numContigs;
    my $lengthAllContigs;
    foreach my $line (@ScaffoldData) {
	my ($scaffold, $s_stop, $num, $flag, $lengthContig) = (split(/\s+/, $line))[0,2,3,4,7];
	($scaffold) = ($scaffold =~ m/scaffold(\d{5})/);
	if ($flag eq 'W') {
	    $scaffolds{$scaffold}{length}  = $s_stop;
	    $scaffolds{$scaffold}{num}     = ($num + 1) / 2;
	    $lengthAllContigs += $lengthContig;
	    $numContigs++;
	}
    }
# Identify "true" scaffolds (more than one Contig or longer then pairDistanceAvg + Dev)
    foreach my $scaffold (keys(%scaffolds)) {
	if ($scaffolds{$scaffold}{num} > 1 
	    || $scaffolds{$scaffold}{length} > ($data{PairDistanceMaxAvg} + $data{PairDistanceMaxDev})) {
	    $data{NumTrueScaffolds}++;
	    $data{NumScaffoldContigs} += $scaffolds{$scaffold}{num};
	} 
    }
    $data{AverageScaffoldedContigSize} = int($lengthAllContigs/$numContigs);
}
# Insert 1,000 blocks
foreach my $key (keys(%data)) {
    my $datum = $data{$key};
    next if ($key =~ m/PairDistance/);
    my $length = length($datum);

    $datum = reverse($datum);
    for (my $i = 3; $i < $length; $i +=4) {
	substr($datum, $i, 0) = ','; 
	$length++;
    }
    $data{$key} = reverse($datum);
}


# Now parse the fasta data to calculate the GC content
open FASTA, "$path/$fasta_file" 
    || die "Cannot open FASTA file $path/$fasta_file!\n";
my ($A, $C, $G, $T);
while (<FASTA>) {
    next if (/^>/);
    $A += s/A//gi;
    $C += s/C//gi;
    $G += s/G//gi;
    $T += s/T//gi;
}
my $GC    = sprintf('%2.2f', (($C+$G) / ($A + $C + $G + $T) * 100));

unless ($opt_l) {
    print "Aligned Reads";
    print " (All/PE)" if $PE_flag;
    print ":\t$data{numAlignedReads}";
    print "/$data{numberWithBothMapped}" if $PE_flag;
    print "\n";
    print "Assembled Bases:\t$data{numAlignedBases}\n";
    if ($PE_flag) { 
	my $libs = join('; ', @{$data{PairDistanceStats}});
	print "PE-Size(s):\t$libs\n";
    }
    print "\n";
    print "Scaffolds (All/True):\t$data{numberOfScaffolds}/$data{NumTrueScaffolds}\n" if $PE_flag;
    print "Contigs (";
    print "S/" if $PE_flag;
    print "L/A):\t";
    print "$data{NumScaffoldContigs}/" if $PE_flag;
    print "$data{NumLargeContigs}/$data{NumAllContigs}\n",
          "Bases in ";
    print "LContigs" unless $PE_flag;
    print "Scaffolds" if $PE_flag;
    print ":\t$data{numberOfBases}\n",
          "Coverage:\t$Coverage\n",
          "GC content (%):\t$GC\n",
          "\n";
    print "Avg. Scaffold:\t$data{avgScaffoldSize}\n",
          "N50 Scaffold:\t$data{N50ScaffoldSize}\n",
          "Largest Scaffold:\t$data{largestScaffoldSize}\n",
          "Avg. Scaf. Contig:\t$data{AverageScaffoldedContigSize}\n",
          "\n" if $PE_flag;
    print "Avg. Contig:\t$data{avgContigSize}\n",
          "N50 Contig:\t$data{N50ContigSize}\n",
          "Largest Contig:\t$data{largestContigSize}\n";
} else {
    print "$data{numAlignedReads}";
    print "/$data{numberWithBothMapped}" if $PE_flag;
    print "\t$data{numAlignedBases}";
    print "\t$data{pairDistanceAvg}";
    print " +/- $data{pairDistanceDev}" if $PE_flag;
    print "\t$data{numberOfScaffolds}";
    print "/$data{NumTrueScaffolds}" if $PE_flag;
    print "\t";
    print "$data{NumScaffoldContigs}/" if $PE_flag;
    print "$data{NumLargeContigs}/$data{NumAllContigs}";
    print "\t$data{numberOfBases}",
          "\t$Coverage",
          "\t$GC",
          "\t$data{avgScaffoldSize}",
          "\t$data{N50ScaffoldSize}",
          "\t$data{largestScaffoldSize}",
          "\t$data{avgContigSize}",
          "\t$data{N50ContigSize}",
          "\t$data{largestContigSize}\n";
}
    
    
