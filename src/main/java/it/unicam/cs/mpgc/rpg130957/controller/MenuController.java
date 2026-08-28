package it.unicam.cs.mpgc.rpg130957.controller;

import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg130957.model.game.GameSession;
import it.unicam.cs.mpgc.rpg130957.model.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import it.unicam.cs.mpgc.rpg130957.navigation.SceneNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class MenuController {

    private final GameFactory gameFactory = new GameFactory();
    private final GamePersistence persistence = new GamePersistence("savegame.json");

    @FXML
    private void handleNewGame() {
        GameEngine engine = gameFactory.createNewGame();
        GameSession.getInstance().setEngine(engine);
        SceneNavigator.navigate("/fxml/map.fxml", "Mappa del Bosco");
    }

    @FXML
    private void handleLoadGame() {
        if (!persistence.saveExists()) {
            showAlert("Errore", "Nessun salvataggio trovato.");
            return;
        }

        try {
            SaveData data = persistence.load();
            GameEngine engine = gameFactory.loadGame(data);
            GameSession.getInstance().setEngine(engine);
            SceneNavigator.navigate("/fxml/map.fxml", "Mappa del Bosco");
        } catch (IOException e) {
            showAlert("Errore", "Impossibile caricare il salvataggio: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}