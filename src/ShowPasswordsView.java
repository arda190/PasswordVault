package com.example.demo;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowPasswordsView {

    public Scene getScene(Stage stage) {
        ListView<String> siteList = new ListView<>();
        ObservableList<String> sites = FXCollections.observableArrayList();

        // Veritabanından site isimlerini çek
        try (Connection conn = Database.getConnection(Database.url)) {
            String query = "SELECT site FROM logs;";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sites.add(rs.getString("site"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        siteList.setItems(sites);

        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ********");
        Button showPasswordBtn = new Button("Show Password");
        Button backBtn = new Button("Back");

        // Site seçilince bilgileri getir
        siteList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try (Connection conn = Database.getConnection(Database.url)) {
                    String query = "SELECT username, password FROM logs WHERE site = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, newVal);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        usernameLabel.setText("Username: " + rs.getString("username"));

                        String decryptedPass = AES.decrypt(rs.getString("password"), LoginView.global);
                        passwordLabel.setText("Password: ********");

                        showPasswordBtn.setOnAction(ev -> {
                            if (passwordLabel.getText().contains("*")) {
                                passwordLabel.setText("Password: " + decryptedPass);
                                showPasswordBtn.setText("Hide Password");
                            } else {
                                passwordLabel.setText("Password: ********");
                                showPasswordBtn.setText("Show Password");
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Geri dön butonu
        backBtn.setOnAction(e -> {
            MenuView menuView = new MenuView();
            stage.setScene(menuView.getMainScene(stage));
        });

        VBox root = new VBox(10, new Label("Saved Sites:"), siteList, usernameLabel, passwordLabel, showPasswordBtn, backBtn);
        root.setPrefSize(400, 400);

        return new Scene(root);
    }
}
