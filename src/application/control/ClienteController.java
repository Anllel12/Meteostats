package application.control;

import java.util.Vector;

import com.jfoenix.controls.JFXTextArea;

import application.main.MensajeObj;
import application.main.Tiempo;
import application.main.TiempoObj;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClienteController {
	
	@FXML
    private TabPane clienteTabPane;
    
    @FXML
    private Label humedad;
    
    @FXML
    private Label ubicacion;
    
    @FXML
    private Label temperatura;
    
    @FXML
    private Label tiempo;
    
    @FXML
    private Label presion;
    
    @FXML
    private Label amanecer;
    
    @FXML
    private Label atardecer;
    
    @FXML
   	void initialize() {
   		clienteTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
               selectedTab(nv.getText());
               System.out.println(nv.getText() + nv.getId());
               
   		});
   	}
   	
   	private void selectedTab(String tabTitle) {
   		switch (tabTitle) {
   		case "Medición actual":
   			estadoTab();
   			
   			break;
   			
   		case "Ver historial mediciones":
   			estadoTab();
   			
   			break;
   			
   		case "Sugerencias / Errores":
   			estadoTab();
   			
   			break;

   		default:
   			break;
   		}
   	}
   	
   	private void estadoTab() {
		Tiempo tiempo_ = new Tiempo();
		Vector<TiempoObj> tiempos = tiempo_.getWeather();
		ubicacion.setText(tiempos.lastElement().getUbicacion().toString());
		temperatura.setText(Integer.toString(tiempos.lastElement().getTemperatura()));
		presion.setText(Integer.toString(tiempos.lastElement().getPresion()));
		humedad.setText(Integer.toString(tiempos.lastElement().getHumedad()));
		amanecer.setText(Integer.toString(tiempos.lastElement().getAmanacer()));
		atardecer.setText(Integer.toString(tiempos.lastElement().getAtardecer()));		
	}
}
