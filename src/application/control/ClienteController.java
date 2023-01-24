package application.control;

import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.main.Mensajeria;
import application.main.Tiempo;
import application.main.TiempoObj;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableView<TiempoObj> tbMsg;
    
    @FXML
	private TableColumn<TiempoObj, String> tcFecha;
    
    @FXML
	private TableColumn<TiempoObj, String> tcTemperatura;
    
    @FXML
	private TableColumn<TiempoObj, String> tcHumedad;
    
    @FXML
	private TableColumn<TiempoObj, String> tcPresion;
    
    @FXML
	private TableColumn<TiempoObj, String> tcAmanecer;
    
    @FXML
	private TableColumn<TiempoObj, String> tcAtardecer;
    
    @FXML
    private JFXComboBox<String> cbEleccion;
    
    @FXML
    private JFXTextArea textArea;
    
    private static final String SELEC_SUGERENCIA = "Sugerencia";
	private static final String SELEC_ERROR = "Error";
    
    @FXML
   	void initialize() {
    	//Como programa empieza en historial de mediciones, cargamos los datos de primeras
    	historialMediciones();
   		clienteTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
               selectedTab(nv.getText());
               System.out.println(nv.getText() + nv.getId());
               
   		});
   		
   	}
    
    void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
   	
   	private void selectedTab(String tabTitle) {
   		switch (tabTitle) {
   		case "Medición actual":
   			estadoTab();
   			
   			break;
   			
   		case "Ver historial mediciones":
   			historialMediciones();
   			
   			break;
   			
   		case "Sugerencias / Errores":
   			cbEleccion.getItems().addAll(SELEC_SUGERENCIA, SELEC_ERROR);
   			
   			break;

   		default:
   			break;
   		}
   	}
   	
   	@FXML
   	void sendMensaje() {
   		int from = 0;
   		if (cbEleccion.getSelectionModel().getSelectedItem() != null) {
   			String selected = cbEleccion.getSelectionModel().getSelectedItem();
   			if (selected.equals(SELEC_ERROR)) {
   				from = Mensajeria.CLIENTE_ERROR;
   				saveMessage(from);
   			} else if (selected.equals(SELEC_SUGERENCIA)) {
   				from = Mensajeria.CLIENTE_SUGERENCIA;
   				saveMessage(from);
   			}
   		} else {
				errorAlertCreator("ERROR", "Debes seleccionar Sugerencia o Error");
		}
   	}
   	
	private void saveMessage(int from) {
		Mensajeria mg = new Mensajeria();   	
    	String mensaje = textArea.getText().trim();
    	Vector<String> to = LogInController.USUARIO_LOGUEADO.getTecnicosACargo();
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

	private void historialMediciones() {
		tcFecha.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("hora"));
		tcTemperatura.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("temperatura"));
		tcHumedad.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("humedad"));
		tcPresion.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("presion"));
		tcAmanecer.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("amanacer"));
		tcAtardecer.setCellValueFactory(new PropertyValueFactory<TiempoObj, String>("atardecer"));
		loadMessagesTabla();
		
	}
	
	private void loadMessagesTabla() {
		Tiempo tiempo_ = new Tiempo();
		Vector<TiempoObj> tiempos = tiempo_.getWeather();
		
		tbMsg.getItems().setAll(tiempos);
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
