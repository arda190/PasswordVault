package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class MenuView {

    public Scene getMainScene(Stage stage) {
        // 1. Buton - Save Password
        Button savePasswordButton = new Button("Save Password");
        savePasswordButton.setPrefWidth(200);
        savePasswordButton.setStyle("-fx-font-size: 14px;");
        savePasswordButton.setOnAction(e -> {
            SavePasswordView saveView = new SavePasswordView();
            stage.setScene(saveView.getScene(stage));
        });

        // 2. Buton - Show Saved Sites (şimdi 2. buton olarak)
        Button showSitesButton = new Button("Show Saved Sites");
        showSitesButton.setPrefWidth(200);
        showSitesButton.setStyle("-fx-font-size: 14px;");
        showSitesButton.setOnAction(e -> {
            ShowPasswordsView showSitesView = new ShowPasswordsView();
            stage.setScene(showSitesView.getScene(stage));
        });

        VBox vbox = new VBox(20); // Butonlar arası 20 px boşluk
        vbox.setPadding(new Insets(50, 0, 0, 150)); // Üst, sağ, alt, sol boşluk
        vbox.getChildren().addAll(savePasswordButton, showSitesButton);

        return new Scene(vbox, 500, 500);
    }
}



