#!/usr/bin/env perl
# small script to extract the header information of 454 assemblies
# end prepare them for visualization in EXCEL
# INPUT: a 454[All|Large]Contigs.fna file
# OUTPUT: print to STDOUT

##### Modules used #####
# always be strict!
use strict;
use lib "/vol/biotools/share/scripts/Statistics/";
use Statistics::Descriptive;
use Getopt::Std;

#unshift(@INC, '/vol/assembly454/bin/');


sub usage {
    print "extract_header_info - extracts the FASTA headers from \n";
    print "                      454[All|Large]Contigs.fna files\n";
    print "                      and prints the data <TAB> separated\n";
    print "                      to STDOUT\n";
    print "                      -E prepare German EXCEL output\n";
    print "usage: 'extract_header_info [-E] (PATH/)454[All|Large]Contigs.fna |tee OUTPUT.txt'\n\n";
}



our($opt_E, $opt_h);

getopts('Eh');

exit &usage unless ($ARGV[0]); 
exit &usage if ($opt_h); 

# open the FASTA file, get the data and do some sanity checks.
my $file = $ARGV[0];
die "Wrong file $file!\nOnly 454[All|Large]Contigs.fna files are accepted\n" 
    unless ($file =~ /454(?:All|Large)Contigs.fna/);

open FILE, $file || die "Cannot open file $file: $!";
my @content = (<FILE>);
chomp @content;
close FILE;


die "Start of file does not match FASTA format\n" 
    unless ($content[0] =~ /^>contig\w+/);


# grep all headers
my @headers = grep(/^>contig/, @content);
die "No headers!\n" unless ($#headers > 1);

my %data;
my @median_data;
foreach my $line (@headers) {
    my ($contig, $length, $numReads) = ($line =~ /^>(contig\S+)\s+length=\s*(\w+)\s+numreads=\s*(\w+)$/);

    # calculate the fraction and store it in an array for median calculation
    # if the contig is at least 10 kB long
    my $fraction = $numReads / $length;
    push (@median_data, $fraction) if ($length >= 10000);

    # store the data for output
    $data{$contig} = [$length, $numReads, $fraction,
    ];
}

my $contig;
my %bases;
foreach my $line (@content) {
    if ($line =~ /^>(.+?)\s+/) {
	if (%bases) {
	    my $total; 
	    foreach my $base ('A', 'C', 'G', 'T') {
		$total += $bases{$base};
	    }
	    my $gc = $bases{'G'} + $bases{'C'};
	    $total = 1 unless ($total);
	    my $gc_content = sprintf('%2.2f', ($gc * 100 / $total));
	    
	    push (@{$data{$contig}}, $gc_content);
	}
	
	undef %bases;
	$contig = $1; 
    } else {
	
	foreach my $base ('A', 'C', 'G', 'T', 'N') {
	    $bases{$base} += ($line =~ s/$base//g);
	}
    }
}

my $total; 
foreach my $base ('A', 'C', 'G', 'T') {
    $total += $bases{$base};
}
my $gc = $bases{'G'} + $bases{'C'};
my $gc_content = sprintf('%2.2f', ($gc * 100 / $total));

push (@{$data{$contig}}, $gc_content);


my $stat = Statistics::Descriptive::Full->new();
$stat->add_data(@median_data); 
my $median = $stat->median();


foreach my $contig (sort(keys(%data))) {
    my ($length, $numReads, $fraction, $gc) = @{$data{$contig}};

    my $ratio = eval{$fraction / $median};

    my $outlier_flag = '';
    $outlier_flag = 'o' if ($ratio > 1.5);
    $outlier_flag = 'u' if ($ratio < 0.666);

    $fraction = sprintf('%.3f', $fraction);
    $ratio    = sprintf('%.3f', $ratio);

    if ($opt_E){
	s/\./,/ foreach ($fraction, $ratio, $gc);
    }
 
    print "$contig\t$length\t$numReads\t$fraction\t$ratio\t$gc\t$outlier_flag\n";
    
}

$median = sprintf('%.4f', $median);
if ($opt_E) {
    s/\./,/ foreach ($median);
    print "\nMedian: $median\n";
}

