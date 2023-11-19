package com.example.searchenginegui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Button button;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Search Engine");
        Button button = new Button("search");
        StackPane layout = new StackPane();
        button.setOnAction(e -> System.out.println("Hello"));
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}