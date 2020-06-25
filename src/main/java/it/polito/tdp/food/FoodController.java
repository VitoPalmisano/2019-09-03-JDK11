/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	if(boxPorzioni.getValue()==null) {
    		txtResult.appendText("Selezionare un valore dalla tendina");
    		return;
    	}
    	
    	int N;
    	
    	try {
			N = Integer.parseInt(txtPassi.getText());
		}catch(NumberFormatException nfe) {
			txtResult.setText("Inserire un valore valido di passi");
			return;
		}
    	
    	List<String> cammino = model.ricorsione(N, boxPorzioni.getValue());
    	
    	if(cammino.isEmpty()) {
    		txtResult.setText("Non esiste cammino, con questa lunghezza, a partire da questa porzione");
    		return;
    	}
    	
    	txtResult.setText("Peso del cammino migliore: "+model.getPeso());
    	
    	for(String s : cammino) {
    		txtResult.appendText("\n"+s);
    	}
    	
    	
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	if(boxPorzioni.getValue()==null) {
    		txtResult.appendText("Selezionare un valore dalla tendina");
    		return;
    	}
    	
    	List<Adiacenza> vicini = model.getVicini(boxPorzioni.getValue());
    	
    	for(Adiacenza a : vicini) {
    		txtResult.appendText(a.getP1()+", peso: "+a.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	double calorie;
    	
    	try {
			calorie = Double.parseDouble(txtCalorie.getText());
		}catch(NumberFormatException nfe) {
			txtResult.setText("Inserire un valore valido di calorie");
			return;
		}
    	
    	model.creaGrafo(calorie);
    	
    	boxPorzioni.getItems().clear();
    	boxPorzioni.getItems().addAll(model.getVertici());
    	
    	txtResult.setText("Creato grafo con "+model.getNumVertici()+" vertici e "+model.getNumArchi()+" archi");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
