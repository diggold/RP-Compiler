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
		if(current_nptr->brother==NULL)continue; //se uno dei fratelli è un NULL pointer lo si salta
		current_nptr=current_nptr->brother;
	}
	current_nptr->brother=NULL;

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

//esegue una visita preorder del grafo Ennario
void btree_preorder_visit(node* root){

	node* nptr;

	//se il nodo non è un null pointer
	if(root!=NULL){
		
		//valuta la radice
		printf("%s\n", root->val);
		
		//valuta preorder il figlio
		btree_preorder_visit(root->son);	
		
		//valuta preorder la lista dei fratelli
		btree_preorder_visit(root->brother);
		
	}
}

//esegue una visita postorder del grafo Ennario
void btree_postorder_visit(node* root){
	
	node* nptr;

	if(root!=NULL){
		
		//valuta postorder il figlio
		btree_postorder_visit(root->son);	
		
		//valuta la radice	
		printf("%s\n", root->val);
		
		//valuta postorder la lista dei fratelli
		btree_postorder_visit(root->brother);

		
	}
}

/*test
int main(){

	node* nodo2=btree_mknode("nodo2", btree_mkleaf("foglia1"), 2, btree_mkleaf("foglia2"), btree_mkleaf("foglia3"));
	node* nodo4=btree_mknode("nodo4", btree_mkleaf("foglia4"), 1, btree_mkleaf("foglia5"));
	node* nodo5=btree_mknode("nodo5", btree_mkleaf("foglia6"), 0);
	node* nodo3=btree_mknode("nodo3", nodo4, 1, nodo5);
	node* nodo1=btree_mknode("nodo1", nodo2, 1, nodo3);
	
	printf("\npre-order visit:\n\n");
	btree_preorder_visit(nodo1);

	printf("\npost-order visit:\n\n");
	btree_postorder_visit(nodo1);

	return 0;

}
*/
