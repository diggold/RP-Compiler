package my_package;
import java.util.ArrayList;

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
	//NB: la visita post order � diversa da quella di un
	//normale albero ennario (vedi il metodo che implementa la visita).
	public ArrayList<Node> preorderVisit(Btree tree){
		
		ArrayList<Node> nodeList=new ArrayList<Node>();
		this.preorderVisit(nodeList, tree.getRoot());
		
		return nodeList;
	}
	
	//visita postorder
	public ArrayList<Node> postorderVisit(Btree tree){
		
		ArrayList<Node> nodeList=new ArrayList<Node>();
		this.postorderVisit(nodeList, tree.getRoot());
		
		return nodeList;
	}
	//-------------------------------------------------------------------------------------------------
	
	//-----------------------------------------------------------------------------------METODI PRIVATI
	//implementazione della visita preorder
	private void preorderVisit(ArrayList<Node> nodeList, Node root){
		
		if(root!=null){
			
			//valutazione della radice
			nodeList.add(root);
			//valutazione del figlio
			preorderVisit(nodeList, root.getSon());
			//valutazione del fratello
			preorderVisit(nodeList, root.getBrother());
		}
	}
	
	//implementazione della visita postorder
	private void postorderVisit(ArrayList<Node> nodeList, Node root){
		
		if(root!=null){
			
			//valutazione del figlio
			postorderVisit(nodeList, root.getSon());
			//valutazione della radice 
			//(NB: visita post-order funziona cos� nel caso 
			// di albero ennario implementato come binario)
			nodeList.add(root);
			//valutazione del fratello
			postorderVisit(nodeList, root.getBrother());
			
		}
	}
	//-------------------------------------------------------------------------------------------------
}
