#!/bin/bash
# gzip multiple files to a directory

ARCHIVE="$1"
OUTDIR="$2"

if [ ! -d "$OUTDIR" ]; then
  mkdir "$OUTDIR"
fi

unzip -K -d "$OUTDIR" "$ARCHIVE"
