package application.control;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import application.database.GestionUsuariosBBDD;
import application.main.Main;
import application.model.Usuario;
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
	
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		
		// Para validar que los campos no estan vacios
		RequiredFieldValidator rfv = new RequiredFieldValidator();
		
        usuario.getValidators().add(rfv);
        email.getValidators().add(rfv);
        nombre.getValidators().add(rfv);
        contrasena.getValidators().add(rfv);
        
        validacionContrasenas();
        
        apellido.getValidators().add(rfv);
        rfv.setMessage("Este campo es obligatorio");
        
        setListenerValidaction(usuario);
        setListenerValidaction(email);
        setListenerValidaction(nombre);
        setListenerValidaction(apellido);
		
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
			stage.getIcons().add(Main.ICONO_APP);
            stage.centerOnScreen();
            stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static int getRandomIdFromVector(Vector<Integer> vector) {
	    int rnd = new Random().nextInt(vector.size());
	    return vector.get(rnd);
	}
	
	@FXML
	void registrarse(ActionEvent event) {
		GestionUsuariosBBDD gestionUsuariosBBDD = new GestionUsuariosBBDD();
		String u = usuario.getText().trim();
		String e = email.getText().trim();
		String n = nombre.getText().trim();
		String c = contrasena.getText().trim();
		String ap = apellido.getText().trim();
		int rol = GestionUsuariosBBDD.ROL_USUARIO;
		// TODO asignar admin a cargo
		Vector<Integer> allAdmins = gestionUsuariosBBDD.getIdUsuariosByRol(GestionUsuariosBBDD.ROL_ADMIN);
		Vector<Integer> adminACargoRandom = new Vector<Integer>();
		adminACargoRandom.add(getRandomIdFromVector(allAdmins));
		System.out.println(adminACargoRandom.toString());
		
		Vector<Integer> allTecnicos = gestionUsuariosBBDD.getIdUsuariosByRol(GestionUsuariosBBDD.ROL_TECNICO);
		Vector<Integer> tecnicoACargoRandom = new Vector<Integer>();
		tecnicoACargoRandom.add(getRandomIdFromVector(allTecnicos));
		System.out.println(tecnicoACargoRandom.toString());
		
		
		
		if (!contrasena2.validate()) {
			
		} else if (!usuario.validate() || !email.validate() || !nombre.validate() || !contrasena.validate()
				|| !apellido.validate()) {
			
			
		} else {
			Usuario usReg = new Usuario(u, n, ap, c, e, rol, adminACargoRandom, tecnicoACargoRandom);
			int isOk = gestionUsuariosBBDD.registrarUsuario(usReg);
			if (isOk == GestionUsuariosBBDD.REG_ERROR_ESCRITURA) {
				errorAlertCreator("Error en el registro", "Error registrando usuario en BBDD");
			} else {
				isOk = gestionUsuariosBBDD.setAdminACargoUsuario(usReg, adminACargoRandom);
				if (isOk == GestionUsuariosBBDD.REG_ERROR_ESCRITURA) {
					errorAlertCreator("Error en la relacion con admins", "Error registrando admins a cargo de usuario en BBDD");
				} else {
					isOk = gestionUsuariosBBDD.setTecnicoACargoUsuario(usReg, tecnicoACargoRandom);
					if (isOk == GestionUsuariosBBDD.REG_ERROR_ESCRITURA) {
						errorAlertCreator("Error en la relacion con tecnicos", "Error registrando tecnicos a cargo de usuario en BBDD");
					} else {
						loadLogin(null);
					}
				}
			}
			
		}
		
		
		
	}
}
