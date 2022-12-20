package application.control;

import java.io.IOException;

import java.util.Vector;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.main.MensajeObj;
import application.main.Mensajeria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
			comunicacionesTab();
			
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
	
}
