package it.unicam.cs.mpgc.rpg130957.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Gestisce la navigazione tra le scene dell'applicazione.
 *
 * <p>Centralizza la logica di caricamento FXML e mantenimento del fullscreen,
 * eliminando la duplicazione di codice nei controller.</p>
 */
public class SceneNavigator {

    private static Stage stage;

    /**
     * Inizializza il navigatore con lo stage principale.
     * Da chiamare una sola volta nel Main.
     *
     * @param primaryStage lo stage dell'applicazione
     */
    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Carica e mostra una nuova schermata.
     *
     * @param fxmlPath percorso del file FXML (es. "/fxml/menu.fxml")
     * @param title    titolo della finestra
     */
    public static void navigate(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle(title);

            // per il fullscreen
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");

        } catch (IOException e) {
            System.err.println("Errore di navigazione verso: " + fxmlPath);
            e.printStackTrace();
        }
    }
}