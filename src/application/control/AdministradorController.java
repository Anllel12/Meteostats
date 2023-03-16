package application.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.database.GestionMensajeriaBBDD;
import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.GestionGson;
import application.model.MensajeObj;
import application.model.Mensajeria;
import application.model.Tiempo;
import application.model.TiempoObj;
import application.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
	private TableView<Usuario> tbUsuario;
	
	@FXML
	private TableColumn<Usuario, String> usuario;
	
    @FXML
    private TableColumn<Usuario, String> email;
    
    @FXML
    private TableColumn<Usuario, Integer> rol;
    
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
    
    @FXML
    private JFXComboBox<String> cbCliente;
    
    @FXML
    private JFXComboBox<String> cbTecnicos;
    
    private Vector<Usuario> usuariosACargo;
    
    private MensajeObj selectedMsg;
    
    private Usuario selectedUsuario;
    
    private String selectedTecnico;
    
    private Vector<Vector<String>> tecnicosAndIds;

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
    	Vector<String> toTecnico = new Vector<String>();
    	toTecnico.add(Mensajeria.TO_TECNICO);
    	
    	String mensaje = txtMensaje.getText().trim();
    	if (!mensaje.isEmpty()) {
    		int isOk = mg.writeNewMessage(mensaje, from, toTecnico);
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
    	cbCliente.setPromptText("Selecciona un cliente");
    	rellenarComboBoxYClientes();
    	cbCliente.valueProperty().addListener((ov, p1, p2) -> {
    	    estadoTab(p2);
    	});
    	
    	cbTecnicos.setPromptText("Selecciona un tecnico");
    	rellenarComboBoxTecnicos();
    	cbTecnicos.valueProperty().addListener((ov, p1, p2) -> {
    	    selectedTecnico = p2;
    	});
    	
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
		
		tbUsuario.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {

			@Override
			public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue,
					Usuario newValue) {
				if (tbUsuario.getSelectionModel().getSelectedItem() != null) {
					selectedUsuario = tbUsuario.getSelectionModel().getSelectedItem();
				}
				
			}
		});
		
	}

	private void rellenarComboBoxYClientes() {
		GestionGson gg = new GestionGson();
    	usuariosACargo = gg.getUsersContainingAdminUsername(LogInController.USUARIO_LOGUEADO.getUsuario());
    	ArrayList<String> nombresUsuarios = new ArrayList<>();
    	for (Usuario usuario : usuariosACargo) {
			nombresUsuarios.add(usuario.getUsuario());
		}
    	cbCliente.getItems().addAll(nombresUsuarios);
	}
	
	private void rellenarComboBoxTecnicos() {
		GestionUsuariosBBDD gub = new GestionUsuariosBBDD();
    	Vector<Vector<String>> tecnicos = gub.getUsuarioAndIdByRol(GestionUsuariosBBDD.ROL_TECNICO);
    	ArrayList<String> nombresUsuarios = new ArrayList<>();
    	for (Vector<String> usuario : tecnicos) {
			nombresUsuarios.add(usuario.get(0));
		}
    	tecnicosAndIds = tecnicos;
    	cbTecnicos.getItems().addAll(nombresUsuarios);
	}
	
	// TODO meter en TO de mensaje enviado
	private int getIdTecnicoByNombre() {
		for (Vector<String> vector : tecnicosAndIds) {
			if (vector.get(0).equals(selectedTecnico)) {
				return Integer.valueOf(vector.get(1));
			}
		}
		return -1;
	}
	
	private void selectedTab(String tabTitle) {
		switch (tabTitle) {
		case "Comunicaciones":
			comunicacionesTab();
			
			break;
			
		case "Estado del Servicio":
			estadoTab(null);
			
			break;
		case "Comunicar a Técnico":
			
			break;
		case "Usuarios":
			usuariosTab();
			break;
		default:
			break;
		}
	}
	
	private void usuariosTab() {
		usuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
		email.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
		rol.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("rol"));
		updateUsuariosTab();
		
	}

	private void updateUsuariosTab() {
		GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
		// TODO obtener objetos mensajes desde BBDD
		Vector<Usuario> usuarios = gestionUsuariosBBDD.getUsuarios();
		tbUsuario.getItems().setAll(usuarios);
		
	}
	


	@FXML
	void tablaUsuarioCambiarRolATecnico() {
		setUsuarioRol(GestionUsuariosBBDD.ROL_TECNICO);
	}
	
	@FXML
	void tablaUsuarioCambiarRolACliente() {
		setUsuarioRol(GestionUsuariosBBDD.ROL_USUARIO);
	}
	
	@FXML
	void tablaUsuarioCambiarRolAAdmin() {
		setUsuarioRol(GestionUsuariosBBDD.ROL_ADMIN);
	}
	
	private void setUsuarioRol(int rol) {
		if (selectedUsuario != null) {
			GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
			gestionUsuariosBBDD.updateUsuario(selectedUsuario, rol);
			updateUsuariosTab();
		} else {
			errorAlertCreator("Error", "Ningun usuario seleccionado");
		}
		
	}

	@FXML
	void menuTablaCambiarRol() {
		
	}

	private void comunicacionesTab() {
		fecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
		desc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
		status.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
		updateMsgsTab();
		
	}
	
	private void updateMsgsTab() {
		Mensajeria mensajeria = new Mensajeria();
		Vector<MensajeObj> mensajes = mensajeria.getMessages(LogInController.USUARIO_LOGUEADO);
		
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
			errorAlertCreator("Error", "Ningun mensaje seleccionado");
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

	private void estadoTab(String usuario_cliente) {
		//TODO meter usuario dentro del tiempo para poder mostrar el q se relacione cn admin
		Tiempo tiempo_ = new Tiempo();
		Vector<TiempoObj> tiempos = tiempo_.getWeather();
		//%d = integer, %s = string
		ubicacion.setText(tiempos.lastElement().getUbicacion().toString());
		temperatura.setText(String.format("%d %s", tiempos.lastElement().getTemperatura(), Tiempo.UNIDADES_TIEMPO.get(0)));
		presion.setText(String.format("%d %s", tiempos.lastElement().getPresion(), Tiempo.UNIDADES_TIEMPO.get(1)));
		humedad.setText(String.format("%d %s", tiempos.lastElement().getHumedad(), Tiempo.UNIDADES_TIEMPO.get(2)));
		duracDia.setText(String.format("%d - %d", tiempos.lastElement().getAmanacer(), tiempos.lastElement().getAtardecer()));
		horaSist.setText(Integer.toString(tiempos.lastElement().getHora()));
		funcionamiento.setText(String.format("%d %s", tiempos.lastElement().getTiempoFuncionando(), Tiempo.UNIDADES_TIEMPO.get(3)));
		
	}
	
	@FXML
	void logOut() {
		//Las siguientes dos lineas para cerrar la anterior ventana
		Stage st = (Stage) adminTabPane.getScene().getWindow();
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
