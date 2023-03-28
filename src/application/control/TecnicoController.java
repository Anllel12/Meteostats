package application.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import application.database.GestionMensajeriaBBDD;
import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.GestionGson;
import application.model.MensajeObj;
import application.model.Tiempo;
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
import javafx.stage.Modality;
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
	    
	    @FXML
		private TableView<MensajeObj> tbSugerencias;
		
		@FXML
		private TableColumn<MensajeObj, String> sFecha;
		
	    @FXML
	    private TableColumn<MensajeObj, String> sDesc;
	    
	    @FXML
	    private TableColumn<MensajeObj, String> sEstado;
	    
	    private MensajeObj selectedMsg;
	    
	    @FXML
	    private JFXTextArea comuncarAdmTxt;
	    
	    @FXML
	    private JFXComboBox<String> cbEleccion;
	    
	    @FXML
	    private JFXComboBox<String> cbAdmins;
	    
	    @FXML
	    private Vector<Vector<String>> adminsAndIds;
	    
	    private String adminSelected;
	    
	    private static final String SELEC_RESET_HISTORIAL = "Borrar TODO el historial del tiempo";
	    private static final String SELEC_BORRAR_USUARIOS = "Borrar BBDD de todos los usuarios";
	    private static final String SELEC_BORRAR_MENSAJES = "Borrar TODOS los mensajes";
	
	    @FXML
	   	void initialize() {
	    	cbAdmins.setPromptText("Selecciona un administrador");
	    	rellenarComboBoxAdmins();
	    	cbAdmins.valueProperty().addListener((ov, p1, p2) -> {
	    	    adminSelected = p2;
	    	});
	    	//Como primer tab en cargar es sugerencias cargamos mensajes
//	    	sugerenciasTabla();
	    	
//	   		tabPaneTecnico.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)->{
//	               selectedTab(nv.getText());
//	               System.out.println(nv.getText() + nv.getId());
//	               
//	   		});
	   		cbEleccion.getItems().addAll(SELEC_BORRAR_MENSAJES, SELEC_BORRAR_USUARIOS, SELEC_RESET_HISTORIAL);
	   		
	   		tbMsg.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MensajeObj>() {

				@Override
				public void changed(ObservableValue<? extends MensajeObj> observable, MensajeObj oldValue,
						MensajeObj newValue) {
					if (tbMsg.getSelectionModel().getSelectedItem() != null) {
						selectedMsg = tbMsg.getSelectionModel().getSelectedItem();
					}
					
				}
			});
	   		
	   		tbSugerencias.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MensajeObj>() {

				@Override
				public void changed(ObservableValue<? extends MensajeObj> observable, MensajeObj oldValue,
						MensajeObj newValue) {
					if (tbSugerencias.getSelectionModel().getSelectedItem() != null) {
						selectedMsg = tbSugerencias.getSelectionModel().getSelectedItem();
					}
					
				}
			});
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
	   	
//	   	private void selectedTab(String tabTitle) {
//	   		switch (tabTitle) {
//	   		case "Sugerencias":
//	   			selectedMsg = null;
//	   			sugerenciasTabla();
//	   			
//	   			break;
//	   			
//	   		case "Errores":
//	   			selectedMsg = null;
//	   			erroresTabla();
//	   			
//	   			break;
	   			
	   		/*case "Arreglos":
	   			
	   			
	   			break;*/
	   			
	   		/*case "Comunicar a Administrador":
	   			// TODO
	   			// comunicarAdministrador();
	   			
	   			break;*/
//
//	   		default:
//	   			break;
//	   		}
//	   	}

	   	@FXML
	   	void aplicarArreglo() {
//	   		if (cbEleccion.getSelectionModel().getSelectedItem() != null) {
//	   			switch (cbEleccion.getSelectionModel().getSelectedItem()) {
//					case SELEC_BORRAR_USUARIOS:
//						try {
//							PrintWriter pw = new PrintWriter(GestionGson.RUTA_JSON);
//							pw.close();
//						} catch (FileNotFoundException e) {
//							errorAlertCreator("ERROR", "No se ha podido limpiar " + GestionGson.RUTA_JSON);
//							e.printStackTrace();
//						}
//						break;
//						
//					case SELEC_BORRAR_MENSAJES:
//						try {
//							PrintWriter pw = new PrintWriter(Mensajeria.JSON_MENSAJERIA);
//							pw.close();
//						} catch (FileNotFoundException e) {
//							errorAlertCreator("ERROR", "No se ha podido limpiar " + Mensajeria.JSON_MENSAJERIA);
//							e.printStackTrace();
//						}
//						break;
//						
//					case SELEC_RESET_HISTORIAL:
//						try {
//							PrintWriter pw = new PrintWriter(Tiempo.JSON_TIEMPO);
//							pw.close();
//						} catch (FileNotFoundException e) {
//							errorAlertCreator("ERROR", "No se ha podido limpiar " + Tiempo.JSON_TIEMPO);
//							e.printStackTrace();
//						}
//						break;
//				}
//	   		} else {
//	   			errorAlertCreator("ERROR", "Selecciona una opcion");
//	   		}
	   	}
	   	
	   	
