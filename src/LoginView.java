package com.example.demo;

import javafx.application.Application;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.*;

import java.awt.*;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class LoginView extends Application {
    public static String global="";

    @Override
    public  void start(Stage primaryStage){
        Label label=new Label("LOGIN");
        //Pane stackPane=new Pane(label);
        label.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;");
        label.setLayoutY(30);
        label.setLayoutX(210);
        PasswordField passwordField=new PasswordField();
        Button button=new Button("Enter");

        Pane pane = new Pane();
        pane.getChildren().addAll(label, passwordField,button);
        Scene scene=new Scene(pane,500,500);


        button.setOnAction(e->{
            File file=new File("masterPassword.db");
            String input=passwordField.getText();
            if(file.exists()){
                //ystem.out.println("File Exist");
                try {
                    if(Database.compareMasterPasswordHashes(input)){
                        global=input;
                        MenuView menuView=new MenuView();
                        Scene mainScene = menuView.getMainScene(primaryStage);
                        primaryStage.setScene(mainScene);
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect password!");
                        alert.show();
                    }
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                try {
                    Database.createMasterPassword(input);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        passwordField.setLayoutX(175);  //180 75
        passwordField.setLayoutY(80);

        button.setLayoutX(220);
        button.setLayoutY(120);


        primaryStage.setResizable(false);




        primaryStage.setTitle("Password Vault");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
         launch(args);
    }

}
