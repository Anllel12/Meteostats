package application.control;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;

import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.GestionGson;
import application.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogInController {
	
	@FXML
    private JFXButton btnEntrar;
	
	@FXML
	private JFXTextField txtUser;
	
	@FXML
	private JFXPasswordField txtPass;
	
	@FXML
	private Hyperlink hyperlinkRegistro;
	
	@FXML
	private AnchorPane view;
	
	@FXML
	private Pane ventanaRegistro;
	
	crearValidacionUsuario cv;
	crearValidacionUsuario cv2;
	
	public static Usuario USUARIO_LOGUEADO = null;
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		/**
		 * VALIDADORES DE CAMPOS VACIOS, PREFIERO LOS OTROS CREADOS
		 * 
		 RequiredFieldValidator rfv = new RequiredFieldValidator();
		rfv.setMessage("No puede estar vacío");
        txtUser.getValidators().add(rfv);
        txtUser.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtUser.validate();
            }
        });
        txtPass.getValidators().add(rfv);
        txtPass.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPass.validate();
            }
        });
		 */
		// btnEntrar.setStyle(" -fx-background-color: #3ac48d;-fx-text-fill: white");
        
		cv = new crearValidacionUsuario();
        txtUser.getValidators().add(cv);
        cv.setMsg("Usuario no encontrado");
        
        cv2 = new crearValidacionUsuario();
        txtPass.getValidators().add(cv2);
        cv2.setMsg("Contraseña incorrecta");
        
	}
	
	private class crearValidacionUsuario extends ValidatorBase {
		// Creando nuevo validador para mostrar error si el usuario no se encuentra
			Boolean error = false;
			String txt = "";
			@Override
			protected void eval() {
				evalUser();
			}
			
			public void setError(Boolean b) {
				this.error = b;
			}
			
			public void setMsg(String msg) {
				this.txt = msg;
			}
			
			private void evalUser() {

				hasErrors.set(false);
				try {
					if (error) {
						setMessage(txt);
						throw new Exception("Password dont match");
					}
				} catch (Exception e) {
					hasErrors.set(true);
				}
			}
		};
	
	void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
	
	@FXML
	public void loguear(ActionEvent event)  {
        try {
        	String usuario = txtUser.getText().trim();
        	String contra = txtPass.getText().trim();
        	
        	cv2.setError(false);
    		cv.setError(false);
    		txtPass.validate();
    		txtUser.validate();
        	
        	if (usuario.isEmpty() || contra.isEmpty()) {
				errorAlertCreator("Error de inicio de sesión", "Existen campos vacíos");
        	} else {
        		
        		GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
        		switch (gestionUsuariosBBDD.loginUsuarioAux(usuario, contra)) {
					case GestionUsuariosBBDD.LOG_CONTRA_INCORRECTA:
						cv2.setError(true);
						txtPass.validate();
						cv.setError(true);
						txtUser.validate();
						break;
						
					case GestionUsuariosBBDD.LOG_OK:
						Usuario us = gestionUsuariosBBDD.getUsuarioActual();
						USUARIO_LOGUEADO = us;
						
						if (us.getRol() == GestionGson.ROL_ADMIN) {
							//Abrir parte admin
							Stage currentSt = (Stage) view.getScene().getWindow();
							currentSt.close();
				            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/administrador.fxml"));
				            ventanaRegistro = loader.load();
				            Stage st = new Stage();
				            // No hacer clickeables resto de ventanas abiertas
				            st.initModality(Modality.APPLICATION_MODAL);
				            st.setScene(new Scene(ventanaRegistro));
				            st.getIcons().add(Main.ICONO_APP);
				            st.setTitle("Admin: " + us.getUsuario() + " - Nombre: " + us.getNombre());
				            st.centerOnScreen();
				            st.show();
						} else if (us.getRol() == GestionGson.ROL_USUARIO) {
							Stage currentSt = (Stage) view.getScene().getWindow();
							currentSt.close();
				            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/cliente.fxml"));
				            ventanaRegistro = loader.load();
				            Stage st = new Stage();
				            // No hacer clickeables resto de ventanas abiertas
				            st.initModality(Modality.APPLICATION_MODAL);
				            st.setScene(new Scene(ventanaRegistro));
				            st.getIcons().add(Main.ICONO_APP);
				            st.setTitle("Cliente: " + us.getUsuario() + " - Nombre: " + us.getNombre());
				            st.centerOnScreen();
				            st.show();
						} else if (us.getRol() == GestionGson.ROL_TECNICO) {
							Stage currentSt = (Stage) view.getScene().getWindow();
							currentSt.close();
				            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/tecnico.fxml"));
				            ventanaRegistro = loader.load();
				            Stage st = new Stage();
				            // No hacer clickeables resto de ventanas abiertas
				            st.initModality(Modality.APPLICATION_MODAL);
				            st.setScene(new Scene(ventanaRegistro));
				            st.getIcons().add(Main.ICONO_APP);
				            st.setTitle("Tecnico: " + us.getUsuario() + " - Nombre: " + us.getNombre());
				            st.centerOnScreen();
				            st.show();
						} else {
							errorAlertCreator("ROL " + us.getRol(), "ROL NO ENCONTRADO");
						}
						
						break;
				}
        		
        		
        		
			}
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@FXML
	public void ventanaRegistro(ActionEvent event)  {
        try {
        	Stage currentSt = (Stage) view.getScene().getWindow();
			currentSt.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Signup.fxml"));
            ventanaRegistro = loader.load();
            Stage st = new Stage();
            // No hacer clickeables resto de ventanas abiertas
            st.initModality(Modality.APPLICATION_MODAL);
            st.setScene(new Scene(ventanaRegistro));
            st.getIcons().add(Main.ICONO_APP);
            st.setTitle("Registro de usuario");
            st.centerOnScreen();
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}
}
