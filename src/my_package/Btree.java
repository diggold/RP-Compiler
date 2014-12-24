package my_package;

public class Btree {
	
	private Node root;
	
	public Btree(Node root){
		this.root=root;
	}
	
	public Node getRoot(){
		return root;
	}
	
	public void setRoot(Node root){
		this.root=root;
	}

}
