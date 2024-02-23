package com.example.lavanya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class LoginController {

    public TextField memberId;
    public TextField memberName;
    public TextField memberEmail;
    public PasswordField memberPassword;
    public Label message;

    private static final String url="jdbc:mysql://localhost:3306/Appointment";
    private static final String username="root";
    private static final String Password="Oreo@2004";

    public void Login() {

        String id_=memberId.getText();
        String name_=memberName.getText();
        String email_=memberEmail.getText();
        String password_=memberPassword.getText();

        if(id_.equals("")||name_.equals("") || email_.equals("")||password_.equals("")){
            setMessage("ID NAME EMAIL AND PASSWORD ARE REQUIRED");
            return;
        }

        try{
            Class.forName("com.cj.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
        }

        try{
            Connection connection=DriverManager.getConnection(url,username,Password);
            String query="SELECT memberpassword FROM Member_Authentication WHERE memberId=? AND memberName=? AND memberEmail=?";

            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(id_));
            preparedStatement.setString(2,name_);
            preparedStatement.setString(3,email_);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                String originalPassword=resultSet.getString("memberPassword");
                if (originalPassword.equals(password_)){
                    try{
                        Parent scene2 = FXMLLoader.load(getClass().getResource("appointment-view.fxml"));
                        Stage stage=new Stage();
                        stage.setScene(new Scene(scene2));
                        stage.setTitle("Appointment Scheduler");
//                        closing previous scene
                        Stage firstStage=(Stage) memberId.getScene().getWindow();
                        firstStage.close();
                        stage.show();
                    }
                    catch(Exception e){
//                        System.out.println(e.getMessage());
                    }
                }
                else{
                    setMessage("INVALID CREDENTIALS");
                }
            }
            else{
                setMessage("INVALID CREDENTIALS");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        memberId.setText("");
        memberName.setText("");
        memberPassword.setText("");
        memberEmail.setText("");

    }
    public void setMessage(String msg){
        message.setText(msg);
    }
}