#!/bin/bash


COMMAND=$@

echo "Command is ${COMMAND}"

xterm  -e "${COMMAND}"
