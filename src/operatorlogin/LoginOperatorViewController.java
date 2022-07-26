package operatorlogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginOperatorViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox chkPass;

    @FXML
    private Label lblmsg;

    @FXML
    private Label lblpass;

    @FXML
    private PasswordField password;

    @FXML
    private TextField userid;
    
    private String uid = "Rahul1710";
    private String pass = "Singla@1710";

    @FXML
    void doShowPassword(ActionEvent event) {
    	if(chkPass.isSelected()) {
    		lblpass.setVisible(true);
    		lblpass.setText(password.getText());
    	}
    	
    	if(!chkPass.isSelected()) {
    		lblpass.setVisible(false);
    	}
    }

    @FXML
    void loginOperator(ActionEvent event) {
    	if(userid.getText().equals(uid) && password.getText().equals(pass)) {
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
    	else {
    		lblmsg.setText("Wrong Username or Password !!!");
    	}
    }

    @FXML
    void initialize() {
    	

    }

}
