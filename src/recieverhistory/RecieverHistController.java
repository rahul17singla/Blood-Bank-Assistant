package recieverhistory;

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

import donorhistory.DonorBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbcc.DatabaseConnection;

public class RecieverHistController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> bloodgrp;

    @FXML
    private Label lblLogo;

    @FXML
    private TableView<RecieverBean> tableView;

    //--------------------------------------------
    
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
	
	ObservableList<RecieverBean> getTableView() {
		ObservableList<RecieverBean> ary = FXCollections.observableArrayList();
		
		try {
			pst=con.prepareStatement("select * from issue_blood");
			table=pst.executeQuery();
			while(table.next()) {
				String name = table.getString("name");
				String mobile = table.getString("mobile");
				String doi = table.getString("dateofissue");
				String hospital = table.getString("hospital");
				String reason = table.getString("reason");
				int units = table.getInt("units");
				
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
				
				RecieverBean obj = new RecieverBean(name, mobile, bgrp, doi, hospital, reason, units);
				ary.add(obj);
				
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ary;
		
	}
	
	ObservableList<RecieverBean> getFiltered() {
		ObservableList<RecieverBean> ary = FXCollections.observableArrayList();
		
		if(bloodgrp.getSelectionModel().isEmpty())
		{
			showWarnMsg("Please Select Blood Group for Filtered Results");
			return null;
		}
		else {
			try {
				getSelectedBGrp();
				pst=con.prepareStatement("select * from issue_blood where bgroup=?");
				pst.setString(1, bloodGroup);
				table=pst.executeQuery();
				while(table.next()) {
					String name = table.getString("name");
					String mobile = table.getString("mobile");
					String doi = table.getString("dateofissue");
					String hospital = table.getString("hospital");
					String reason = table.getString("reason");
					int units = table.getInt("units");
					
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
					
					RecieverBean obj = new RecieverBean(name, mobile, bgrp, doi, hospital, reason, units);
					ary.add(obj);
					
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return ary;
		}
	}    
	
	//--------------------------------------------

	
	//**********************************************************
	ObservableList<RecieverBean> allRecords = null;
	
	public void writeExcel( ObservableList<RecieverBean> list) throws Exception {
        Writer writer = null;
        try {
        	File file = new File("Recievers.csv");
            writer = new BufferedWriter(new FileWriter(file));
            String text="Name,Mobile,Blood Group,Units,Date of Issue,Hospital,Reason\n";
            writer.write(text);
            for (RecieverBean p : list)
            {
				text = p.getName()+ ","+ p.getMobile()+ "," + p.getBgrp() + "," + p.getUnits()+ "," + p.getDoi()+ "," + p.getHospital()+ "," + p.getReason()+"\n";
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

    	TableColumn<RecieverBean, String> name = new TableColumn<RecieverBean, String>("Patient");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	name.setMinWidth(80);
    	
    	TableColumn<RecieverBean, String> mobile = new TableColumn<RecieverBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(80);
    	
    	TableColumn<RecieverBean, String> bgrp = new TableColumn<RecieverBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(50);

    	TableColumn<RecieverBean, String> units = new TableColumn<RecieverBean, String>("Units");
    	units.setCellValueFactory(new PropertyValueFactory<>("units"));
    	units.setMinWidth(50);
    	
    	TableColumn<RecieverBean, String> doi = new TableColumn<RecieverBean, String>("Issue Date");
    	doi.setCellValueFactory(new PropertyValueFactory<>("doi"));
    	doi.setMinWidth(50);
    	
    	TableColumn<RecieverBean, String> hosp = new TableColumn<RecieverBean, String>("Hospital");
    	hosp.setCellValueFactory(new PropertyValueFactory<>("hospital"));
    	hosp.setMinWidth(100);
    	
    	TableColumn<RecieverBean, String> reas = new TableColumn<RecieverBean, String>("Reason");
    	reas.setCellValueFactory(new PropertyValueFactory<>("reason"));
    	reas.setMinWidth(115);
    	
    	tableView.getColumns().addAll(name,mobile,bgrp,units,doi,hosp,reas);
    	
    	tableView.setItems(null);
    	
    	allRecords = getFiltered();
    	tableView.setItems(allRecords);
    }

    @FXML
    void doShowAll(ActionEvent event) {
    	bloodgrp.setValue(null); 
    	tableView.getColumns().clear();;

    	TableColumn<RecieverBean, String> name = new TableColumn<RecieverBean, String>("Patient");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
    	name.setMinWidth(80);
    	
    	TableColumn<RecieverBean, String> mobile = new TableColumn<RecieverBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	mobile.setMinWidth(80);
    	
    	TableColumn<RecieverBean, String> bgrp = new TableColumn<RecieverBean, String>("Blood Group");
    	bgrp.setCellValueFactory(new PropertyValueFactory<>("bgrp"));
    	bgrp.setMinWidth(50);

    	TableColumn<RecieverBean, String> units = new TableColumn<RecieverBean, String>("Units");
    	units.setCellValueFactory(new PropertyValueFactory<>("units"));
    	units.setMinWidth(50);
    	
    	TableColumn<RecieverBean, String> doi = new TableColumn<RecieverBean, String>("Issue Date");
    	doi.setCellValueFactory(new PropertyValueFactory<>("doi"));
    	doi.setMinWidth(50);
    	
    	TableColumn<RecieverBean, String> hosp = new TableColumn<RecieverBean, String>("Hospital");
    	hosp.setCellValueFactory(new PropertyValueFactory<>("hospital"));
    	hosp.setMinWidth(100);
    	
    	TableColumn<RecieverBean, String> reas = new TableColumn<RecieverBean, String>("Reason");
    	reas.setCellValueFactory(new PropertyValueFactory<>("reason"));
    	reas.setMinWidth(115);
    	
    	tableView.getColumns().addAll(name,mobile,bgrp,units,doi,hosp,reas);
    	
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
    	ArrayList<String> bloodGroups = new ArrayList<String>(Arrays.asList("AB+","AB-","A+","A-","B+","B-","O+","O-"));
    	bloodgrp.getItems().setAll(bloodGroups);
    	con = DatabaseConnection.doConnect();
    }

}