//	   	@FXML
//		void comunicarAdministrador() {
//			Mensajeria mg = new Mensajeria();
//	    	int from = GestionGson.ROL_TECNICO;	
//	    	String mensaje = comuncarAdmTxt.getText().trim();
//	    	Vector<String> toAdmin = new Vector<String>();
//	    	// TODO meter en to el valor de combobox (getIdAdminByNombre())
//	    	toAdmin.add(Mensajeria.TO_ADMIN);
//	    	if (!mensaje.isEmpty()) {
//	    		int isOk = mg.writeNewMessage(mensaje, from, toAdmin);
//	    		if (isOk == Mensajeria.ERROR_ESCRITURA) {
//	    			errorAlertCreator("Error","No se ha podido enviar el mensaje");
//	    		} else if (isOk == Mensajeria.ESCRITURA_OK) {
//	    			errorAlertCreator("Completado","El mensaje se ha enviado correctamente");
//	    		}
//	    	} else {
//	    		errorAlertCreator("Error","El mensaje no puede estar vacio");
//	    	}
//			
//		}
//
//		private void erroresTabla() {
//			fecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
//			desc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
//			status.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
//			loadMessagesTabla();
//			
//		}
//		
//		private void loadMessagesTabla() {
//			GestionMensajeriaBBDD gMens = new GestionMensajeriaBBDD();
//			Vector<MensajeObj> mensajes = gMens.getMessages(LogInController.USUARIO_LOGUEADO);
//			Vector<MensajeObj> msg_aux = new Vector<>();	
//			for (MensajeObj mensajeObj : mensajes) {
//				if (mensajeObj.getFrom() == Mensajeria.CLIENTE_ERROR) {
//					msg_aux.add(mensajeObj);
//				}
//			}
//			tbMsg.getItems().setAll(msg_aux);
//		}
//		
//		@FXML
//		void menuTablaEliminar() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				mensajeria.deleteMessage(selectedMsg);
//				errorAlertCreator("OK", "Mensaje eliminado correctamente");
//				loadMessagesTabla();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//		
//		@FXML
//		void menuTablaEliminarSugerencias() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				mensajeria.deleteMessage(selectedMsg);
//				errorAlertCreator("OK", "Mensaje eliminado correctamente");
//				loadMessagesTablaSugerencias();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//		
//		@FXML
//		void menuTablaReestablecerStatus() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				selectedMsg.setStatus(Mensajeria.STATUS_INICIAL);
//				mensajeria.modifyMessage(selectedMsg);
//				errorAlertCreator("OK", "Status reestablecido");
//				loadMessagesTabla();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//		
//		@FXML
//		void menuTablaReestablecerStatusSugerencias() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				selectedMsg.setStatus(Mensajeria.STATUS_INICIAL);
//				mensajeria.modifyMessage(selectedMsg);
//				errorAlertCreator("OK", "Status reestablecido");
//				loadMessagesTablaSugerencias();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//		
//		@FXML
//		void menuTablaSetPendienteSugerencias() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				selectedMsg.setStatus(Mensajeria.STATUS_PENDIENTE);
//				mensajeria.modifyMessage(selectedMsg);
//				errorAlertCreator("OK", "Mensaje set como corregido");
//				loadMessagesTablaSugerencias();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//		
//		@FXML
//		void menuTablaSetPendiente() {
//			if (selectedMsg != null) {
//				Mensajeria mensajeria = new Mensajeria();
//				selectedMsg.setStatus(Mensajeria.STATUS_PENDIENTE);
//				mensajeria.modifyMessage(selectedMsg);
//				errorAlertCreator("OK", "Mensaje set como corregido");
//				loadMessagesTabla();
//			} else {
//				errorAlertCreator("OK", "Ningun mensaje seleccionado");
//			}
//		}
//
//		private void sugerenciasTabla() {
//			sFecha.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("fecha"));
//			sDesc.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("descripcion"));
//			sEstado.setCellValueFactory(new PropertyValueFactory<MensajeObj, String>("status"));
//			loadMessagesTablaSugerencias();
//			
//		}
//		
//		private void loadMessagesTablaSugerencias() {
//			Mensajeria mensajeria = new Mensajeria();
//			Vector<MensajeObj> mensajes = mensajeria.getMessages(LogInController.USUARIO_LOGUEADO);
//			Vector<MensajeObj> msg_aux = new Vector<>();	
//			for (MensajeObj mensajeObj : mensajes) {
//				if (mensajeObj.getFrom() == Mensajeria.CLIENTE_SUGERENCIA || mensajeObj.getTo().contains(Mensajeria.TO_TECNICO)) {
//					msg_aux.add(mensajeObj);
//				}
//			}
//			tbSugerencias.getItems().setAll(msg_aux);
//		}
		
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
