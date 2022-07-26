package bloodissue;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class IssueBloodViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> bloodgrp;

    @FXML
    private TextField hospital;

    @FXML
    private DatePicker issueDate;

    @FXML
    private Label lblmsg;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;

    @FXML
    private TextField reason;

    @FXML
    private TextField units;
    
    Connection con;
    PreparedStatement pst;
    ResultSet table;
    String bloodGroup="";
    

    void showErrorMsg(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR !!!");
		alert.setContentText(msg);
		alert.show();
	}
    void getSelectedBGrp() {
		String bg = bloodgrp.getValue();
		
		if(bg.equals("A+")) {
			bloodGroup="apos";
		}
		else if(bg.equals("A-")) {
			bloodGroup="aneg";
		}
		else if(bg.equals("B+")) {
			bloodGroup="bpos";
		}
		else if(bg.equals("B-")) {
			bloodGroup="bneg";
		}
		else if(bg.equals("AB+")) {
			bloodGroup="abpos";
		}
		else if(bg.equals("AB-")) {
			bloodGroup="abneg";
		}
		else if(bg.equals("O+")) {
			bloodGroup="opos";
		}
		else if(bg.equals("O-")) {
			bloodGroup="oneg";
		}
	}
	
    boolean isUnitsAvailable(int u, String bgp) {
    	boolean flag = false;
    	try {
//    		System.out.println(bgp);
			pst=con.prepareStatement("select * from total_blood_record");
			table=pst.executeQuery();
			while(table.next()) {
//				System.out.println(table.getInt(bgp));
				if(u<=table.getInt(bgp)) {
					flag=true;
				}
			}
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return flag;
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
			
			Scene scene1 = (Scene)bloodgrp.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doClear(ActionEvent event) {
    	bloodgrp.setValue(null);
    	hospital.setText(null);
    	issueDate.setValue(null);
    	lblmsg.setText(null);
    	mobile.setText(null);
    	name.setText(null);
    	reason.setText(null);
    	units.setText(null);
    }

    @FXML
    void uploadData(ActionEvent event) {
    	if(bloodgrp.getSelectionModel().isEmpty()) {
	    	if(bloodgrp.getSelectionModel().isEmpty()||name.getText().equals(null)||mobile.getText().equals(null)||reason.getText().equals(null)||units.getText().equals(null)||hospital.getText().equals(null))
	    	{
	    		showErrorMsg("Do not leave Empty Fields !!!");
	    	}
	    	else if(String.valueOf(issueDate.getValue()).equals(null)) {
	    		showErrorMsg("Please enter the Date !!!");
	    	}
    	}
    	else {
	    	getSelectedBGrp();
	    	if(isUnitsAvailable(Integer.parseInt(units.getText()),bloodGroup)==false) {
	    		showErrorMsg("Demanded Number of Units are not Available !!!");
	    	}
	    	else {
		    	try {
		    		
		    		pst=con.prepareStatement("insert into issue_blood values(?,?,?,?,?,?,?)");
					pst.setString(1, name.getText());
					pst.setString(2, mobile.getText());
					pst.setString(3, bloodGroup);
					pst.setString(4, hospital.getText());
					pst.setString(5, reason.getText());
					pst.setString(6, String.valueOf(issueDate.getValue()));
					pst.setInt(7, Integer.parseInt(units.getText()));
					
					pst.executeUpdate();
	
					lblmsg.setText("Data Uploaded Successfully");
	
		    	}
		    	catch(Exception e) {
		    		e.printStackTrace();
		    	}
		    	
		    	try {
		    		String bg = bloodGroup;
					pst=con.prepareStatement("update total_blood_record set "+bg+"="+bg+"-?");
					pst.setInt(1, Integer.parseInt(units.getText()));
					
					pst.executeUpdate();
					
					lblmsg.setText("Data Uploaded Successfully");
		    	}
		    	catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	}
    }

    @FXML
    void initialize() {
    	ArrayList<String> bloodGroups = new ArrayList<String>(Arrays.asList("AB+","AB-","A+","A-","B+","B-","O+","O-"));
    	bloodgrp.getItems().setAll(bloodGroups);
    	con = DatabaseConnection.doConnect();

    }

}
