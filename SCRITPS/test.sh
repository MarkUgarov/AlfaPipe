#!/bin/bash
# gzip multiple files to a directory

FILE=$"/vol/ampipe/data/testDir/testTime,txt"
LOCATION=$(pwd)
TIME=$(date +"%T")
MORE=$@

echo "Start with"

echo "File is ${FILE}" 
echo "Time is ${TIME}"
echo "Location is ${LOCATION}"
echo "More is ${MORE}"

echo $TIME >> $FILE
echo $LOCATION >> $FILE
echo $MORE >> $FILE
