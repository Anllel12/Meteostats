package application.control;

import java.io.IOException;

import java.util.Vector;

import com.jfoenix.controls.JFXTextArea;

import application.main.MensajeObj;
import application.main.Mensajeria;
import application.main.TiempoObj;
import application.main.Tiempo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdministradorController {
	
	@FXML
    private TabPane adminTabPane;
	
	@FXML
	private TableView<MensajeObj> tbMsg;
	
	@FXML
	private TableColumn<MensajeObj, String> fecha;
	
    @FXML
    private TableColumn<MensajeObj, String> desc;
    
    @FXML
    private TableColumn<MensajeObj, String> status;
    
    @FXML
    private Button btnEnviarM;
    
    @FXML
    private JFXTextArea txtMensaje;
    
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
    private Label duracDia;
    
    @FXML
    private Label horaSist;
    
    @FXML
    private Label funcionamiento;

    void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
    
    @FXML
    void enviarMensaje(ActionEvent event) throws IOException{
    	Mensajeria mg = new Mensajeria();
    	int from = 0;
    	int to = 0;    	
    	String mensaje = txtMensaje.getText().trim();
    	int isOk = mg.writeNewMessage(mensaje, from, to);
		if (isOk == Mensajeria.ERROR_ESCRITURA) {
			errorAlertCreator("Error","No se ha podido enviar el mensaje");
		} else if (isOk == Mensajeria.ESCRITURA_OK) {
			errorAlertCreator("Completado","El mensaje se ha enviado correctamente");
		}
    }
    
    @FXML
	void initialize() {
		adminTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
            selectedTab(nv.getText());
            System.out.println(nv.getText() + nv.getId());
            
		});
		
	
	}
	
	private void selectedTab(String tabTitle) {
		switch (tabTitle) {
		case "Comunicaciones":
			comunicacionesTab();
			
			break;
			
		case "Estado del Servicio":
			estadoTab();
			
			break;
			
		case "Comunicar a Técnico":
			comunicacionesTab();
			
			break;

		default:
			break;
		}
	}

	private void comunicacionesTab() {
		fecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
		desc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
		status.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
		Mensajeria mensajeria = new Mensajeria();
		Vector<MensajeObj> mensajes = mensajeria.getMessages(Mensajeria.USER_ADMIN);
		
		tbMsg.getItems().setAll(mensajes);
		
	}
	
	private void estadoTab() {
		Tiempo tiempo_ = new Tiempo();
		Vector<TiempoObj> tiempos = tiempo_.getWeather();
		ubicacion.setText(tiempos.lastElement().getUbicacion().toString());
		temperatura.setText(Integer.toString(tiempos.lastElement().getTemperatura()));
		presion.setText(Integer.toString(tiempos.lastElement().getPresion()));
		humedad.setText(Integer.toString(tiempos.lastElement().getHumedad()));
		duracDia.setText(Integer.toString(tiempos.lastElement().getAmanacer()) + " - " + Integer.toString(tiempos.lastElement().getAtardecer()));
		horaSist.setText(Integer.toString(tiempos.lastElement().getHora()));
		funcionamiento.setText(Integer.toString(tiempos.lastElement().getTiempoFuncionando()));
		
	}
	
}
