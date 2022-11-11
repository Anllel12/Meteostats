package application.control;

import java.io.IOException;

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
        		//TODO CHECK USUARIO JSON
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
