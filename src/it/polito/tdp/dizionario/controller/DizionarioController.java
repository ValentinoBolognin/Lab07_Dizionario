package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {
	
	private Model model;

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTrovaGradoMax;
	@FXML
    private Button btnTrovaTuttiVicini;
	
	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	void doReset(ActionEvent event) {
		inputNumeroLettere.clear();
		inputParola.clear();
		txtResult.clear();
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		inputParola.clear();
		txtResult.clear();
		
		try {
		
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			
			List<String> parole = model.createGraph(numeroLettere);
			
			for(String parola : parole)
				txtResult.appendText(parola+"\n");
		
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire il numero di lettere nel formato corretto.");
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {
		
		inputParola.clear();
		txtResult.clear();
		
		try {
			String gradoMax = model.findMaxDegree();
			
			txtResult.setText(gradoMax);

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}
	
	@FXML
	void doTrovaTuttiVicini(ActionEvent event) {
		txtResult.clear();
		
		try {
		
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			
			String parolaInserita = inputParola.getText();
			
			if(numeroLettere != parolaInserita.length()){
				txtResult.setText("Inserire una parola con il numero di lettere corretto.");
				return;
			}
			
			List<String> paroleRaggiungibili = model.graphVisit(parolaInserita);
			
			for(String parola : paroleRaggiungibili)
				txtResult.appendText(parola+"\n");
			
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire il numero di lettere nel formato corretto.");
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}


	@FXML
	void doTrovaVicini(ActionEvent event) {
		
		txtResult.clear();
		
		try {
		
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			
			String parolaInserita = inputParola.getText();
			
			if(numeroLettere != parolaInserita.length()){
				txtResult.setText("Inserire una parola con il numero di lettere corretto.");
				return;
			}
			
			List<String> paroleSimili = model.displayNeighbours(parolaInserita);
			
			for(String parola : paroleSimili)
				txtResult.appendText(parola+"\n");
			
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire il numero di lettere nel formato corretto.");
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaTuttiVicini != null : "fx:id=\"btnTrovaTuttiVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
	}
}