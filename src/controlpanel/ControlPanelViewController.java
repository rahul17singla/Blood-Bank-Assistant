package controlpanel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlPanelViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblLogo;
    
    @FXML
    void showAvailable(ActionEvent event) {
        try {
        	Parent root = FXMLLoader.load(getClass().getResource("/bloodavailable/BloodStockView.fxml"));
        	Scene scene = new Scene(root);
        	Stage stage = new Stage();
        	stage.setScene(scene);
        	stage.show();
        	
        	//---------------------------------
        	
        	Scene scene1 = (Scene)lblLogo.getScene();
        	scene1.getWindow().hide();
        	
        }
        catch (Exception e) {
        	e.printStackTrace();
        }

    }

    @FXML
    void showBloodCollection(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/collection/BloodUnitCollection.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void showDeveloper(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/contactdeveloper/ContactView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void showDonorMaster(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/donormaster/DonorMasterView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void showDonorTable(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/userdatatable/userTableView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void showHistory(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/history/HistoryView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void showIssue(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/bloodissue/IssueBloodView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)lblLogo.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {

    }

}
