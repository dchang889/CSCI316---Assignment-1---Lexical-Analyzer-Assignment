# CSCI316---Assignment-1---Lexical-Analyzer-Assignment
Implementation of a lexical analyzer for the Ada Language Subset 1 (ADALS1) language.

Description: 
The program takes in a "input.txt" file and reads line by line identifying all the lexical units. 
It prints each lexical unit onto a the file, "outputfile1.txt," for the user.
Each line is treated as a single string with each character read one at a time.
Each character is read in until a piece of the string is identified as a lexical unit.
These lexical units are Identifier, Number, and Symbol.
After a lexical unit is identified, the program will continue to identify the remaining lexical units on the line.
Once the program reaches the end of the line, it repeats its functions on the next line.
The program stops reading the input.txt file after identifying the lexical unit, End of Input.
