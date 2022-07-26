package userdatatable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbcc.DatabaseConnection;
import recieverhistory.RecieverBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class userTableViewController {
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private ComboBox<String> bloodgrp;
    
    @FXML
    private TableView<UserBean> tableView;
    

    ResultSet table;
	Connection con;
	PreparedStatement pst;
	String bloodGroup="";
	
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
	
	void showWarnMsg(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("WARNING !!!");
		alert.setContentText(msg);
		alert.show();
	}
	
	ObservableList<UserBean> getTableView() {
		ObservableList<UserBean> ary = FXCollections.observableArrayList();
		
		try {
			pst=con.prepareStatement("select * from donors");
			table=pst.executeQuery();
			while(table.next()) {
				String name = table.getString("name");
				String mobile = table.getString("mobile");
				String gender = table.getString("gender");
				
				String bg = table.getString("bgroup");
				String bgrp="";
				if(bg.equals("apos")) {
					bgrp="A+";
				}
				else if(bg.equals("aneg")) {
					bgrp="A-";
				}
				else if(bg.equals("bpos")) {
					bgrp="B+";
				}
				else if(bg.equals("bneg")) {
					bgrp="B-";
				}
				else if(bg.equals("abpos")) {
					bgrp="AB+";
				}
				else if(bg.equals("abneg")) {
					bgrp="AB-";
				}
				else if(bg.equals("opos")) {
					bgrp="O+";
				}
				else if(bg.equals("oneg")) {
					bgrp="O-";
				}
				
				UserBean obj = new UserBean(name, mobile, gender, bgrp);
				ary.add(obj);
				
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ary;
		
	}
	
	ObservableList<UserBean> getFiltered() {
		ObservableList<UserBean> ary = FXCollections.observableArrayList();
		
		if(bloodgrp.getSelectionModel().isEmpty())
		{
			showWarnMsg("Please Select Blood Group for Filtered Results");
			return null;
		}
		else {
			try {
				getSelectedBGrp();
				pst=con.prepareStatement("select * from donors where bgroup=?");
				pst.setString(1, bloodGroup);
				table=pst.executeQuery();
				while(table.next()) {
					String name = table.getString("name");
					String mobile = table.getString("mobile");
					String gender = table.getString("gender");
					
					String bg = table.getString("bgroup");
					String bgrp="";
					if(bg.equals("apos")) {
						bgrp="A+";
					}
					else if(bg.equals("aneg")) {
						bgrp="A-";
					}
					else if(bg.equals("bpos")) {
						bgrp="B+";
					}
					else if(bg.equals("bneg")) {
						bgrp="B-";
					}
					else if(bg.equals("abpos")) {
						bgrp="AB+";
					}
					else if(bg.equals("abneg")) {
						bgrp="AB-";
					}
					else if(bg.equals("opos")) {
						bgrp="O+";
					}
					else if(bg.equals("oneg")) {
						bgrp="O-";
					}
					
					
					UserBean obj = new UserBean(name, mobile, gender, bgrp);
					ary.add(obj);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return ary;
		}
		
	}
	//***************************************************

	ObservableList<UserBean> allRecords = null;
	
	public void writeExcel( ObservableList<UserBean> list) throws Exception {
        Writer writer = null;
        try {
        	File file = new File("UsersData.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Name,Mobile,Gender,Blood Group\n";
            writer.write(text);
            for (UserBean p : list)
            {
				text = p.getName()+ ","+ p.getMobile()+ "," + p.getGender()+ "," + p.getBgrp() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }
	
	
	@FXML
    void export(ActionEvent event) {
		if(allRecords==null) {
			showWarnMsg("Retrieve Data before Exporting to Excel");
		}
		else {
			try {
				writeExcel(allRecords);
				System.out.println("Exported to excel..");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	//***************************************************

	@FXML
    void doFilterRecords(ActionEvent event) {
//    	tableView.setItems(null);
    	tableView.getColumns().clear();;

		TableColumn<UserBean, String> name = new TableColumn<UserBean, String>("Name of the Donor");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	name.setMinWidth(200);
    	
    	TableColumn<UserBean, String> mobile = new TableColumn<UserBean, String>("Mobile Number");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(180);
    	
    	TableColumn<UserBean, String> gend = new TableColumn<UserBean, String>("Gender");
    	gend.setCellValueFactory(new PropertyValueFactory<>("gender"));
    	gend.setMinWidth(100);
    	
    	TableColumn<UserBean, String> bgrp = new TableColumn<UserBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(100);
    	
    	tableView.getColumns().addAll(name,mobile,gend,bgrp);
    	
    	tableView.setItems(null);
    	
    	allRecords = getFiltered();
    	tableView.setItems(allRecords);
    }
	
	
    @FXML
    void doShowAll(ActionEvent event) {
    	
    	bloodgrp.setValue(null); 
//    	tableView.setItems(null);
    	tableView.getColumns().clear();;

    	
    	TableColumn<UserBean, String> name = new TableColumn<UserBean, String>("Name of the Donor");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	name.setMinWidth(200);
    	
    	TableColumn<UserBean, String> mobile = new TableColumn<UserBean, String>("Mobile Number");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(180);
    	
    	TableColumn<UserBean, String> gend = new TableColumn<UserBean, String>("Gender");
    	gend.setCellValueFactory(new PropertyValueFactory<>("gender"));
    	gend.setMinWidth(100);
    	
    	TableColumn<UserBean, String> bgrp = new TableColumn<UserBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(100);
    	
    	tableView.getColumns().addAll(name,mobile,gend,bgrp);
    	
    	tableView.setItems(null);
    	
    	allRecords = getTableView();
    	tableView.setItems(allRecords);
    	
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
    void initialize() {
    	ArrayList<String> bloodGroups = new ArrayList<String>(Arrays.asList("AB+","AB-","A+","A-","B+","B-","O+","O-"));
    	bloodgrp.getItems().setAll(bloodGroups);
    	con = DatabaseConnection.doConnect();
    	
    }
}
