package application.control;

import java.io.IOException;

import application.main.GestionGson;
import application.main.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogInController {
	
	@FXML
    private Button btnEntrar;
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private TextField txtPass;
	
	@FXML
	private Hyperlink hyperlinkRegistro;
	
	@FXML
	private AnchorPane view;
	
	@FXML
	private Pane ventanaRegistro;
	
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
        	
        	if (usuario.isEmpty() || contra.isEmpty()) {
				errorAlertCreator("Error de inicio de sesión", "Existen campos vacíos");
        	} else {
        		GestionGson gg = new GestionGson();
        		switch (gg.loginUsuario(usuario, contra)) {
					case GestionGson.LOG_CONTRA_INCORRECTA:
						errorAlertCreator("Error de inicio de sesión", "Contraseña incorrecta");
						break;
					case GestionGson.LOG_USUARIO_NO_ENCONTRADO:
						errorAlertCreator("Error de inicio de sesión", "El nombre de usuario no corresponde a ninguna cuenta");
						break;
					case GestionGson.LOG_OK:
						Usuario us = gg.getUsuarioActual();
						errorAlertCreator("LOGGING OK", us.getUsuario());
						break;
				}
        		
        		
        		//TODO cargar ventana principal
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ventanaPrincipal.fxml"));
	            view = loader.load();
	            Stage st = new Stage();
	            st.setScene(new Scene(view));
	            st.centerOnScreen();
	            st.show();
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
            st.centerOnScreen();
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}
}
