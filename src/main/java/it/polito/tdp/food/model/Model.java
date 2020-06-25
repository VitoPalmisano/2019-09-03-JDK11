package it.polito.tdp.food.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private List<String> best;
	private int peso;
	private int N;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(double calorie) {
		
		grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.listPortionsType(calorie));
		
		List<Adiacenza> adiacenze = dao.listAdiacenze(calorie);
		
		for(Adiacenza a : adiacenze) {
			if(grafo.vertexSet().contains(a.getP1()) && grafo.vertexSet().contains(a.getP2()))
				Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
		}
	}
	
	public List<String> getVertici() {
		List<String> vertici = new ArrayList<String>(grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getVicini(String porzione){
		List<DefaultWeightedEdge> archi = new ArrayList<DefaultWeightedEdge>(grafo.edgesOf(porzione));
		List<Adiacenza> vicini = new ArrayList<Adiacenza>();
		for(DefaultWeightedEdge e : archi) {
			vicini.add(new Adiacenza(Graphs.getOppositeVertex(grafo, e, porzione), "", (int)grafo.getEdgeWeight(e)));
		}
		return vicini;
	}
	
	public List<String> ricorsione(int N, String porzione) {
		
		best = new ArrayList<String>();
		peso = 0;
		this.N = N;
		
		List<String> parziale = new ArrayList<String>();
		parziale.add(porzione);
		
		cerca(parziale, 0, porzione, 0);
		
		if(!best.isEmpty())
			best.remove(porzione);
		
		return best;
	}
	
	public void cerca(List<String> parziale, int pesoTemp, String attuale, int livello) {
		
		if(livello==N) {
			if(pesoTemp>peso) {
				best = new ArrayList<String>(parziale);
				peso = pesoTemp;
			}
			return;
		}
		
		for(String vicino : Graphs.neighborListOf(grafo, attuale)) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(parziale, pesoTemp+(int)grafo.getEdgeWeight(grafo.getEdge(attuale, vicino)), vicino, livello+1);
				parziale.remove(vicino);
			}
		}
	}
	
	public int getPeso(){
		return peso;
	}
}
