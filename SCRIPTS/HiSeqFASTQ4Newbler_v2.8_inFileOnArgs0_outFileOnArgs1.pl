#!/usr/bin/env perl
die "Usage: <detPairs FASTQ file> <output FASTQ file> \n" unless (defined($ARGV[0]) && defined($ARGV[1])); 

my $ffile = $ARGV[0];
my $ofile = $ARGV[1];




die "Fwd file $ffile is not properly named (*_L001_R1_001.fastq)\n"
    unless ($ffile =~ m/_L00\d_R1_001.fastq$/);

die "Fwd file $ffile not readable or does not exist: $!\n" 
    unless (-e $ffile && -r $ffile);

my $rfile = $ffile;

$rfile =~ s/_R1_001.fastq$/_R2_001.fastq/;

die "Rev file $rfile not readable or does not exist: $!\n" 
    unless (-e $rfile && -r $rfile);

die "File $ofile exists!\n" if (-e $ofile); 

open OUT, ">$ofile" || die "Cannot open output file $ofile: $!\n";

for my $ifile ($ffile, $rfile) { 

    open(FILE, $ifile) || die "Error: Unable to open file: $ifile\n";


    while (<FILE>) {
	chomp;

	my ($id, $header) = (m/([@>])(.+)/);
	
	die "Something is amiss at line $_\n" unless ($id eq '>' || $id eq '@'); 
	
	my ($template, $dir) = ($header =~ /(\w+:\d+:\d+:\d+:\d+) ([\d{1}]):/);
	my $new_head;
	
	$template =~ s/:/_/g;
	$new_head = "$id$template/$dir";
#	$new_head = "$id$template\_$dir";
	
	die "Something is amiss at line $_\n" unless ($dir); 
	
	my $seq = (<FILE>);
	chomp $seq;
	
	print OUT "$new_head\n$seq\n";
	
	next if ($id eq '>');
	my $void = (<FILE>);
	
	my $qual = (<FILE>);
	chomp $qual;
	print OUT "+\n$qual\n";
    }
}
