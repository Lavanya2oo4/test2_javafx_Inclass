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
                setMessage("Appointment Booked Successfully");
            }
            else{
                setMessage("Failed to Book Appointment");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void updateAppointment(MouseEvent mouseEvent) {
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
                setMessage("Appointment Booked Successfully");
            }
            else{
                setMessage("Failed to Book Appointment");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteAppointment(MouseEvent mouseEvent) {
    }

    public void getAppointmentDetails(MouseEvent mouseEvent) {
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