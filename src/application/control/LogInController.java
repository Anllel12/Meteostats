package application.control;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;

import application.main.GestionGson;
import application.main.Usuario;
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
	private JFXTextField txtPass;
	
	@FXML
	private Hyperlink hyperlinkRegistro;
	
	@FXML
	private AnchorPane view;
	
	@FXML
	private Pane ventanaRegistro;
	
	crearValidacionUsuario cv;
	crearValidacionUsuario cv2;
	
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
        		
        		GestionGson gg = new GestionGson();
        		switch (gg.loginUsuario(usuario, contra)) {
					case GestionGson.LOG_CONTRA_INCORRECTA:
						cv2.setError(true);
						txtPass.validate();
						break;
					case GestionGson.LOG_USUARIO_NO_ENCONTRADO:
						cv.setError(true);
						txtUser.validate();
						break;
					case GestionGson.LOG_OK:
						Usuario us = gg.getUsuarioActual();
						errorAlertCreator("LOGGING OK", us.getUsuario());
						
						//TODO cargar ventana principal
						FXMLLoader loader = new FXMLLoader(getClass().getResource("ventanaPrincipal.fxml"));
			            view = loader.load();
			            Stage st = new Stage();
			            st.setScene(new Scene(view));
			            st.centerOnScreen();
			            st.show();
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
            st.setTitle("Registro de usuario");
            st.centerOnScreen();
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}
}
