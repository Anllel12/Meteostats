package application.control;

import java.util.Vector;

import application.main.MensajeObj;
import application.main.Mensajeria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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


    void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
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
