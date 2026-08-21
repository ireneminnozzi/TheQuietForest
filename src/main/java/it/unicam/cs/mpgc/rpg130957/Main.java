package it.unicam.cs.mpgc.rpg130957;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carica il file FXML del menu iniziale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();

            // Crea la scena e la imposta sullo Stage (la finestra)
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("The Quiet Forest");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Blocca il ridimensionamento per mantenere le proporzioni
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'interfaccia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}