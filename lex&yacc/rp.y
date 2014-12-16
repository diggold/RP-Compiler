/*-------------------------------------------------------------SEZIONE 1: DICHIARAZIONI*/
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#include "./btree/btree.h"
%}

%union{
	char* val;
	struct node* nptr;
}

%token <val>  PARAPERTA_T
%token <val> PARCHIUSA_T
%token <val> PARAPERTA_Q
%token <val> PARCHIUSA_Q
%token <val> PV
%token <val> EPS
%token <val> PIPE
%token <val> PUO_ESSERE
%token <val> NUM_LOOKAHEAD
%token <val> OP_LOOKAHEAD
%token <val> NON_TERM
%token <val> TERM

%type <nptr> grammatica
%type <nptr> regole
%type <nptr> regola
%type <nptr> corpo
%type <nptr> elementi
%type <nptr> elemento

%start grammatica
 
/*-----------------------------------------------------------------------FINE SEZIONE 1*/

/*--------------------------------------------------------------------SEZIONE 2: REGOLE*/
%%

grammatica : regole	{printf("grammatica->regole\n"); 
	   		 $$=$1;
	   		 printf("\n\n--------------------\n"
				"PRE-ORDER VISIT\n"
				"--------------------\n");
			 btree_preorder_visit($$);
			 printf("\n\n--------------------\n"
				"POST-ORDER VISIT\n"
				"--------------------\n");
			 btree_postorder_visit($$);}

	;
regole : regola		{printf("regole->regola\n"); 
       			 $$=$1;}
       | regola regole	{printf("regole->regola regole\n"); $$=btree_mknode("seq", $1, 1, $2);}
       ;

regola : OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD PARCHIUSA_T NON_TERM PUO_ESSERE corpo PV
					{printf("regola->OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD"
						"PARCHIUSA_T NON_TERM PUO_ESSERE corpo PV\n"); 
					 $$=btree_mknode("::=", btree_mkleaf($3), 2, btree_mkleaf($5), $7);}

       | NON_TERM PUO_ESSERE corpo PV	{printf("regola->NON_TERM PUO_ESSERE corpo PV\n"); 
					 $$=btree_mknode("::=", btree_mkleaf($1), 1, $3);}
       ;

corpo : elementi		{printf("corpo->elementi\n");
      				 $$=$1;}

      | elementi PIPE corpo	{printf("corpo->elementi PIPE corpo\n");
				 $$=btree_mknode("|", $1, 1, $3);}
      ;

elementi : elemento		{printf("elementi->elemento\n");
	 			 $$=$1;}

	 | elemento elementi	{printf("elementi->elemento elementi\n;");
				 $$=btree_mknode("concat", $1, 1, $2);}
    	 

elemento   : TERM 				{printf("elemento->TERM\n"); 
	   					 $$=btree_mkleaf($1);}

           | NON_TERM				{printf("elemento->TERM\n");
						 $$=btree_mkleaf($1);}

	   | EPS				{printf("elemento->TERM\n");
						 $$=btree_mkleaf($1);}

	   | PARAPERTA_Q elementi PARCHIUSA_Q	{printf("elemento->PARAPERTA_Q elementi PARCHIUSA_Q\n");
						 $$=btree_mknode("?", $2, 0);}
	   ;

%%
/*------------------------------------------------------------------------FINE SEZIONE 2*/


/*--------------------------------------------------------SEZIONE 3: ROUTINE AUSILIARIE*/

int main(){
	
	printf("\n\n--------------------\n"
	       "PARSING\n"
	       "--------------------\n");
	yyparse();
	return 0;
}

/*------------------------------------------------------------------------FINE SEZIONE 3*/

