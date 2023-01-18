package application.control;

import java.util.Vector;

import com.jfoenix.controls.JFXTextArea;

import application.main.GestionGson;
import application.main.MensajeObj;
import application.main.Mensajeria;
import application.main.TiempoObj;
import application.main.Tiempo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    
    private MensajeObj selectedMsg;

    void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
    
    @FXML
    void enviarMensaje(ActionEvent event) {
    	
    	Mensajeria mg = new Mensajeria();
    	int from = GestionGson.ROL_ADMIN;
    	int to = GestionGson.ROL_TECNICO;
    	String mensaje = txtMensaje.getText().trim();
    	if (!mensaje.isEmpty()) {
    		int isOk = mg.writeNewMessage(mensaje, from, to);
    		if (isOk == Mensajeria.ERROR_ESCRITURA) {
    			errorAlertCreator("Error","No se ha podido enviar el mensaje");
    		} else if (isOk == Mensajeria.ESCRITURA_OK) {
    			errorAlertCreator("Completado","El mensaje se ha enviado correctamente");
    		}
    	} else {
    		errorAlertCreator("Error","El mensaje no puede estar vacio");
    	}
    	
    	
    }
    
    @FXML
	void initialize() {
    	comunicacionesTab();
		adminTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
            selectedTab(nv.getText());
            System.out.println(nv.getText() + nv.getId());
            
		});
		
		tbMsg.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MensajeObj>() {

			@Override
			public void changed(ObservableValue<? extends MensajeObj> observable, MensajeObj oldValue,
					MensajeObj newValue) {
				if (tbMsg.getSelectionModel().getSelectedItem() != null) {
					selectedMsg = tbMsg.getSelectionModel().getSelectedItem();
				}
				
			}
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
			
	/*	case "Comunicar a Técnico":
			comunicacionesTab();
			
			break;
	*/
		default:
			break;
		}
	}

	private void comunicacionesTab() {
		fecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
		desc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
		status.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
		updateMsgsTab();
		
	}
	
	private void updateMsgsTab() {
		Mensajeria mensajeria = new Mensajeria();
		Vector<MensajeObj> mensajes = mensajeria.getMessages(Mensajeria.USER_ADMIN);
		
		tbMsg.getItems().setAll(mensajes);
	}
	
	@FXML
	void menuTablaEliminar() {
		if (selectedMsg != null) {
			Mensajeria mensajeria = new Mensajeria();
			mensajeria.deleteMessage(selectedMsg);
			errorAlertCreator("OK", "Mensaje eliminado correctamente");
			updateMsgsTab();
		} else {
			errorAlertCreator("OK", "Ningun mensaje seleccionado");
		}
	}
	
	@FXML
	void menuTablaSetPendiente() {
		if (selectedMsg != null) {
			Mensajeria mensajeria = new Mensajeria();
			selectedMsg.setStatus(Mensajeria.STATUS_PENDIENTE);
			mensajeria.modifyMessage(selectedMsg);
			errorAlertCreator("OK", "Mensaje set como corregido");
			updateMsgsTab();
		} else {
			errorAlertCreator("OK", "Ningun mensaje seleccionado");
		}
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
