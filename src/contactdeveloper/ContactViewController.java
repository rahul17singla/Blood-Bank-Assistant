package contactdeveloper;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ContactViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label lblmsg;

    @FXML
    void doBack(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/controlpanel/ControlPanelView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblmsg.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doMail(MouseEvent event) {
    	try {
			Desktop.getDesktop().mail(new URI("mailto:rahulsingla1710@gmail.com?subject=Blood_Bank_Assistant"));;

		} 
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void showGithub(MouseEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("https://github.com/rahul17singla"));

		} 
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void showLinkedin(MouseEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/rahul-1710-singla"));

		} 
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void showWebsite(MouseEvent event) {
    	try {
			Desktop.getDesktop().browse(new URI("https://rahul1710.netlify.app"));
    		
    	} 
    	catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    @FXML
    void initialize() {

    }

}
