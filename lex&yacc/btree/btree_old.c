#include <stdarg.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

/*nodo dell'albero*/
typedef struct node{
		
	char* val;
	struct node* son;
	struct node* brother;
}node;


/*crea un nodo*/
node* btree_mknode(char* op		/*operazione - valore associato al nodo*/, 
	     	   node* son		/*figlio*/, 
             	   int n_son_brothers/*numero altri figli (visti come lista di nodi fratelli del nodo figlio)*/, 
	     	   ... 		/*lista figli - fratelli del primo figlio "son"*/
	     	  )
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
node* btree_mkleaf(char* val){
	
	//----------------------------------------alloca lo spazio per il nodo
	node* nptr=(node*)malloc(sizeof(node));

	//----------------------------------------avvalora i campi del nodo foglia
	nptr->val=val;
	nptr->son=NULL;
	nptr->brother=NULL;

	return nptr;	
}

void btree_preorder_visit(node* root){

	if(root!=NULL){

		btree_preorder_visit(root->son);
		btree_preorder_visit(root->brother);
		printf("%s\n", root->val);
	}
}

void btree_inorder_visit(node* root){
	
	if(root!=NULL){
		btree_inorder_visit(root->son);
		printf("%s\n", root->val);
		btree_inorder_visit(root->brother);

	}
}
