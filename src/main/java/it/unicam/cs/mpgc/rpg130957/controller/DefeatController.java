package it.unicam.cs.mpgc.rpg130957.controller;

import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg130957.model.game.GameSession;
import it.unicam.cs.mpgc.rpg130957.navigation.SceneNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per la schermata di sconfitta.
 */
public class DefeatController {

    private final GameFactory gameFactory = new GameFactory();

    @FXML
    private void handleRetry() {
        GameEngine engine = gameFactory.createNewGame();
        GameSession.getInstance().setEngine(engine);
        SceneNavigator.navigate("/fxml/map.fxml", "Mappa del Bosco");
    }

    @FXML
    private void handleMainMenu() {
        GameSession.getInstance().clear();
        SceneNavigator.navigate("/fxml/menu.fxml", "The Quiet Forest");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
