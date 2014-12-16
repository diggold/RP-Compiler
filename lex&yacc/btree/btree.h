/*nodo dell'albero*/
typedef struct node{
		
	char* val;
	struct node* son;
	struct node* brother;
}node;

/*crea un nodo*/
node* btree_mknode(char*, node*, int, ...);

//crea un nodo foglia
node* btree_mkleaf(char*);
