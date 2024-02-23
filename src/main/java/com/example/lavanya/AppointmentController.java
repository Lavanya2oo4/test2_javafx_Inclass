package com.example.lavanya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    private static final String url="jdbc:mysql://localhost:3306/Appointment";
    private static final String username="root";
    private static final String Password="Oreo@2004";


    public TableView<Appointment_Scheduler> tableVIew;
    public TableColumn<Appointment_Scheduler,Integer> tableId;
    public TableColumn<Appointment_Scheduler,String> tableName;
    public TableColumn<Appointment_Scheduler,java.sql.Date> tabledate;
    public TableColumn<Appointment_Scheduler,String> tabledoctor;
    public TextField patientName;
    public TextField doctor;
    public Label message;
    public DatePicker date;
    public TextField id;

    ObservableList<Appointment_Scheduler> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     tableId.setCellValueFactory(new PropertyValueFactory<Appointment_Scheduler,Integer>("appointmentId"));
     tableName.setCellValueFactory(new PropertyValueFactory<Appointment_Scheduler,String>("patientName"));
     tabledoctor.setCellValueFactory(new PropertyValueFactory<Appointment_Scheduler,String>("doctor"));
     tabledate.setCellValueFactory(new PropertyValueFactory<Appointment_Scheduler,java.sql.Date>("appointmentDate"));
     tableVIew.setItems(list);
    }

    public void setMessage(String msg){
        message.setText(msg);
    }

    public void bookAppointment(MouseEvent mouseEvent) {
          setMessage("");
        if(date.getValue()==null){
            setMessage("ENTER APPOINTMENT DATE,DOCTOR,PATIENT NAME");
            return;
        }
        String date_=date.getValue().toString();
        String doctor_=doctor.getText();
        String name_= patientName.getText();

        if( doctor_.equals("") || name_.equals("")){
            setMessage("ENTER APPOINTMENT DATE,DOCTOR,PATIENT NAME");
            return;
        }
        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,Password);
            Statement statement=connection.createStatement();
            String query=String.format("INSERT INTO Appointment_Scheduler(appointmentDate,patientName,doctor)VALUES ('%s','%s','%s')",date_,name_,doctor_);
            int rowsAffected=statement.executeUpdate(query);
            if(rowsAffected>0){
                setMessage("APPOINTMENT BOOKED SUCCESSFULLY");
//                fetchAllData();
            }
            else{
                setMessage("FAILED TO BOOK APPOINTMENT");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateAppointment(MouseEvent mouseEvent) {
        setMessage("");
        if(date.getValue()==null){
            setMessage("ENTER APPOINTMENT DATE,DOCTOR,PATIENT NAME");
            return;
        }
        String date_=date.getValue().toString();
        String doctor_=doctor.getText();
        String name_= patientName.getText();
        String id_=id.getText();

        if( doctor_.equals("") || name_.equals("") || id_.equals("")){
            setMessage("ENTER APPOINTMENT DATE,DOCTOR,PATIENT NAME AND ID");
            return;
        }
        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,Password);
            Statement statement=connection.createStatement();
            String query=String.format("UPDATE Appointment_Scheduler SET appointmentDate='%s',patientName='%s',doctor='%s' WHERE appointmentId=%d",date_,name_,doctor_,Integer.parseInt(id_));
            int rowsAffected=statement.executeUpdate(query);
            if(rowsAffected>0){
                setMessage("APPOINTMENT UPDATED SUCCESSFULLY");
//                fetchAllData();
            }
            else{
                setMessage("FAILED TO UPDATE APPOINMENT");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteAppointment(MouseEvent mouseEvent) {
        tableVIew.getItems().clear();
        String id_=id.getText();
        setMessage("");

        if( id_.equals("")){
            setMessage("ENTER APPOINTMENT ID");
            return;
        }
        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,Password);
            Statement statement=connection.createStatement();
            String query=String.format("DELETE FROM appointment_scheduler WHERE appointmentId=%d",Integer.parseInt(id_));
            int rowsAffected=statement.executeUpdate(query);
            if(rowsAffected>0){
                setMessage("APPOINTMENT DELETED SUCCESSFULLY");
                fetchAllData();
            }
            else{
                setMessage("FAILED TO DELETE APPOINMENT");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void getAppointmentDetails(MouseEvent mouseEvent) {
        String id_=id.getText();
        tableVIew.getItems().clear();
        setMessage("");
        if( id_.equals("")){
            setMessage("ENTER APPOINTMENT ID");
            return;
        }
        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,Password);
            Statement statement=connection.createStatement();
            String query=String.format("SELECT * FROM Appointment_Scheduler WHERE appointmentId=%d",Integer.parseInt(id_));
            ResultSet resultSet=statement.executeQuery(query);

            if(resultSet.next()){
                int id=resultSet.getInt("appointmentId");
                String name=resultSet.getString("patientName");
                String doctor=resultSet.getString("doctor");
                String date=resultSet.getString("appointmentDate");

                tableVIew.getItems().add(new Appointment_Scheduler(id,date,doctor,name));
//                System.out.println("fount");
            }
            else{
                setMessage("NO APPOINTMENT FOUND");
            }




        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void getAllAppointments(MouseEvent mouseEvent) {

        tableVIew.getItems().clear();
        setMessage("FETCHED ALL APPOINTMENTS");
        fetchAllData();


    }



    public void fetchAllData(){
        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,Password);
            Statement statement=connection.createStatement();
            String query="SELECT * FROM Appointment_Scheduler";
            ResultSet resultSet=statement.executeQuery(query);

            while(resultSet.next()){
                int id=resultSet.getInt("appointmentId");
                String name=resultSet.getString("patientName");
                String doctor=resultSet.getString("doctor");
                String date=resultSet.getString("appointmentDate");

                tableVIew.getItems().add(new Appointment_Scheduler(id,date,doctor,name));
            }




        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}