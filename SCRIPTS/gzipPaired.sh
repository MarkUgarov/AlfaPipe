#!/bin/bash
# gzip multiple files to a directory

FILE="$1"
REVFILE="$2"
OUTDIR="$3"

echo "Start with"

echo "File 1 as ${FILE}" 
echo "File 2 as ${REVFILE}"
echo "Output directory as ${OUTDIR}"

if [ ! -d "$OUTDIR" ]; then
  mkdir "$OUTDIR"
fi

gzip -dck "${FILE}" > "${OUTDIR}/Unziped_R1_001.fastq"
gzip -dck "${REVFILE}" > "${OUTDIR}/Unziped_R2_001.fastq"





