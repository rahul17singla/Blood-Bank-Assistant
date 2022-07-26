module BloodBankAssistant {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
	opens donormaster to javafx.graphics, javafx.fxml;
	opens jdbcc to javafx.graphics, javafx.fxml;
	opens collection to javafx.graphics, javafx.fxml;
	opens bloodavailable to javafx.graphics, javafx.fxml;
	opens operatorlogin to javafx.graphics, javafx.fxml;
	opens controlpanel to javafx.graphics, javafx.fxml;
	opens userdatatable to javafx.graphics, javafx.fxml, javafx.base;
	opens bloodissue to javafx.graphics, javafx.fxml;
	opens history to javafx.graphics, javafx.fxml;
	opens donorhistory to javafx.graphics, javafx.fxml, javafx.base;
	opens recieverhistory to javafx.graphics, javafx.fxml, javafx.base;
	opens contactdeveloper to javafx.graphics, javafx.fxml;

}
