package application.control;

import java.io.IOException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import application.main.GestionGson;
import application.main.Usuario;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignUpController {

	@FXML
	private JFXTextField usuario;
	
	@FXML
	private JFXTextField email;
	
	@FXML
	private JFXTextField nombre;
	
	@FXML
	private JFXTextField contrasena;
	
	@FXML
	private JFXTextField contrasena2;
	
	@FXML
	private JFXTextField apellido;
	
	@FXML
	private JFXComboBox<String> seleccionRol;
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		seleccionRol.getItems().addAll("Usuario", "Administrador", "Tecnico");
		
		// Para validar que los campos no estan vacios
		RequiredFieldValidator rfv = new RequiredFieldValidator();
		
        usuario.getValidators().add(rfv);
        email.getValidators().add(rfv);
        nombre.getValidators().add(rfv);
        contrasena.getValidators().add(rfv);
        
        validacionContrasenas();
        
        apellido.getValidators().add(rfv);
        seleccionRol.getValidators().add(rfv);
        rfv.setMessage("Este campo es obligatorio");
        
        setListenerValidaction(usuario);
        setListenerValidaction(email);
        setListenerValidaction(nombre);
        setListenerValidaction(apellido);
        seleccionRol.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                seleccionRol.validate();
            }
        });
		
    }
	
	private void validacionContrasenas() {
		// Creamos un nuevo validador desde cero, para comparar pass1 y pass2 y mostrar
		// el mensaje de error
		ValidatorBase vb = new ValidatorBase() {
			
			@Override
			protected void eval() {
				if (srcControl.get() instanceof TextInputControl) {
					evalPassword();
				}
			}
			
			private void evalPassword() {
				TextInputControl textField = (TextInputControl) srcControl.get();
				String pass2 = textField.getText();
				String pass1 = contrasena.getText();
				hasErrors.set(false);
				try {
					if (!pass1.isEmpty()) {
						if (!pass1.equals(pass2)) {
							setMessage("Contraseñas no coinciden");
							throw new Exception("Password dont match");
						}
					}
				} catch (Exception e) {
					hasErrors.set(true);
				}
			}
		};
		
		contrasena2.getValidators().add(vb);
        contrasena.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                contrasena2.validate();
            }
        });
        contrasena2.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                contrasena2.validate();
            }
        });
		
	}

	private void setListenerValidaction(JFXTextField jf) {
		jf.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                jf.validate();
            }
        });
		
	}

	void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
	
	@FXML
	void loadLogin(MouseEvent event) {
		//Las siguientes dos lineas para cerrar la anterior ventana
		Stage st = (Stage) usuario.getScene().getWindow();
		st.close();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Login.fxml"));
        
		Stage stage = new Stage();
        // No hacer clickeables resto de ventanas abiertas
        stage.setTitle("Login");
        Parent root1;
		try {
			root1 = (Parent) loader.load();
			stage.setScene(new Scene(root1));
            stage.centerOnScreen();
            stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@FXML
	void registrarse(ActionEvent event) {
		GestionGson gg = new GestionGson();
		String u = usuario.getText().trim();
		String e = email.getText().trim();
		String n = nombre.getText().trim();
		String c = contrasena.getText().trim();
		String ap = apellido.getText().trim();
		String selected = seleccionRol.getSelectionModel().getSelectedItem();
		
		if (!contrasena2.validate()) {
			
			errorAlertCreator("Error en el registro", "Las contraseñas introducidas no coinciden");
			
		} else if (!usuario.validate() || !email.validate() || !nombre.validate() || !contrasena.validate()
				|| !apellido.validate() || !seleccionRol.validate()) {
			
			errorAlertCreator("Error en el registro", "Existen campos obligatorios vacíos");
			
		} else {
			int rol = 0;
			switch (selected) {
			case "Usuario":
				rol = GestionGson.ROL_USUARIO;
				break;
			case "Administrador":
				rol = GestionGson.ROL_ADMIN;
				break;
			case "Tecnico":
				rol = GestionGson.ROL_TECNICO;
				break;

			default:
				break;
			}
			int isOk = gg.registrarUsuario(new Usuario(u, n, ap, c, e, rol));
			if (isOk == GestionGson.REG_ERROR_ESCRITURA) {
				errorAlertCreator("Error en el registro", "Error registrando usuario en JSON");
			} else if (isOk == GestionGson.REG_ERROR_MISMO_USUARIO) {
				errorAlertCreator("Error en el registro", "El usuario introducido ya existe en la BBDD");
				// Pone el texto del textfield de color rojo, puede ser util
			} else {
				loadLogin(null);
			}
			
		}
		
		
		
	}
}
