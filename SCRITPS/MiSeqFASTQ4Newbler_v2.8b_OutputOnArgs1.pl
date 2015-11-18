#!/usr/bin/perl
use Getopt::Std;

our($opt_f, $opt_h);

getopts('f:h');


die "Usage: detPairs FASTQ file\n" unless defined($ARGV[0]); 

my $ffile = $ARGV[0];

die "Fwd file $ffile is not properly named (*_R1_001.fastq)\n"
    unless ($ffile =~ m/.+_R1.+fastq$/);

die "Fwd file $ffile not readable or does not exist: $!\n" 
    unless (-e $ffile && -r $ffile);

my $rfile = $ffile;

$rfile =~ s/(.+)_R1(.+)$/$1_R2$2/;

die "Rev file $rfile not readable or does not exist: $!\n" 
    unless (-e $rfile && -r $rfile);

my $ofile = $ffile;

$ofile = $ARGV[1];

die "File $ofile exists!\n" if (-e $ofile); 

open OUT, ">$ofile" || die "Cannot open output file $ofile: $!\n";


open (FFILE, $ffile) || die "Error: Unable to open fwd file: $ffile\n";  
open (RFILE, $rfile) || die "Error: Unable to open rev file: $rfile\n";  

my $trc;
my $cfb;
my $crb;
my $rnbf;
my $rnbr;
my $ncrf;
my $ncrr;


while (<FFILE>) {
    chomp;
    $trc++;
    my ($header) = (m/^@(.+)/);
	
    die "Something is amiss at line $_\n" unless ($header); 
	
    my ($run,$flid,$pos, $dir) = ($header =~ /^\w+:(\d+)\S+-(\w+):(\S+) ([\d{1}]):/);
    
    $pos =~ s/:/_/g;
    my $new_head = '@'."$flid\_$run\_$pos/$dir";
	
    die "Something is amiss at line $_\n" unless ($dir); 

    my $seq = (<FFILE>);
    chomp $seq;

    my $void = (<FFILE>);
    
    my $qual = (<FFILE>);
    chomp $qual;

    my ($Nseq) = ($seq =~ m/(N+)$/); 
    my ($lenNseq) = length $Nseq;
    $seq  = substr ($seq,  0, (length($seq)-$lenNseq));
    $qual = substr ($qual, 0, (length($qual)-$lenNseq));

    if ($lenNseq > 0) {
	$rnbf += $lenNseq; 
	$ncrf++;
    }

    if ($opt_f) {
	my $lbq  = &get_lbq($qual);
	
	while (($lbq < $opt_f) && $seq) { 
	    chop $seq;
	    $cfb++;
	    chop $qual;
	    $lbq  = &get_lbq($qual);
	}
    }

    if ((length $seq) > 50) { 
	print OUT "$new_head\n$seq\n+\n$qual\n";
    }

    my $line = (<RFILE>);
    chomp $line ;
    ($header) = ($line =~ m/^@(.+)/);
	
    die "Something is amiss at line $_\n" unless ($header); 
	
    ($run,$flid,$pos, $dir) = ($header =~ /^\w+:(\d+)\S+-(\w+):(\S+) ([\d{1}]):/);
    $new_head;
    
    $pos =~ s/:/_/g;
    $new_head = '@'."$flid\_$run\_$pos/$dir";
	
    die "Something is amiss at line $_\n" unless ($dir); 
	
    $seq = (<RFILE>);
    chomp $seq;

    $void = (<RFILE>);
    
    $qual = (<RFILE>);
    chomp $qual;

    ($Nseq) = ($seq =~ m/(N+)$/); 
    ($lenNseq) = length $Nseq;
    $seq  = substr ($seq,  0, (length($seq)-$lenNseq));
    $qual = substr ($qual, 0, (length($qual)-$lenNseq));

    if ($lenNseq > 0) {
	$rnbr += $lenNseq; 
	$ncrr++;
    }

    if ($opt_f) {
	my $lbq  = &get_lbq($qual);
	
	while (($lbq < $opt_f) && $seq) { 
	    chop $seq;
	    $crb++;
	    chop $qual;
	    $lbq  = &get_lbq($qual);
	}
    }

    if ((length $seq) > 50) { 
	print OUT "$new_head\n$seq\n+\n$qual\n";
    }
}

my $bcpfr = sprintf('%1.1f', ($rnbf/$trc));
my $bcprr = sprintf('%1.1f', ($rnbr/$trc));
print STDERR "Removed $rnbf Ns from $ncrf fwd reads, $rnbr Ns from $ncrr rev reads\n";
print STDERR "For $trc reads, this makes $bcpfr/$bcprr per fwd/rev read\n";


if ($opt_f) {
    my $bcpfr = sprintf('%1.1f', ($cfb/$trc));
    my $bcprr = sprintf('%1.1f', ($crb/$trc));
    print STDERR "Cut $cfb from fwd reads, $crb from rev reads\n";
    print STDERR "For $trc reads, this makes $bcpfr/$bcprr per fwd/rev read\n";
}


sub get_lbq {
    my $qual = @_[0];

    my $lbq = (unpack 'c', (substr($qual, -1, 1))) - 33;

    for (my $i = 2; $i < 6; $i++) {
	last if ($lbq < $opt_f);
	my $nbq = (unpack 'c', (substr($qual, -$i, 1))) - 33;
	$lbq = $nbq if ($lbq > $nbq);
    }
    return $lbq;

}
