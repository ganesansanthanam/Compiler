# Compiler
The lexer has been created for converting the program into tokens and the lexer is validated by numerous tests cases for accuracy of production rules accepted by the language. The parser that goes through tokens, checking for syntax error. Then AST will create a nodes for each type of statement and declaration. The type checker will go through the tree to set and check types,then the Code generator will generate byte code for the program. 
