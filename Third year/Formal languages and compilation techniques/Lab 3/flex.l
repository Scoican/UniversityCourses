%{
#include "myscanner.h"
%}
%%
"int" 			return INT;
"using"			return USING;
"namespace"		return NAMESPACE;
"std"			return STD;
";" 			return SEMICOLON;
"=" 			return EQUAL;
"," 			return COMMA;
"==" 			return EQUALITY;
"return" 		return RETURN;
"(" 			return OPEN_R_PARANTHESIS;
")" 			return CLOSED_R_PARANTHESIS;
"if" 			return IF;
"while" 		return WHILE;
"else" 			return ELSE;
"{" 			return OPEN_B_PARANTHESIS;
"cout<<" 		return COUT;
"cin>>" 		return CIN;
"<" 			return LESSTHAN;
"!=" 			return DIFFERENT;
"main" 			return MAIN;
"&" 			return ADDRESS;
"#include" 		return INCLUDE;
"<iostream>" 		return IOSTREAM_DLL;
"+" 			return PLUS;
"-" 			return MINUS;
"}" 			return CLOSE_B_PARANTHESIS;
"typedef" 		return TYPEDEF;
"struct" 		return STRUCT;
"float" 		return FLOAT;
"." 			return DOT;
"*" 			return MULTIPLY;
[a-zA-Z]+[_a-zA-Z0-9]{0,7} 	return ID;
[1-9][0-9]*		return CONST;
[0-9]			return CONST;
[1-9]*\.[0-9]		return CONST;
\".*\"			return CONST;
[ \t\n]			return EMPTY;
.			printf("");
%%

int yywrap(void)
{
	return 1;
}
