package application.control;

import java.io.IOException;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.main.Main;
import application.model.Mensajeria;
import application.model.Tiempo;
import application.model.TiempoObj;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
   		cbEleccion.getItems().addAll(SELEC_SUGERENCIA, SELEC_ERROR);
   		
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
		//%d = integer, %s = string
		ubicacion.setText(tiempos.lastElement().getUbicacion().toString());
		temperatura.setText(String.format("%d %s", tiempos.lastElement().getTemperatura(), Tiempo.UNIDADES_TIEMPO.get(0)));
		presion.setText(String.format("%d %s", tiempos.lastElement().getPresion(), Tiempo.UNIDADES_TIEMPO.get(1)));
		humedad.setText(String.format("%d %s", tiempos.lastElement().getHumedad(), Tiempo.UNIDADES_TIEMPO.get(2)));
		amanecer.setText(Integer.toString(tiempos.lastElement().getAmanacer()));
		atardecer.setText(Integer.toString(tiempos.lastElement().getAtardecer()));		
	}
	
	@FXML
	void logOut() {
		//Las siguientes dos lineas para cerrar la anterior ventana
		Stage st = (Stage) clienteTabPane.getScene().getWindow();
		st.close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Login.fxml"));
        
		Stage stage = new Stage();
        // No hacer clickeables resto de ventanas abiertas
        stage.setTitle("Login");
        Parent root1;
		try {
			root1 = (Parent) loader.load();
			stage.setScene(new Scene(root1));
			stage.getIcons().add(Main.ICONO_APP);
            stage.centerOnScreen();
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
