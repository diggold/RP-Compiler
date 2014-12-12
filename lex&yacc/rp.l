 /*--------------------------------------------------------------------------SEZIONE 1: DICHIARAZIONI*/
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "y.tab.h"

void yyerror(char*);

%}

LETTERA	[A-Za-z]
CIFRA	[0-9]

 /*stato lessicale "in commento"*/
%x IN_COMMENT

 /*------------------------------------------------------------------------------------FINE SEZIONE 1*/
%%
 /*-------------------------------------------------------------------SEZIONE 2: REGOLE DI TRADUZIONE*/

 /*--SKIP--*/
[\n]				++yylineno; /*new line*/				;
[\r\t ]				; /*tabulazione e spazio*/
"//"[^\n]*"\n"			++yylineno; /*commento su singola linea*/

 /*SKIP DEL COMMENTO MULTILINEA TRAMITE PASSAGGIO DI STATO LESSICALE*/
"/*"            { BEGIN(IN_COMMENT); }
<IN_COMMENT>"*/" { BEGIN(INITIAL); }
<IN_COMMENT>.    {;} 		/*SE IL COMMENTO MULTILINEA E' SU SINGOLA LINEA ALLORA LO SKIPPA SOLTANTO*/
<IN_COMMENT>\n   { ++yylineno; } /*SE IL COMMENTO MULTININEA E' SU PIU' LINEE INCREMENTA LA VARIABILE "yylineno"*/


"("				{yylval.val=yytext;return PARAPERTA_T;}

")"				{yylval.val=yytext;return PARCHIUSA_T;}

"["				{yylval.val=yytext;return PARAPERTA_Q;}	

"]"				{yylval.val=yytext;return PARCHIUSA_Q;}

";"				{yylval.val=yytext;return PV;}

"eps"				{yylval.val=yytext;return EPS;}

"|"				{yylval.val=yytext;return PIPE;}

"::="				{yylval.val=yytext;return PUO_ESSERE;}

{CIFRA}+			{yylval.val=yytext;return NUM_LOOKAHEAD;}

("LOOKAHEAD")|("lookahead")	{yylval.val=yytext;return OP_LOOKAHEAD;}

"<"[^.\n\r\t<>| :=[\]+~\\"'?#,\-();/*]+">" {yylval.val=yytext;return NON_TERM;}

[^.\n\r\t<>| :=[\]+~\\"'?#,\-();/*]+ 	{yylval.val=yytext;return TERM;}


 /*--ERROR--*/
.				{;}
				
 /*------------------------------------------------------------------------------------FINE SEZIONE 2*/
%%
 /*--------------------------------------------------------------------SEZIONE 3: FUNZIONI AUSILIARIE*/

 void yyerror(char* s)
 {
 fprintf(stderr,"%s, line %d, on '%s'\n",s, yylineno, yytext);
 }

 /*------------------------------------------------------------------------------------FINE SEZIONE 3*/

