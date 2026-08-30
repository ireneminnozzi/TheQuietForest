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
 * <p>Avvia l'applicazione in modalità fullscreen e carica
 * la schermata del menu iniziale con la musica di sottofondo.</p>
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Inizializza il navigatore
            SceneNavigator.init(primaryStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("The Quiet Forest");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");

            primaryStage.show();

            // Avvia la musica di sottofondo
            SoundManager.playBackgroundMusic("/audio/musica.mp3");

        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'interfaccia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}