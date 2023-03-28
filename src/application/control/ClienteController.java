package application.control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.database.GestionMensajeriaBBDD;
import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.MensajeObj;
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
    private JFXTextArea textArea;
    
    @FXML
    private JFXComboBox<String> cbDestinatario;
    
    @FXML
   	void initialize() {
    	//Como programa empieza en historial de mediciones, cargamos los datos de primeras
    	historialMediciones();
   		clienteTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
               selectedTab(nv.getText());
               System.out.println(nv.getText() + nv.getId());
               
   		});
   		cbDestinatario.getItems().addAll(getTecnicosACargo());
   	}
    
//    Vector<String> getAdminsACargo() {
//    	GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
//   		return gestionUsuariosBBDD.getAdminsACargoUsuario(LogInController.USUARIO_LOGUEADO.getUsuario());
//    }
    
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
   	
   	// TODO obtenemos el primer tecnico a cargo, por defecto en el registro solo se asigna un tecnico
   	private Vector<String> getTecnicosACargo() {
   		GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
   		int tecnico_id = gestionUsuariosBBDD.getTecnicoACargoUsuario(LogInController.USUARIO_LOGUEADO.getNombre()).get(0);
   		Vector<String> tecnicos = new Vector<String>();
   		tecnicos.add(gestionUsuariosBBDD.getUsuarioById(tecnico_id));
   		return tecnicos;
   	}
   	
   	// TODO obtener id administrador seleccionado en combobox de cliente de mensaje a enviar
   	
   	@FXML
   	void sendMensaje() {
   		GestionUsuariosBBDD gUsuario = new GestionUsuariosBBDD();
   		if (cbDestinatario.getSelectionModel().getSelectedItem() != null) {
   				saveMessage(gUsuario.getIdUsuarioByUsuario(LogInController.USUARIO_LOGUEADO.getUsuario()));
   		} else {
				errorAlertCreator("ERROR", "Debes seleccionar Sugerencia o Error");
		}
   	}
   	
	private void saveMessage(int from) {
		GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();  
		GestionUsuariosBBDD gUsuarios = new GestionUsuariosBBDD();
    	String mensaje = textArea.getText().trim();
    	// id tecnico a cargo
    	int to = gUsuarios.getIdUsuarioByUsuario(cbDestinatario.getSelectionModel().getSelectedItem());
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	if (!mensaje.isEmpty()) {
    		MensajeObj msg = new MensajeObj(mensaje, from, to, 0, timestamp);
    		int isOk = gMens.writeNewMessage(msg);
    		if (isOk == GestionMensajeriaBBDD.ERROR_ESCRITURA) {
    			errorAlertCreator("Error","No se ha podido enviar el mensaje");
    		} else if (isOk == GestionMensajeriaBBDD.ESCRITURA_OK) {
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
		loadWeatherTabla();	
	}
	
	private void loadWeatherTabla() {
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
