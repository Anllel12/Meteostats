package application.control;

import application.main.GestionGson;
import application.main.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class SignUpController {

	@FXML
	private TextField usuario;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField nombre;
	
	@FXML
	private TextField contrasena;
	
	@FXML
	private TextField contrasena2;
	
	@FXML
	private TextField apellido;
	
	@FXML
	private ComboBox<String> seleccionRol;
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		seleccionRol.getItems().addAll("Usuario", "Administrador", "Tecnico");
    }
	
	void errorAlertCreator(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
	
	@FXML
	void registrarse(ActionEvent event) {
		GestionGson gg = new GestionGson();
		String u = usuario.getText().trim();
		String e = email.getText().trim();
		String n = nombre.getText().trim();
		String c = contrasena.getText().trim();
		String c2 = contrasena2.getText().trim();
		String ap = apellido.getText().trim();
		String selected = seleccionRol.getSelectionModel().getSelectedItem();
		if (!c.equals(c2)) {
			
			errorAlertCreator("Error en el registro", "Las contraseñas introducidas no coinciden");
			
		} else if (u.isEmpty() || e.isEmpty() || n.isEmpty() || c.isEmpty() || c2.isEmpty()
				|| ap.isEmpty() || selected.isEmpty()) {
			
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
			
			gg.registrarUsuario(new Usuario(u, n, ap, c, e, rol));
		}
		
		
		
	}
}
