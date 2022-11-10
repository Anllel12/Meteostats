package application.control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LogInController {
	
	@FXML
    private Button btnEntrar;
	
	@FXML
	private TextField txtUser;
	
	@FXML
	private TextField txtPass;
	
	@FXML
	private Label txtRegistro;
	
	@FXML
	private AnchorPane view;
	
	@FXML
	private Pane ventanaRegistro;
	
	@FXML
	public void loguear(ActionEvent event)  {
        try {
        	//TODO
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ventanaPrincipal.fxml"));
            view = loader.load();
            Stage st = new Stage();
            st.setScene(new Scene(view));
            st.centerOnScreen();
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@FXML
	public void ventanaRegistro(MouseEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Signup.fxml"));
            ventanaRegistro = loader.load();
            Stage st = new Stage();
            st.setScene(new Scene(ventanaRegistro));
            st.centerOnScreen();
            st.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}
}
