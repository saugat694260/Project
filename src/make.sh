#!/bin/bash
readonly BIN_DIR="/home/questofjoy/Desktop/Project/bin"

# Create bin directory if it doesn't exist
mkdir -p "$BIN_DIR"

# Find all Java files and write to sources.txt
find . -name "*.java" >sources.txt

# Compile with error messages
if ! javac -d "$BIN_DIR" @sources.txt 2>&1; then
  echo -e "\033[31mCompilation failed with the above errors\033[0m"
  rm sources.txt
  exit 1
fi

# Only run if compilation succeeded
java -cp "$BIN_DIR" Main

# Clean up
rm sources.txt
