package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {
	
	private WordDAO wordDAO;
	private UndirectedGraph<String, DefaultEdge> grafo;
	
	public Model() {
		wordDAO = new WordDAO();
	}

	public List<String> createGraph(int numeroLettere) {
		
		grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		List<String> parole = wordDAO.getAllWordsFixedLength(numeroLettere);
		
		for(int i=0; i<parole.size() ; i++){
			grafo.addVertex(parole.get(i));
			for(int j=i+1; j < parole.size(); j++)
				if(wordDAO.getSimilarWord((parole.get(i)), parole.get(j))) {
					grafo.addVertex(parole.get(j));
					grafo.addEdge(parole.get(i), parole.get(j));
				}
		}
		
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		
		return wordDAO.getAllSimilarWords(parolaInserita, parolaInserita.length());
	}

	public String findMaxDegree() {
		String risultato = "";
		
		int max = 0;
		String vertice = "";
		
		for( String s: grafo.vertexSet() ) {
			for(DefaultEdge e : grafo.edgesOf(s) ) {
				if(max < grafo.degreeOf(s)) {
					max = grafo.degreeOf(s);
					vertice = s;
				}
			}
		}
		
		risultato += "Grado max: "+max+"\n"+"Vertice: "+vertice+"\n"+"Vicini:";
		
		List<String> vicini = this.displayNeighbours(vertice);
		
		for(String s: vicini){
			risultato +=" "+s;
		}
	
		return risultato;
	}
	
	public List<String> graphVisit(String parolaInserita) {
		List<String> visited = new ArrayList<String>();
		
		BreadthFirstIterator<String, DefaultEdge> bfv = new BreadthFirstIterator<>(grafo, parolaInserita) ;
		while(bfv.hasNext()) {
			visited.add(bfv.next()) ;
		}
		
		return visited;
	}

}
