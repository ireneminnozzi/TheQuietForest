package it.unicam.cs.mpgc.rpg130957;

import it.unicam.cs.mpgc.rpg130957.navigation.SceneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto di ingresso dell'applicazione JavaFX.
 *
 * <p>Avvia l'applicazione in modalità fullscreen per un'esperienza
 * di gioco immersiva, caricando la schermata del menu iniziale.</p>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //navigatore
            SceneNavigator.init(primaryStage);

            // Carica il file FXML del menu iniziale
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();

            // Crea la scena
            Scene scene = new Scene(root);

            // Imposta il titolo
            primaryStage.setTitle("The Quiet Forest");
            primaryStage.setScene(scene);

            // MODIFICA CHIAVE: Abilita il fullscreen
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");

            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'interfaccia: " + e.getMessage());
            e.printStackTrace();
        }


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                primaryStage.setTitle("The Quiet Forest");
                primaryStage.setScene(scene);
                primaryStage.setFullScreen(true);
                primaryStage.setFullScreenExitHint("");

                primaryStage.show();

                // AVVIA MUSICA
                SoundManager.playBackgroundMusic("/sound/audioGioco.mp3");

            } catch (IOException e) {
                System.err.println("Errore nel caricamento dell'interfaccia: " + e.getMessage());
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        launch(args);
    }
}