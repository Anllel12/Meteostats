package application.control;

import java.io.IOException;

import application.main.GestionGson;
import application.main.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitMenuButton;
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
	private ComboBox<Int> seleccionRol;
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
		seleccionRol.
    }
	
	@FXML
	void registrarse() {
		GestionGson gg = new GestionGson();
		String u = usuario.getText().trim();
		String e = email.getText().trim();
		String n = nombre.getText().trim();
		String c = contrasena.getText().trim();
		String c2 = contrasena2.getText().trim();
		String ap = apellido.getText().trim();
		
		gg.registrarUsuario(new Usuario(null, null, null, null, null, 0))
		
	}
}
