package bloodavailable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class BloodStockViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField abneg;

    @FXML
    private TextField abpos;

    @FXML
    private TextField aneg;

    @FXML
    private TextField apos;

    @FXML
    private TextField bneg;

    @FXML
    private TextField bpos;

    @FXML
    private TextField oneg;

    @FXML
    private TextField opos;

    Connection con;
    PreparedStatement pst;
    ResultSet table;
    
    void showStockAvailable() {
    	try {
			pst=con.prepareStatement("select * from total_blood_record");
			table = pst.executeQuery();
			
			while(table.next()) {
				abpos.setText(String.valueOf(table.getInt("abpos")));
				abneg.setText(String.valueOf(table.getInt("abneg")));
				apos.setText(String.valueOf(table.getInt("apos")));
				aneg.setText(String.valueOf(table.getInt("aneg")));
				bpos.setText(String.valueOf(table.getInt("bpos")));
				bneg.setText(String.valueOf(table.getInt("bneg")));
				opos.setText(String.valueOf(table.getInt("opos")));
				oneg.setText(String.valueOf(table.getInt("oneg")));
				
			}
			
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void doBack(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/controlpanel/ControlPanelView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			//---------------------------------
			
			Scene scene1 = (Scene)abneg.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void initialize() {
    	con = DatabaseConnection.doConnect();
    	showStockAvailable();

    }

}
