package donormaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class DonorMasterViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address;

    @FXML
    private TextField age;

    @FXML
    private ComboBox<String> bloodgrp;

    @FXML
    private TextField city;

    @FXML
    private TextField disease;

    @FXML
    private RadioButton female;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Rectangle imgRect;
    
    @FXML
    private Label lblmsg;

    @FXML
    private RadioButton male;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;

    @FXML
    private ImageView picView;
    
    Connection con;
    PreparedStatement pst;
    ResultSet table;
    
    private String picPath="";
    
    private String gend="";
    
    void getGender() {
    	if(male.isSelected()) {
    		gend="Male";
    	}
    	else if(female.isSelected()) {
    		gend="Female";
    	}
    }
    
    void showErrorMsg(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR !!!");
		alert.setContentText(msg);
		alert.show();
	}
    
    boolean checkMob(String mob)
    {
    	boolean flag=false;
    	
    	try {
    		pst=con.prepareStatement("select * from donors where mobile=?");
    		pst.setString(1, mob);
    		table = pst.executeQuery();
    		
    		while(table.next())
    		{
    			flag=true;
    		}
		}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	
    	return flag;
    }

    @FXML
    void cancel(ActionEvent event) {
		try {
			pst=con.prepareStatement("delete from donors where mobile=?");
			pst.setString(1, mobile.getText());
			int count = pst.executeUpdate();
			if (count==0) {
				showErrorMsg("Invalid Mobile Number !!!");
			} 
			else {
				lblmsg.setText("Your registration is now cancelled");
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
			
			Scene scene1 = (Scene)city.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void findUser(ActionEvent event) {
    	if(checkMob(mobile.getText())==false)
    	{
    		showErrorMsg("No Record Found with this Mobile Number");
    		return;
    	}
    	try {
    		pst=con.prepareStatement("select * from donors where mobile=?");
    		pst.setString(1, mobile.getText());
    		table = pst.executeQuery();
    		
    		while(table.next())
    		{
    			mobile.setText(table.getString("mobile"));
    			name.setText(table.getString("name"));
    			
    			String g = table.getString("gender");
    			if(g.equals("Male"))
    			{
    				male.setSelected(true);
    			}
    			else if(g.equals("Female"))
    			{
    				female.setSelected(true);
    			}
    			
    			Image img = new Image(new FileInputStream(table.getString("picPath")));
    			picView.setImage(img);
    			imgRect.setVisible(false);
    			
    			address.setText(table.getString("address"));
    			city.setText(table.getString("city"));
    			bloodgrp.setValue(table.getString("bgroup"));
    			age.setText(String.valueOf(table.getInt("age")));
    			disease.setText(table.getString("disease"));
    			
    		}
		}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	
    }

    @FXML
    void newuser(ActionEvent event) {
    	mobile.setText("");
    	name.setText("");
    	address.setText("");
    	city.setText("");
    	picView.setImage(null);
    	imgRect.setVisible(true);
//    	bloodgrp.setPromptText("Blood Group");
    	bloodgrp.setValue("");
    	male.setSelected(false);
    	female.setSelected(false);
    	age.setText("");
    	disease.setText("");
    	lblmsg.setText("");
    	
    }

    @FXML
    void register(ActionEvent event) {
    	getGender();
    	if(checkMob(mobile.getText()))
    	{
    		showErrorMsg("This Mobile is already registered with us.");
    	}
    	else if(gend.equals("")||bloodgrp.getSelectionModel().isEmpty()||mobile.getText().equals("")||name.getText().equals("")||address.getText().equals("")||city.getText().equals("")||age.getText().equals(""))
    	{
    		showErrorMsg("Do not leave Empty Fields !!");
    	}
    	else if(picPath.equals(""))
    	{
    		showErrorMsg("Please Upload your profile pic!!");
    	}
    	else {
    		try {
				pst=con.prepareStatement("insert into donors values(?,?,?,?,?,?,?,?,?, current_date())");
				pst.setString(1, mobile.getText());
				pst.setString(2, picPath);
				pst.setString(3, name.getText());
				pst.setString(4, gend);
				pst.setString(5, address.getText());
				pst.setString(6, city.getText());
				
				String bg = bloodgrp.getValue();
				String bgrp="";
				if(bg.equals("A+")) {
					bgrp="apos";
				}
				else if(bg.equals("A-")) {
					bgrp="aneg";
				}
				else if(bg.equals("B+")) {
					bgrp="bpos";
				}
				else if(bg.equals("B-")) {
					bgrp="bneg";
				}
				else if(bg.equals("AB+")) {
					bgrp="abpos";
				}
				else if(bg.equals("AB-")) {
					bgrp="abneg";
				}
				else if(bg.equals("O+")) {
					bgrp="opos";
				}
				else if(bg.equals("O-")) {
					bgrp="oneg";
				}
				
				pst.setString(7, bgrp);
				
				
				pst.setInt(8, Integer.parseInt(age.getText()));
				pst.setString(9, disease.getText());
				
				pst.executeUpdate();
				lblmsg.setText("You are successfully registered");
				
			} 
    		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void update(ActionEvent event) {
    	getGender();
    	if(checkMob(mobile.getText())==false) {
    		showErrorMsg("No Record Found with this Mobile Number");
    	}
    	else if(gend.equals("")||bloodgrp.getSelectionModel().isEmpty()||name.getText().equals("")||address.getText().equals("")||city.getText().equals("")||age.getText().equals(""))
    	{
    		showErrorMsg("Do not leave Empty Fields before Updating data !!");
    	}
    	else {
    		try {
    			pst=con.prepareStatement("update donors set name=?, gender=?, address=?, city=?, bgroup=?, age=?, disease=? where mobile=?");
    			
    			pst.setString(1, name.getText());
				pst.setString(2, gend);
				pst.setString(3, address.getText());
				pst.setString(4, city.getText());
				
				String bg = bloodgrp.getValue();
				String bgrp="";
				if(bg.equals("A+")) {
					bgrp="apos";
				}
				else if(bg.equals("A-")) {
					bgrp="aneg";
				}
				else if(bg.equals("B+")) {
					bgrp="bpos";
				}
				else if(bg.equals("B-")) {
					bgrp="bneg";
				}
				else if(bg.equals("AB+")) {
					bgrp="abpos";
				}
				else if(bg.equals("AB-")) {
					bgrp="abneg";
				}
				else if(bg.equals("O+")) {
					bgrp="opos";
				}
				else if(bg.equals("O-")) {
					bgrp="oneg";
				}
				
				pst.setString(5, bgrp);
				
				
				pst.setInt(6, Integer.parseInt(age.getText()));
				pst.setString(7, disease.getText());
				pst.setString(8, mobile.getText());
				
				pst.executeUpdate();
				lblmsg.setText("Your record is Successfully Updated");
    		}
    		catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }

    @FXML
    void uploadImage(ActionEvent event) {
    	FileChooser file = new FileChooser();
    	file.getExtensionFilters().add(new ExtensionFilter("JPG Files", "*.jpg"));
    	File f=file.showOpenDialog(null);
    	if (f!=null) {
			try {
				Image img = new Image(new FileInputStream(f.getAbsoluteFile()));
				picView.setImage(img);
				picPath = f.getAbsolutePath();
				imgRect.setVisible(false);
			} 
			catch(FileNotFoundException e){
				e.printStackTrace();
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
