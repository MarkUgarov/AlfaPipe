#!/bin/bash


COMMAND=${@:1:$(($# - 1))}
OUTFILE=${@:${#@}}

echo "File is ${OUTFILE}"
echo "Command is ${COMMAND}"


$COMMAND>$OUTFILE
