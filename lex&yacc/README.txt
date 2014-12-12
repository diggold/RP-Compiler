COMANDI PER LA COMPILAZIONE E L'ESECUZIONE DEL PARSER:

% flex tiny.l		/*generazione del sorgente del lexer --> lex.yy.c*/

% yacc -v -d tiny.y	/*1-generazione del sorgente del parser --> y.tab.c
					 *2-generazione dell'header file --> y.tab.h
					 *3-generazione del file di descrizione dell'automa -->y.output*/

% gcc -o parser.exe lex.yy.c y.tab.c -lfl /*compilazione e generazione dell'eseguibile del parser --> parser.exe*/

% ./parser.exe < program.txt /*esecuzione del parser sul file di ingresso "program.txt"*/

NB_1:
Il lexer, nella sezione delle definizioni non definisce i TOKEN ma, importando "y.tab.h",
(creata in fase di generazione del sorgente del parser "y.tab.c", grazie all'opzione -d) utilizza 
le definizioni dei token del parser.

NB_2:
Il lexer, nella sezione dedicata alle funzioni ausiliarie deve implementare la funzione "yyerror"
in quanto essa non è definita nei file di libreria (cercare una soluzione???), quindi bisognerà
anche dichiararne la relativa signature nella sezione dedicata alle definizioni.

NB_3:
In fase di generazione del sorgente del parser, l'opzione "-v" permette di ottenere 
il file "y.output", contenente la descrizione dell'automa implementato e l'indicazione degli 
eventuali conflitti presenti.
