/*-------------------------------------------------------------SEZIONE 1: DICHIARAZIONI*/
%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>

typedef struct node{
		
	char* val;
	struct node* son;
	struct node* brother;
}node;


node* mknode(char*, node*, int, ...);
node* mkleaf(char*);

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

grammatica : regole	{printf("grammatica->regole\n"); $$=$1;}

	;
regole : regola		{printf("regole->regola\n"); $$=$1;}
       | regola regole	{printf("regole->regola regole\n"); $$=mknode("seq", $1, 1, $2);}
       ;

regola : OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD PARCHIUSA_T NON_TERM PUO_ESSERE corpo PV
					{printf("regola->OP_LOOKAHEAD PARAPERTA_T NUM_LOOKAHEAD PARCHIUSA_T NON_TERM PUO_ESSERE corpo PV\n"); $$=mknode("::=", mkleaf($3), 2, mkleaf($5), $7);}
       | NON_TERM PUO_ESSERE corpo PV	{printf("regola->NON_TERM PUO_ESSERE corpo PV\n"); $$=mknode("::=", mkleaf($1), 1, $3);}
       ;

corpo : elementi		{printf("corpo->elementi\n"); $$=$1;}
      | elementi PIPE corpo	{printf("corpo->elementi PIPE corpo\n"); $$=mknode("|", $1, 1, $3);}
      ;

elementi : elemento		{printf("elementi->elemento\n"); $$=$1;}
	 | elemento elementi	{printf("elementi->elemento elementi\n;"); $$=mknode("concat", $1, 1, $2);}
    	 

elemento   : TERM 				{printf("elemento->TERM"); $$=mkleaf($1);}
           | NON_TERM				{printf("elemento->TERM"); $$=mkleaf($1);}
	   | EPS				{printf("elemento->TERM"); $$=mkleaf($1);}
	   | PARAPERTA_Q elementi PARCHIUSA_Q	{printf("elemento->PARAPERTA_Q elementi PARCHIUSA_Q\n"); $$=mknode("?", $2, 0);}
	   ;

%%
/*------------------------------------------------------------------------FINE SEZIONE 2*/


/*--------------------------------------------------------SEZIONE 3: ROUTINE AUSILIARIE*/

/*crea un nodo*/
node* mknode(char* op		/*operazione - valore associato al nodo*/, 
	     node* son		/*figlio*/, 
             int n_son_brothers/*numero altri figli (visti come lista di nodi fratelli del nodo figlio)*/, 
	     ... 		/*lista figli - fratelli del primo figlio "son"*/)
{
	
	//-------------------------------------------------------------alloca spazio per il nodo corrente
	node* nptr=(node*)malloc(sizeof(node));

	//---------------------------------------------------avvalora i primi due campi del nodo corrente
	nptr->val=op;//assegna il valore
	nptr->son=son;//assegna il figlio

	//-----------------------------creazione figli successivi come lista di fratelli del primo figlio
	int i;
	va_list argP;

	va_start(argP, n_son_brothers);	//inizio lettura argomenti variabili

	node* current_nptr=nptr->son;
	
	//per ogni successivo "figlio" viene assegnato 
	//un "fratello" al primo figlio
	for(i=0; i<n_son_brothers; i++){

		current_nptr->brother=va_arg(argP, node*);
		current_nptr=current_nptr->brother;
	}

	va_end(argP); //fine lettura argomenti variabili

	return nptr;
}


//crea un nodo foglia
node* mkleaf(char* val){
	
	//alloca lo spazio per la struttura "node"
	node* nptr=(node*)malloc(sizeof(node));

	//avvalora i campi del nodo foglia
	nptr->val=val;
	nptr->son=NULL;
	nptr->brother=NULL;

	return nptr;	
}

int main(){

	yyparse();
	return 0;
}

/*------------------------------------------------------------------------FINE SEZIONE 3*/

