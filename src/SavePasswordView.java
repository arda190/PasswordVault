package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import javafx.geometry.Pos;

import javafx.scene.layout.HBox;


public class SavePasswordView {

    public Scene getScene(Stage stage) {
    
        Label siteLabel = new Label("Site Name:");
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");

        
        TextField siteField = new TextField();
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();

       
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(100);

       
        Button backButton = new Button("Back");
        backButton.setPrefWidth(100);

    
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setHgap(10);
        grid.setVgap(15);

       
        grid.add(siteLabel, 0, 0);
        grid.add(siteField, 1, 0);
        grid.add(userLabel, 0, 1);
        grid.add(userField, 1, 1);
        grid.add(passLabel, 0, 2);
        grid.add(passField, 1, 2);

        grid.add(saveButton, 1, 3);

       
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_LEFT);
        backBox.setPadding(new Insets(200, 0, 0, 0)); 
        grid.add(backBox, 0, 4);

        
        saveButton.setOnAction(e -> {
            String site = siteField.getText();
            String username = userField.getText();
            String password = passField.getText();

            System.out.println("Site: " + site);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            try {
                Database.createTable();
                Database.insertLog(site, username, password);
                siteField.clear();
                userField.clear();
                passField.clear();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

       
        backButton.setOnAction(e -> {
            MenuView menuView = new MenuView();
            stage.setScene(menuView.getMainScene(stage));
        });

        return new Scene(grid, 500, 500);
    }
}
