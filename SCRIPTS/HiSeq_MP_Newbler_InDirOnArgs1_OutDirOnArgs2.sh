#!/bin/bash
# gzip multiple files to a directory

INDIR="$1"
OUTDIR="$3"

echo "Start with"

echo "File 1 as ${FILE}" 
echo "File 2 as ${REVFILE}"
echo "Output directory as ${OUTDIR}"

if [ ! -d "$OUTDIR" ]; then
  mkdir "$OUTDIR"
fi


