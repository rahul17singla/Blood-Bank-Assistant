package donorhistory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class DonorHistController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblLogo;
    
    @FXML
    private TextField mobile;
    
    @FXML
    private TableView<DonorBean> tableView;
    //---------------------------------------------
    
    ResultSet table;
	Connection con;
	PreparedStatement pst;
	String bloodGroup="";
	
	void showWarnMsg(String msg) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("WARNING !!!");
		alert.setContentText(msg);
		alert.show();
	}

	boolean checkMob(String mob)
    {
    	boolean flag=false;
    	
    	try {
    		pst=con.prepareStatement("select * from donations where mobile=?");
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
	
	ObservableList<DonorBean> getTableView() {
		ObservableList<DonorBean> ary = FXCollections.observableArrayList();
		
		try {
			pst=con.prepareStatement("select * from donations");
			table=pst.executeQuery();
			while(table.next()) {
				String mobile = table.getString("mobile");
				String dod = table.getString("dateofdon");
				
				String bg = table.getString("bgrp");
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
				
				DonorBean obj = new DonorBean(mobile, bgrp, dod);
				ary.add(obj);
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ary;
		
	}
	
	ObservableList<DonorBean> getFiltered() {
		ObservableList<DonorBean> ary = FXCollections.observableArrayList();
		
		if(mobile.getText().equals(null)) {
			showWarnMsg("Please Enter Mobile Number");
		}
		else {
			if(checkMob(mobile.getText())==false) {
				showWarnMsg("Please Enter Correct Mobile Number");
			}
			else {
				try {
					pst=con.prepareStatement("select * from donations where mobile=?");
					pst.setString(1, mobile.getText());
					table=pst.executeQuery();
					while(table.next()) {
						String mobile = table.getString("mobile");
						String dod = table.getString("dateofdon");
						
						String bg = table.getString("bgrp");
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
						
						DonorBean obj = new DonorBean(mobile, bgrp, dod);
						ary.add(obj);
						
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return ary;
	}
    

    //---------------------------------------------
	
	
	
	//**********************************************************
	ObservableList<DonorBean> allRecords=null;
	
	public void writeExcel( ObservableList<DonorBean> list) throws Exception {
        Writer writer = null;
        try {
        	File file = new File("Donations.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Mobile,Blood Group,Date of Donation\n";
            writer.write(text);
            for (DonorBean p : list)
            {
				text = p.getMobile()+ "," + p.getBgrp() + ","  + p.getDod()+"\n";
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
	
	
	//**********************************************************
    @FXML
    void doFilterRecords(ActionEvent event) {
    	tableView.getColumns().clear();;
    	
    	TableColumn<DonorBean, String> mobile = new TableColumn<DonorBean, String>("Mobile Number ");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(200);
    	
    	TableColumn<DonorBean, String> bgrp = new TableColumn<DonorBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(180);
    	
    	TableColumn<DonorBean, String> dod = new TableColumn<DonorBean, String>("Date of Donation");
    	dod.setCellValueFactory(new PropertyValueFactory<>("dod"));
    	dod.setMinWidth(200);
    	
    	tableView.getColumns().addAll(mobile,bgrp,dod);
    	
    	tableView.setItems(null);
    	
    	allRecords = getFiltered();
    	tableView.setItems(allRecords);
    	
    }

    @FXML
    void doShowAll(ActionEvent event) {
    	tableView.getColumns().clear();;
    	mobile.setText(null); 
    	tableView.getColumns().clear();;
    	
    	TableColumn<DonorBean, String> mobile = new TableColumn<DonorBean, String>("Mobile Number ");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(200);
    	
    	TableColumn<DonorBean, String> bgrp = new TableColumn<DonorBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(180);
    	
    	TableColumn<DonorBean, String> dod = new TableColumn<DonorBean, String>("Date of Donation");
    	dod.setCellValueFactory(new PropertyValueFactory<>("dod"));
    	dod.setMinWidth(200);
    	
    	tableView.getColumns().addAll(mobile,bgrp,dod);
    	
    	tableView.setItems(null);
    	
    	allRecords = getTableView();
    	tableView.setItems(allRecords);
    }
    
    @FXML
    void doBack(ActionEvent event) {
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
    void initialize() {
    	con = DatabaseConnection.doConnect();
    }

}
