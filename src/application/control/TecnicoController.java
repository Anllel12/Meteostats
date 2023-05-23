package application.control;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.database.GestionMensajeriaBBDD;
import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.MensajeObj;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TecnicoController {
	@FXML
	private TabPane tabPaneTecnico;

	@FXML
	private TableView<MensajeObj> tbMsg;

	@FXML
	private TableColumn<MensajeObj, String> fecha;

	@FXML
	private TableColumn<MensajeObj, String> desc;

	@FXML
	private TableColumn<MensajeObj, String> status;

	private MensajeObj selectedMsg;

	@FXML
	private JFXTextArea comuncarAdmTxt;

	@FXML
	private JFXComboBox<String> cbEleccion;
	
	@FXML
	private JFXComboBox<String> selecCliente;

	@FXML
	private JFXComboBox<String> cbAdmins;

	@FXML
	private Vector<Vector<String>> adminsAndIds;

	private String adminSelected;
	
	private String arregloSeleccionado;

	private static final String SELEC_RESET_HISTORIAL_CLIENTE = "Borrar historial de cliente";
	private static final String SELEC_BORRAR_CLIENTE = "Borrar un cliente";

	@FXML
	void initialize() {
		cbAdmins.setPromptText("Selecciona un administrador");
		rellenarComboBoxAdmins();
		cbAdmins.valueProperty().addListener((ov, p1, p2) -> {
			adminSelected = p2;
		});
		//Como primer tab en cargar es tabla de errores cargamos mensajes
		erroresTabla();

		tabPaneTecnico.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
			selectedTab(nv.getText());
			System.out.println(nv.getText() + nv.getId());

		});
		cbEleccion.getItems().addAll(SELEC_RESET_HISTORIAL_CLIENTE, SELEC_BORRAR_CLIENTE);
		
		cbEleccion.valueProperty().addListener((ov, p1, p2) -> {
			arregloSeleccionado = p2;
		});
		
		ArrayList<String> clientesACargo = getClientesACargo();
		selecCliente.getItems().addAll(clientesACargo);
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

	private ArrayList<String> getClientesACargo() {
		GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
		Vector<Vector<String>> arrarUsuariosAndId = gestionUsuariosBBDD.getUsuariosACargoTecnico(LogInController.USUARIO_LOGUEADO.getUsuario());
		ArrayList<String> nombresUsuarios = new ArrayList<>();
		if (arrarUsuariosAndId.size() > 0) {
			for (Vector<String> usuario : arrarUsuariosAndId) {
				nombresUsuarios.add(usuario.get(0));
			}
		} else {
			nombresUsuarios.add("SIN CLIENTES A CARGO");		
		}
		return nombresUsuarios;
		
	}

	private void rellenarComboBoxAdmins() {
		GestionUsuariosBBDD gub = new GestionUsuariosBBDD();
		Vector<Vector<String>> admins = gub.getUsuarioAndIdByRol(GestionUsuariosBBDD.ROL_ADMIN);
		ArrayList<String> nombresUsuarios = new ArrayList<>();
		for (Vector<String> usuario : admins) {
			nombresUsuarios.add(usuario.get(0));
		}
		adminsAndIds = admins;
		cbAdmins.getItems().addAll(nombresUsuarios);

	}

	// TODO meter en TO de mensaje enviado
	private int getIdAdminByNombre() {
		for (Vector<String> vector : adminsAndIds) {
			if (vector.get(0).equals(adminSelected)) {
				return Integer.valueOf(vector.get(1));
			}
		}
		return -1;
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

		case "Errores":
			selectedMsg = null;
			erroresTabla();

			break;

			/*case "Arreglos":


	   			break;*/

		case "Comunicar a Administrador":

			//comunicarAdministrador();

			break;

		default:
			break;
		}
	}

	@FXML
	void aplicarArreglo() {
		if (arregloSeleccionado.equals(SELEC_BORRAR_CLIENTE)) {
			if (selecCliente.getSelectionModel().getSelectedItem() != null) {
				GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
				int res = gestionUsuariosBBDD.deleteCliente(selecCliente.getSelectionModel().getSelectedItem());
				if (res == 0) {
					errorAlertCreator("Cliente borrado ok", selecCliente.getSelectionModel().getSelectedItem());
				} else {
					errorAlertCreator("Error borrando cliente", selecCliente.getSelectionModel().getSelectedItem());
				}
			}
		} else if (arregloSeleccionado.equals(SELEC_RESET_HISTORIAL_CLIENTE)) {
			if (selecCliente.getSelectionModel().getSelectedItem() != null) {
				GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
				int res = gestionUsuariosBBDD.deleteHistorial(selecCliente.getSelectionModel().getSelectedItem());
				if (res == 0) {
					errorAlertCreator("Historial de cliente borrado ok", selecCliente.getSelectionModel().getSelectedItem());
				} else {
					errorAlertCreator("Error borrando historial cliente", selecCliente.getSelectionModel().getSelectedItem());
				}
			}
		}
//		if (cbEleccion.getSelectionModel().getSelectedItem() != null) {
//			switch (cbEleccion.getSelectionModel().getSelectedItem()) {
//			case SELEC_BORRAR_USUARIOS:
//				try {
//					PrintWriter pw = new PrintWriter(GestionGson.RUTA_JSON);
//					pw.close();
//				} catch (FileNotFoundException e) {
//					errorAlertCreator("ERROR", "No se ha podido limpiar " + GestionGson.RUTA_JSON);
//					e.printStackTrace();
//				}
//				break;
//
//			case SELEC_BORRAR_MENSAJES:
//				try {
//					PrintWriter pw = new PrintWriter(Mensajeria.JSON_MENSAJERIA);
//					pw.close();
//				} catch (FileNotFoundException e) {
//					errorAlertCreator("ERROR", "No se ha podido limpiar " + Mensajeria.JSON_MENSAJERIA);
//					e.printStackTrace();
//				}
//				break;
//
//			case SELEC_RESET_HISTORIAL:
//				try {
//					PrintWriter pw = new PrintWriter(Tiempo.JSON_TIEMPO);
//					pw.close();
//				} catch (FileNotFoundException e) {
//					errorAlertCreator("ERROR", "No se ha podido limpiar " + Tiempo.JSON_TIEMPO);
//					e.printStackTrace();
//				}
//				break;
//			}
//		} else {
//			errorAlertCreator("ERROR", "Selecciona una opcion");
//		}
	}


	@FXML
	void comunicarAdministrador() {
		GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
    	GestionUsuariosBBDD gUser = new GestionUsuariosBBDD();
    	int from = gUser.getIdUsuarioByUsuario(LogInController.USUARIO_LOGUEADO.getUsuario());
    	int to = gUser.getIdUsuarioByUsuario(adminSelected);   	
    	String mensaje = comuncarAdmTxt.getText().trim();
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
	//
	private void erroresTabla() {
		fecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
		desc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
		status.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
		loadMessagesTabla();
	}

	private void loadMessagesTabla() {
		GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
		Vector<MensajeObj> mensajes = gMens.getMessages(LogInController.USUARIO_LOGUEADO);
		tbMsg.getItems().setAll(mensajes);
	}

	@FXML
	void menuTablaEliminar() {
		if (selectedMsg != null) {
			GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
			gMens.deleteMessage(selectedMsg);
			errorAlertCreator("OK", "Mensaje eliminado correctamente");
			loadMessagesTabla();
		} else {
			errorAlertCreator("OK", "Ningun mensaje seleccionado");
		}
	}
	@FXML
	void menuTablaReestablecerStatus() {
		if (selectedMsg != null) {
			GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
			selectedMsg.setStatus(GestionMensajeriaBBDD.STATUS_INICIAL);
			gMens.modifyMessage(selectedMsg);
			errorAlertCreator("OK", "Status reestablecido");
			loadMessagesTabla();
		} else {
			errorAlertCreator("OK", "Ningun mensaje seleccionado");
		}
	}

	@FXML
	void menuTablaSetPendiente() {
		if (selectedMsg != null) {
			GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
			selectedMsg.setStatus(GestionMensajeriaBBDD.STATUS_PENDIENTE);
			gMens.modifyMessage(selectedMsg);
			errorAlertCreator("OK", "Mensaje set como corregido");
			loadMessagesTabla();
		} else {
			errorAlertCreator("OK", "Ningun mensaje seleccionado");
		}
	}

	@FXML
	void logOut() {
		//Las siguientes dos lineas para cerrar la anterior ventana
		Stage st = (Stage) tabPaneTecnico.getScene().getWindow();
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
