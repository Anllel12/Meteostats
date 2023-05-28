package application.control;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.database.GestionMensajeriaBBDD;
import application.database.GestionTiempoBBDD;
import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.MensajeObj;
import application.model.SensorAmaAtar;
import application.model.SensorHumedad;
import application.model.SensorPresion;
import application.model.SensorTemp;
import application.model.TiempoObj;
import application.model.TiempoObjConUnidades;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ClienteController {
	
	public final static ArrayList<String> UNIDADES_TIEMPO = new ArrayList<>(Arrays.asList("ºC", "mmHg", "%", "segundos"));
	
	@FXML
    private TabPane clienteTabPane;
    
    @FXML
    private Label humedad;
    
    @FXML
    private ImageView estadoImageView;
    
    @FXML
    private Label ubicacion;
    
    @FXML
    private Label temperatura;
    
    @FXML
    private Label tiempo;
    
    @FXML
    private Label estadoT;
    
    @FXML
    private Label presion;
    
    @FXML
    private Label amanecer;
    
    @FXML
    private Label atardecer;
    
    @FXML
    private TableView<TiempoObjConUnidades> tbMsg;
    
    @FXML
	private TableColumn<TiempoObj, String> tcFecha;
    
    @FXML
	private TableColumn<SensorTemp, String> tcTemperatura;
    
    @FXML
	private TableColumn<SensorHumedad, String> tcHumedad;
    
    @FXML
	private TableColumn<SensorPresion, String> tcPresion;
    
    @FXML
	private TableColumn<SensorAmaAtar, String> tcAmanecer;
    
    @FXML
	private TableColumn<SensorAmaAtar, String> tcAtardecer;
    
    @FXML
    private JFXTextArea textArea;
    
    @FXML
    private JFXComboBox<String> cbDestinatario;
    
    private String usuarioLoggeado;

    
    @FXML
   	void initialize() {
    	//Como programa empieza en historial de mediciones, cargamos los datos de primeras
        usuarioLoggeado = LogInController.USUARIO_LOGUEADO.getUsuario();

    	loadWeatherTabla(); 
    	estadoTab();
    	
    	/*clienteTabPane.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
               selectedTab(nv.getText());
               System.out.println(nv.getText() + nv.getId());
               
   		});
   		*/
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
   		Vector<Integer> tecnicosACargo = gestionUsuariosBBDD.getTecnicoACargoUsuario(LogInController.USUARIO_LOGUEADO.getUsuario());
   		Vector<String> tecnicos = new Vector<String>();
   		for (Integer idTecnico : tecnicosACargo) {
			tecnicos.add(gestionUsuariosBBDD.getUsuarioById(idTecnico));
		}
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
	    tcFecha.setCellValueFactory(new PropertyValueFactory<>("hora"));
	    tcTemperatura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTemperatura() + " °C"));
	    tcHumedad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHumedad() + " %"));
	    tcPresion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPresion() + " hPa"));
	    tcAmanecer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAmanacer() + " AM"));
	    tcAtardecer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAtardecer() + " PM"));
	}
	
	
	private void loadWeatherTabla() {
	    tcFecha.setCellValueFactory(new PropertyValueFactory<>("hora"));
	    tcTemperatura.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
	    tcPresion.setCellValueFactory(new PropertyValueFactory<>("presion"));
	    tcHumedad.setCellValueFactory(new PropertyValueFactory<>("humedad"));
	    tcAmanecer.setCellValueFactory(new PropertyValueFactory<>("amanecer"));
	    tcAtardecer.setCellValueFactory(new PropertyValueFactory<>("atardecer"));

	    GestionTiempoBBDD gestionTiempo = new GestionTiempoBBDD();

	    List<TiempoObj> tiempoList = gestionTiempo.obtenerInformacionTiempo();

	    if (tiempoList == null) {
	        // muestra error si la lista esat vacia
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("No se pudo cargar la información");
	        alert.setContentText("La cantidad de elementos en las listas no coincide.");
	        alert.showAndWait();
	    } else {
	        ObservableList<TiempoObjConUnidades> items = FXCollections.observableArrayList();
	        for (TiempoObj tiempo : tiempoList) {
	            TiempoObjConUnidades tiempoConUnidades = new TiempoObjConUnidades();
	            tiempoConUnidades.setHora(tiempo.getHora());
	            tiempoConUnidades.setTemperatura(tiempo.getTemperatura() + " °C");
	            tiempoConUnidades.setPresion(tiempo.getPresion() + " hPa");
	            tiempoConUnidades.setHumedad(tiempo.getHumedad() + " %");
	            tiempoConUnidades.setAmanecer(tiempo.getAmanecer() + " AM");
	            tiempoConUnidades.setAtardecer(tiempo.getAtardecer() + " PM");
	            items.add(tiempoConUnidades);
	        }

	        // Cargar los datos en la tabla
	        tbMsg.setItems(items);
	    }
	}
	

	
	private void estadoTab() {
		
		GestionTiempoBBDD gestionTiempo = new GestionTiempoBBDD();
		TiempoObj tiempoActual = gestionTiempo.obtenerInformacionTiempoUltimo();

		Platform.runLater(() -> {
		    if (tiempoActual != null) {
		        ubicacion.setText(tiempoActual.getUbicacion());
		        temperatura.setText(String.format("%d %s", tiempoActual.getTemperatura(), UNIDADES_TIEMPO.get(0)));
		        presion.setText(String.format("%d %s", tiempoActual.getPresion(), UNIDADES_TIEMPO.get(1)));
		        humedad.setText(String.format("%d %s", tiempoActual.getHumedad(), UNIDADES_TIEMPO.get(2)));
		        amanecer.setText(String.valueOf(tiempoActual.getAmanecer()));
		        atardecer.setText(String.valueOf(tiempoActual.getAtardecer()));

		        double presionValue = tiempoActual.getPresion();
		        double humedadValue = tiempoActual.getHumedad();

		        double umbralPresion = 1013.25; // Valor de presión de referencia
		        double umbralHumedad = 70; // Porcentaje de humedad de referencia

		        boolean estaNublado = presionValue < umbralPresion && humedadValue > umbralHumedad;

		        String tiempo = estaNublado ? "Tiempo Nublado" : "Tiempo Despejado";
		        estadoT.setText(tiempo);

		        Image imagenEstado;
		        if (estaNublado) {
		            imagenEstado = new Image(getClass().getResourceAsStream("/data/resources/nublado.png"));
		        } else {
		            imagenEstado = new Image(getClass().getResourceAsStream("/data/resources/despejado.jpg"));
		        }
		        estadoImageView.setImage(imagenEstado);
		    } else {
		        // Handle the case when no data is available
		        ubicacion.setText("Ubicación desconocida");
		        temperatura.setText("vacio");
		        presion.setText("vacio");
		        humedad.setText("vacio");
		        amanecer.setText("vacio");
		        atardecer.setText("vacio");
		        estadoT.setText("No hay datos disponibles");
		        estadoImageView.setImage(null);
		    }
		});

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