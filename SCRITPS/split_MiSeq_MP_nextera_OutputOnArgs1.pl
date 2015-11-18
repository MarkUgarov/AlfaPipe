#!/usr/bin/perl
use strict;
use lib "/vol/assembly454/bin/";
use Statistics::Descriptive;



my $file = $ARGV[0];

die "File $file is not properly named (*.fastq)\n"
    unless ($file =~ m/.fastq$/);

die "File $file not readable or does not exist: $!\n" 
    unless (-e $file && -r $file);

my $ofile = $file;
my $efile = $file;

$ofile = $ARGV[1];
$efile =~ s/.fastq$/_nohit.fastq/;

open OUT, ">$ofile" || die "Cannot open output file $ofile: $!\n";
open NOHIT, ">$efile" || die "Cannot open output file $efile: $!\n";

my %stats;

open(FILE, $file) || die "Error: Unable to open file: $file\n";

my @freads;
my @rreads;

while (<FILE>) {
    chomp;

    my ($header) = (m/@(.+)/);

    $header =~ s/MISEQ:\d+:000000000-(\S+)/MP_$1/;
    
    die "Something is amiss at line $_\n" unless ($header); 
    
    my $seq = (<FILE>);
    chomp $seq;
	
    $seq = uc($seq);
	
    my $qual;

    my $void = (<FILE>);
	
    $qual = (<FILE>);
    chomp $qual;

    my ($fseq, $rseq) = ($seq =~ m/(.+)CTGTCTCTTATACACATCTAGATGTGTATAAGAGACAG(.+)/);

    next if ($seq =~ m/-/);


    unless ($fseq) {

#	next if ($seq =~ m/ATAACTTCGTATA(?:GCATACAT|ATGTATGC)TATACGAAGTTAT/);

	($fseq, $rseq) = ($seq =~ m/(.+)CTGTCTCTTATACACATCT(.+)/);
	
	if ($fseq) { 
	    $rseq = substr($rseq, 19);
	} else {
	    ($fseq, $rseq) = ($seq =~ m/(.+)AGATGTGTATAAGAGACAG(.+)/);   
	    if ($fseq) { 
		substr($fseq, -19) = '';
	    }
	}

	unless ($fseq || $rseq) { 
	    $stats{no_hit}++;
	    print NOHIT "@", "$header\n$seq\n+\n$qual\n";

	    next;
	}
	$stats{imperfect}++;
    }

    my $flength = length $fseq;
    my $rlength = length $rseq;


    my $fflag;
    my $rflag;

    if ($flength > 30) {
	if ($rlength > 30) {
	    $stats{both}++;
	    $fflag = 1;
	    $rflag = 1;
	} else {
	    $stats{fwd_only}++;
	    $fflag = 1;
	}
    } else {
	if ($rlength > 30) {
	    $stats{rev_only}++;
	    $rflag = 1;
	} else {
	    $stats{too_short}++;
	    next;
	}
    }
    
    my $fqual = substr($qual, 0, $flength);
    my $rqual = substr($qual, ($flength + 38));
    
    $fseq = reverse $fseq;
    $fseq =~ tr/ACGT/TGCA/;
    $fqual = reverse $fqual;

#    my $fread = "@" . "$header" . "_1 template=$header dir=f library=longlib\n$fseq\n+\n$fqual";
#    my $rread = "@" . "$header" . "_2 template=$header dir=r library=longlib\n$rseq\n+\n$rqual";

#    push(@freads, $fread);
#    push(@rreads, $rread);

    if ($flength == (length $fqual)) {
	print OUT "@", "$header", "/1\n$fseq\n+\n$fqual\n" if ($fflag); 
    }
    if ($rlength == (length $rqual)) {
	print OUT "@", "$header", "/2\n$rseq\n+\n$rqual\n" if ($rflag); 
    }
}

#foreach my $read (@freads, @rreads) {
#    print OUT "$read\n";
#}

print STDERR "Both: $stats{both}; Fwd: $stats{fwd_only}; Rev: $stats{rev_only}; Short: $stats{too_short}; Imperfect: $stats{imperfect}; No hit: $stats{no_hit}\n";


   
