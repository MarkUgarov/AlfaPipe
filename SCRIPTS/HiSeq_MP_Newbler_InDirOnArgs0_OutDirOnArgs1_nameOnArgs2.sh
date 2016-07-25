#!/bin/bash
# getting multiple files parsed from HiSeq format to a  format compatible with Newbler

INDIR="$1"
OUTDIR="$2"
NAME="$3"
INFILES=$(find $INDIR -name "*R1*.fastq")

echo "Start with input directory $INDIR and output directory $OUTDIR"

if [ ! -d "$OUTDIR" ]; then
  mkdir "$OUTDIR"
fi

counter=1;
outfile=.
for f in $INFILES 
do 
	echo "Input file:" $f
	outfile=$OUTDIR"/"$NAME"_"$counter".fastq"
	echo "Output file: " $outfile 
 	/vol/ampipe/data/SCRIPTS/HiSeqFASTQ4Newbler_v2.8_inFileOnArgs0_outFileOnArgs1.pl "$f" "$outfile"
	let counter=$counter+1
done		





