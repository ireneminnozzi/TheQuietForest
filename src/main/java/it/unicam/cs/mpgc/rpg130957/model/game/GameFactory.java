package it.unicam.cs.mpgc.rpg130957.model.game;

import it.unicam.cs.mpgc.rpg130957.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg130957.model.entity.Witch;
import it.unicam.cs.mpgc.rpg130957.model.location.ForestMap;
import it.unicam.cs.mpgc.rpg130957.model.location.Location;
import it.unicam.cs.mpgc.rpg130957.model.location.LocationType;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import it.unicam.cs.mpgc.rpg130957.model.potion.Potion;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionFactory;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory responsabile della creazione delle partite.
 *
 * <p>Incapsula la logica di inizializzazione del {@link GameEngine},
 * sia per una nuova partita che per il caricamento di un salvataggio.</p>
 */
public class GameFactory {

    private static final int INITIAL_PLAYER_HEALTH = 100;

    /**
     * Crea una nuova partita con seed casuale (timestamp).
     *
     * @return nuovo GameEngine pronto per il gioco
     */
    public GameEngine createNewGame() {
        long seed = System.currentTimeMillis();
        GameRandom random = new GameRandom(seed);
        CombatSystem combatSystem = new CombatSystem(random);
        PotionFactory potionFactory = new PotionFactory(random);

        return new GameEngine(
                new Witch(),
                new ForestMap(),
                combatSystem,
                potionFactory,
                random
        );
    }

    /**
     * Ricostruisce una partita a partire dai dati salvati.
     *
     * @param data dati del salvataggio
     * @return GameEngine ripristinato
     */
    public GameEngine loadGame(SaveData data) {
        GameRandom random = new GameRandom(data.getSeed());
        Witch witch = restoreWitch(data);
        ForestMap map = restoreMap(data);
        CombatSystem combatSystem = new CombatSystem(random);
        PotionFactory potionFactory = new PotionFactory(random);

        GameEngine engine = new GameEngine(
                witch,
                map,
                combatSystem,
                potionFactory,
                random
        );
        engine.restoreGameState(GameState.valueOf(data.getGameState()));

        return engine;
    }



    private GameEngine buildEngine(GameRandom random, Witch witch, ForestMap map, PotionFactory potionFactory) {
        CombatSystem combatSystem = new CombatSystem(random);
        return new GameEngine(witch, map, combatSystem, potionFactory, random);
    }

    private Witch restoreWitch(SaveData data) {
        Witch witch = new Witch();
        witch.restoreHealth(data.getWitchHealth());
        witch.restoreMana(data.getWitchMana());
        witch.restorePowerBonus(data.getPowerBonus());

        for (String potionName : data.getInventoryPotions()) {
            witch.addPotion(new Potion(PotionType.valueOf(potionName)));
        }
        return witch;
    }

    private ForestMap restoreMap(SaveData data) {
        ForestMap map = new ForestMap();
        for (String locName : data.getDefeatedLocations()) {
            LocationType type = LocationType.valueOf(locName);
            map.getLocation(type).setMonsterDefeated();
        }
        return map;
    }
}