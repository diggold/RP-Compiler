package my_package;
import java.util.Vector;

/**
 * Esegue le visite
 * di un albero ennario organizzato in una
 * struttura binaria (Vedi classe Node).
 * Ogni visita restituisce un Vector che
 * contiene l'albero linearizzato (lista di nodi della visita)
 * @author Luciano
 *
 */
public class Visitor{
	
	//----------------------------------------------------------------------------------METODI PUBBLICI
	//visita preorder di un albero ennario implementato
	//come albero Binario (vedi classe Node).
	//
	//NB: la visita post order è diversa da quella di un
	//normale albero ennario (vedi il metodo che implementa la visita).
	public Vector<Node> preorderVisit(Btree tree){
		
		Vector<Node> nodeList=new Vector<Node>();
		this.preorderVisit(nodeList, tree.getRoot());
		
		return nodeList;
	}
	
	//visita postorder
	public Vector<Node> postorderVisit(Btree tree){
		
		Vector<Node> nodeList=new Vector<Node>();
		this.postorderVisit(nodeList, tree.getRoot());
		
		return nodeList;
	}
	//-------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------METODI PRIVATI
	//implementazione della visita preorder
	private void preorderVisit(Vector<Node> nodeList, Node root){
		
		if(root!=null){
			
			//valutazione della radice
			nodeList.addElement(root);
			//valutazione del figlio
			preorderVisit(nodeList, root.getSon());
			//valutazione del fratello
			preorderVisit(nodeList, root.getBrother());
		}
	}
	
	//implementazione della visita postorder
	private void postorderVisit(Vector<Node> nodeList, Node root){
		
		if(root!=null){
			
			//valutazione del figlio
			postorderVisit(nodeList, root.getSon());
			//valutazione della radice 
			//(NB: visita post-order funziona così nel caso 
			// di albero ennario implementato come binario)
			nodeList.addElement(root);
			//valutazione del fratello
			postorderVisit(nodeList, root.getBrother());
			
		}
	}
	//-------------------------------------------------------------------------------------------------
}
