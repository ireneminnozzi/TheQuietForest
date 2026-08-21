package it.unicam.cs.mpgc.rpg130957.controller;

import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg130957.model.game.GameSession;
import it.unicam.cs.mpgc.rpg130957.model.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per la schermata iniziale del menu.
 */
public class MenuController {

    private final GameFactory gameFactory = new GameFactory();
    private final GamePersistence persistence = new GamePersistence("savegame.json");

    // Iniettiamo un bottone dal file FXML. Ci servirà per recuperare la Stage (finestra) corrente.
    @FXML
    private Button btnExit;

    @FXML
    private void handleNewGame() {
        // 1. Crea una nuova partita
        GameEngine engine = gameFactory.createNewGame();

        // 2. La salva nella Sessione Singleton
        GameSession.getInstance().setEngine(engine);

        // 3. Cambia schermata
        changeScene("/fxml/map.fxml", "Mappa del Bosco");
    }

    @FXML
    private void handleLoadGame() {
        if (!persistence.saveExists()) {
            showAlert("Errore", "Nessun salvataggio trovato.");
            return;
        }

        try {
            // 1. Carica i dati
            SaveData data = persistence.load();

            // 2. Ricostruisce la partita
            GameEngine engine = gameFactory.loadGame(data);

            // 3. Salva nella Sessione
            GameSession.getInstance().setEngine(engine);

            // 4. Cambia schermata
            changeScene("/fxml/map.fxml", "Mappa del Bosco");

        } catch (IOException e) {
            showAlert("Errore", "Impossibile caricare il salvataggio: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        // Il modo più pulito e standard per chiudere un'applicazione JavaFX
        Platform.exit();
    }

    /**
     * Metodo di utilità per cambiare scena.
     */
    private void changeScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Recuperiamo la Stage corrente in modo sicuro partendo dal bottone iniettato
            Stage stage = (Stage) btnExit.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore Critico", "Impossibile caricare la schermata: " + fxmlPath);
        }
    }

    /**
     * Mostra una finestra di dialogo di errore.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}