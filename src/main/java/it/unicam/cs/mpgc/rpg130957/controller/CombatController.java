package it.unicam.cs.mpgc.rpg130957.controller;

import it.unicam.cs.mpgc.rpg130957.model.combat.CombatAction;
import it.unicam.cs.mpgc.rpg130957.model.combat.CombatSession;
import it.unicam.cs.mpgc.rpg130957.model.entity.Witch;
import it.unicam.cs.mpgc.rpg130957.model.entity.monster.Monster;
import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameEvent;
import it.unicam.cs.mpgc.rpg130957.model.game.GameEventType;
import it.unicam.cs.mpgc.rpg130957.model.game.GameSession;
import it.unicam.cs.mpgc.rpg130957.model.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionType;
import it.unicam.cs.mpgc.rpg130957.model.spell.SpellType;
import it.unicam.cs.mpgc.rpg130957.navigation.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CombatController {

    private GameEngine gameEngine;
    private CombatSession session;
    private final GamePersistence persistence = new GamePersistence("savegame.json");

    private int maxMonsterHp;
    private int maxWitchHp;
    private int maxWitchMana;

    @FXML private Label monsterNameLabel;
    @FXML private Label monsterHpLabel;
    @FXML private Label witchHpLabel;
    @FXML private Label witchManaLabel;
    @FXML private TextArea combatLog;
    @FXML private ImageView monsterSprite;

    @FXML private Button btnAttack;
    @FXML private Button btnSpell;
    @FXML private Button btnPotion;
    @FXML private Button btnFlee;

    @FXML private ComboBox<SpellType> spellCombo;
    @FXML private ComboBox<PotionType> potionCombo;

    @FXML
    public void initialize() {
        gameEngine = GameSession.getInstance().getEngine();

        if (gameEngine == null || !gameEngine.isInCombat()) {
            showAlert("Errore", "Nessun combattimento in corso.");
            return;
        }

        session = gameEngine.getCurrentSession();
        Monster monster = session.getMonster();
        Witch witch = gameEngine.getWitch();

        maxMonsterHp = monster.getHealth();
        maxWitchHp = witch.getHealth();
        maxWitchMana = witch.getMana();

        monsterNameLabel.setText(monster.getDisplayName());
        updateMonsterSprite(monster);
        updateStats();
        setupComboBoxes(witch);
    }

    private void setupComboBoxes(Witch witch) {
        spellCombo.getItems().addAll(SpellType.values());
        spellCombo.getSelectionModel().selectFirst();
        updatePotionCombo(witch);
    }

    private void updatePotionCombo(Witch witch) {
        potionCombo.getItems().clear();
        for (PotionType type : PotionType.values()) {
            if (witch.hasPotion(type)) {
                potionCombo.getItems().add(type);
            }
        }

        if (!potionCombo.getItems().isEmpty()) {
            potionCombo.getSelectionModel().selectFirst();
            potionCombo.setDisable(false);
        } else {
            potionCombo.setPromptText("Nessuna pozione");
            potionCombo.setDisable(true);
        }
    }

    private void updateStats() {
        Monster monster = session.getMonster();
        Witch witch = gameEngine.getWitch();

        monsterHpLabel.setText("HP: " + monster.getHealth() + "/" + maxMonsterHp);
        witchHpLabel.setText("HP: " + witch.getHealth() + "/" + maxWitchHp);
        witchManaLabel.setText("Mana: " + witch.getMana() + "/" + maxWitchMana);
    }

    private void updateMonsterSprite(Monster monster) {
        String spritePath = switch (monster.getDisplayName()) {
            case "Goblin" -> "/img/enemy_goblin.png";
            case "Troll" -> "/img/enemy_troll.png";
            case "Drago" -> "/img/enemy_drago.png";
            case "Spirito" -> "/img/enemy_spirito.png";
            default -> "/img/enemy_goblin.png";
        };

        Image image = new Image(getClass().getResourceAsStream(spritePath));
        monsterSprite.setImage(image);
    }

    @FXML
    private void handleAttack() {
        executeAction(CombatAction.ATTACK, null, null);
    }

    @FXML
    private void handleSpell() {
        SpellType spell = spellCombo.getValue();
        if (spell == null) return;
        executeAction(CombatAction.SPELL, spell, null);
    }

    @FXML
    private void handlePotion() {
        PotionType potion = potionCombo.getValue();
        if (potion == null) return;
        executeAction(CombatAction.POTION, null, potion);
    }

    @FXML
    private void handleFlee() {
        executeAction(CombatAction.FLEE, null, null);
    }

    private void executeAction(CombatAction action, SpellType spell, PotionType potion) {
        try {
            GameEvent event = gameEngine.performCombatAction(action, spell, potion);

            combatLog.appendText(event.getMessage() + "\n");
            updateStats();
            updatePotionCombo(gameEngine.getWitch());

            if (event.getType() == GameEventType.COMBAT_ENDED ||
                    event.getType() == GameEventType.GAME_WON ||
                    event.getType() == GameEventType.GAME_LOST) {

                setControlsDisabled(true);
                saveGame();
                showAlert("Combattimento Terminato", event.getMessage());

                if (event.getType() == GameEventType.GAME_WON) {
                    SceneNavigator.navigate("/fxml/victory.fxml", "Vittoria!");
                } else if (event.getType() == GameEventType.GAME_LOST) {
                    SceneNavigator.navigate("/fxml/defeat.fxml", "Sconfitta...");
                } else {
                    SceneNavigator.navigate("/fxml/map.fxml", "Mappa del Bosco");
                }
            } else if (event.getType() == GameEventType.ERROR) {
                showAlert("Azione non valida", event.getMessage());
            }

        } catch (IllegalArgumentException e) {
            showAlert("Azione non possibile", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore Critico", "Si è verificato un errore: " + e.getMessage());
        }
    }

    private void setControlsDisabled(boolean disabled) {
        btnAttack.setDisable(disabled);
        btnSpell.setDisable(disabled);
        btnPotion.setDisable(disabled);
        btnFlee.setDisable(disabled);
        spellCombo.setDisable(disabled);
        potionCombo.setDisable(disabled);
    }

    private void saveGame() {
        try {
            SaveData data = gameEngine.extractSaveData();
            persistence.save(data);
            System.out.println("Partita salvata automaticamente dopo combattimento.");
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