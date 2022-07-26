package collection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class BloodUnitCollectionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bgroup;

    @FXML
    private DatePicker donDate;

    @FXML
    private Rectangle imgRect;

    @FXML
    private Label lblmsg;

    @FXML
    private ComboBox<String> mobile;

    @FXML
    private ImageView picView;

    Connection con;
    PreparedStatement pst;
    ResultSet table;
    
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
    
    String getBGroup(String mob) {
    	String bg="";
    	try {
    		pst=con.prepareStatement("select bgroup from donors where mobile=?");
    		pst.setString(1, mob);
    		table = pst.executeQuery();
    		
    		while(table.next())
    		{
    			bg=table.getString("bgroup");
    		}
		}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	return bg;
    }
    
    String getPicPath(String mob) {
    	String pp="";
    	try {
    		pst=con.prepareStatement("select picpath from donors where mobile=?");
    		pst.setString(1, mob);
    		table = pst.executeQuery();
    		
    		while(table.next())
    		{
    			pp=table.getString("picpath");
    		}
		}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	
    	return pp;
    }
    
    ArrayList<String> getAllMobiles() {
    	ArrayList<String> mobs = new ArrayList<String>();
    	try {
    		pst=con.prepareStatement("select mobile from donors order by mobile");
    		table=pst.executeQuery();
    		while(table.next()) {
    			mobs.add(table.getString("mobile"));
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return mobs;
    	
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
			
			Scene scene1 = (Scene)bgroup.getScene();
			scene1.getWindow().hide();
			
		}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void doClear(ActionEvent event) {
    	mobile.setValue(null);
    	mobile.setPromptText("Your Mobile Number");
    	bgroup.setText(null);
    	bgroup.setPromptText("Blood Group");
    	picView.setImage(null);
    	imgRect.setVisible(true);
    	donDate.setValue(null);
    	
    }

    @FXML
    void searchUser(ActionEvent event) {
    	if(checkMob(mobile.getValue())==false)
    	{
    		showErrorMsg("No Record Found with this Mobile Number");
    		return;
    	}
    	
    	bgroup.setText(getBGroup(mobile.getValue()));
    	
    	String picpath=getPicPath(mobile.getValue());
		try {
			Image img = new Image(new FileInputStream(picpath));
			picView.setImage(img);
			imgRect.setVisible(false);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }

    @FXML
    void uploadData(ActionEvent event) {
    	if(checkMob(mobile.getValue())==false) {
    		showErrorMsg("No Record Found with this Mobile Number");
    	}
    	else if(donDate.getValue()==null) {
    		showErrorMsg("Please enter Date of Donation !!!");
    	}
    	else {
	    	try {
				pst=con.prepareStatement("insert into donations values(?,?,?)");
				pst.setString(1, mobile.getValue());
				pst.setString(2, bgroup.getText());
				pst.setString(3, String.valueOf(donDate.getValue()));
				
				pst.executeUpdate();
				
				lblmsg.setText("Data Uploaded Successfully");
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
	    		String bg = bgroup.getText();
				pst=con.prepareStatement("update total_blood_record set "+bg+"="+bg+"+?");
				pst.setInt(1, 1);
				
				pst.executeUpdate();
				
				lblmsg.setText("Data Uploaded Successfully");
	    	}
	    	catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	
    	}
    }

    @FXML
    void initialize() {
    	con = DatabaseConnection.doConnect();
    	mobile.getItems().setAll(getAllMobiles());
    	
    	
    }

}
