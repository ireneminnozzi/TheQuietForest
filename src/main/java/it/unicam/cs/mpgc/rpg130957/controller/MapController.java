package it.unicam.cs.mpgc.rpg130957.controller;

import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameEvent;
import it.unicam.cs.mpgc.rpg130957.model.game.GameEventType;
import it.unicam.cs.mpgc.rpg130957.model.game.GameSession;
import it.unicam.cs.mpgc.rpg130957.model.game.GameState;
import it.unicam.cs.mpgc.rpg130957.model.location.Location;
import it.unicam.cs.mpgc.rpg130957.model.location.LocationType;
import it.unicam.cs.mpgc.rpg130957.model.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import it.unicam.cs.mpgc.rpg130957.navigation.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MapController {

    private GameEngine gameEngine;
    private final GamePersistence persistence = new GamePersistence("savegame.json");

    @FXML
    private Label messageLabel;

    @FXML
    private Button btnWaterfall;
    @FXML
    private Button btnSwamp;
    @FXML
    private Button btnHut;
    @FXML
    private Button btnForest;
    @FXML
    private Button btnCemetery;

    @FXML
    public void initialize() {
        gameEngine = GameSession.getInstance().getEngine();

        if (gameEngine == null) {
            showAlert("Errore", "Nessuna partita in corso. Torna al menu.");
            return;
        }

        updateMapUI();

        if (gameEngine.getGameState() == GameState.WON) {
            SceneNavigator.navigate("/fxml/victory.fxml", "Vittoria!");
            return;
        }

        if (gameEngine.getGameState() == GameState.LOST) {
            SceneNavigator.navigate("/fxml/defeat.fxml", "Sconfitta...");
            return;
        }
    }

    private void updateMapUI() {
        updateButtonState(btnWaterfall, LocationType.WATERFALL);
        updateButtonState(btnSwamp, LocationType.SWAMP);
        updateButtonState(btnForest, LocationType.FOREST);
        updateButtonState(btnCemetery, LocationType.CEMETERY);
        btnHut.setDisable(false);
    }

    private void updateButtonState(Button button, LocationType type) {
        Location loc = gameEngine.getMap().getLocation(type);
        if (loc.isMonsterDefeated()) {
            button.setDisable(true);
            button.setText(type.getDisplayName() + " ✓");
            button.setStyle("-fx-opacity: 0.5; -fx-cursor: default;");
        } else {
            button.setDisable(false);
            button.setText(type.getDisplayName());
            button.setStyle("");
        }
    }

    @FXML
    private void handleWaterfall() { explore(LocationType.WATERFALL); }

    @FXML
    private void handleSwamp() { explore(LocationType.SWAMP); }

    @FXML
    private void handleForest() { explore(LocationType.FOREST); }

    @FXML
    private void handleCemetery() { explore(LocationType.CEMETERY); }

    @FXML
    private void handleHut() {
        GameEvent event = gameEngine.exploreLocation(LocationType.HUT);
        messageLabel.setText(event.getMessage());
        showAlert("Riposo", "Ti sei riposata nella capanna.\nSalute e Mana sono stati completamente ripristinati!");
        saveGame();
    }

    private void explore(LocationType type) {
        GameEvent event = gameEngine.exploreLocation(type);

        if (event.getType() == GameEventType.ERROR) {
            messageLabel.setText(event.getMessage());
        } else if (event.getType() == GameEventType.COMBAT_STARTED) {
            messageLabel.setText(event.getMessage());
            SceneNavigator.navigate("/fxml/combat.fxml", "Combattimento");
        }
    }

    @FXML
    private void handleBackToMenu() {
        SceneNavigator.navigate("/fxml/menu.fxml", "The Quiet Forest");
    }

    private void saveGame() {
        try {
            SaveData data = gameEngine.extractSaveData();
            persistence.save(data);
            System.out.println("Partita salvata automaticamente.");
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}