package it.unicam.cs.mpgc.rpg130957.model.game;

import it.unicam.cs.mpgc.rpg130957.model.combat.CombatAction;
import it.unicam.cs.mpgc.rpg130957.model.combat.CombatResult;
import it.unicam.cs.mpgc.rpg130957.model.combat.CombatSession;
import it.unicam.cs.mpgc.rpg130957.model.combat.CombatSystem;
import it.unicam.cs.mpgc.rpg130957.model.entity.Witch;
import it.unicam.cs.mpgc.rpg130957.model.location.ForestMap;
import it.unicam.cs.mpgc.rpg130957.model.location.Location;
import it.unicam.cs.mpgc.rpg130957.model.location.LocationType;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;
import it.unicam.cs.mpgc.rpg130957.model.potion.Potion;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionFactory;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionType;
import it.unicam.cs.mpgc.rpg130957.model.spell.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Motore principale del gioco.
 *
 * <p>Coordina la strega, la mappa e il sistema di combattimento.
 * Rappresenta il punto di ingresso per il Controller JavaFX:
 * l'interfaccia grafica non chiama mai direttamente la Witch o il CombatSystem,
 * ma invoca i metodi pubblici di questa classe.</p>
 *
 */

public final class GameEngine {
    private final Witch witch;
    private final ForestMap map;
    private final CombatSystem combatSystem;
    private final PotionFactory potionFactory;
    private final GameRandom random;

    private CombatSession currentSession;
    private GameState gameState;

    /**
     * Crea una nuova istanza del motore di gioco.
     *
     * @param witch        la strega protagonista
     * @param map          la mappa del bosco
     * @param combatSystem il sistema di calcolo del combattimento
     * @param potionFactory la factory per generare le ricompense
     */
    public GameEngine(Witch witch, ForestMap map, CombatSystem combatSystem, PotionFactory potionFactory, GameRandom random) {
        this.witch = Objects.requireNonNull(witch);
        this.map = Objects.requireNonNull(map);
        this.combatSystem = Objects.requireNonNull(combatSystem);
        this.potionFactory = Objects.requireNonNull(potionFactory);
        this.random = Objects.requireNonNull(random);
        this.gameState = GameState.RUNNING;
    }

    /**
     * Tenta di esplorare una location selezionata dalla mappa.
     *
     * <p>Se la location è la Capanna, la strega si riposa.
     * Se la location ha un mostro vivo, inizia una sessione di combattimento.
     * Se il mostro è già stato sconfitto, restituisce un evento di errore.</p>
     *
     * @param locationType il tipo di location da esplorare
     * @return evento di gioco risultante dall'azione
     */
    public GameEvent exploreLocation(LocationType locationType) {
        if (gameState != GameState.RUNNING) {
            return new GameEvent(GameEventType.ERROR, "La partita è già terminata.", null);
        }

        Location location = map.getLocation(locationType);
        if (location == null) {
            return new GameEvent(GameEventType.ERROR, "Location non valida.", null);
        }

        // Caso 1: Capanna (Riposo)
        if (location.isSafeZone()) {
            witch.rest();
            return new GameEvent(GameEventType.RESTED, "Ti sei riposata nella capanna. Salute e Mana ripristinate!", null);
        }

        // Caso 2: Location con mostro già sconfitto
        if (location.isMonsterDefeated()) {
            return new GameEvent(GameEventType.ERROR, "Hai già sconfitto il mostro in questa zona.", null);
        }

        // Caso 3: Inizio Combattimento
        currentSession = new CombatSession(witch, location.getMonster(), combatSystem);
        String msg = "Hai incontrato " + location.getMonster().getDisplayName() + "! Preparati al combattimento.";
        return new GameEvent(GameEventType.COMBAT_STARTED, msg, null);
    }

    /**
     * Esegue un turno di combattimento basato sulla scelta del giocatore.
     *
     * @param action azione scelta (ATTACK, SPELL, POTION, FLEE)
     * @param spell  incantesimo da lanciare (necessario solo se action è SPELL)
     * @param potion tipo di pozione da usare (necessario solo se action è POTION)
     * @return evento di gioco contenente l'esito del turno
     * @throws IllegalStateException se non c'è un combattimento in corso
     */
    public GameEvent performCombatAction(CombatAction action, Spell spell, PotionType potion) {
        if (currentSession == null || !gameState.equals(GameState.RUNNING)) {
            return new GameEvent(GameEventType.ERROR, "Nessun combattimento in corso.", null);
        }

        // Esecuzione del turno (delegata alla CombatSession)
        String turnReport = currentSession.executeTurn(action, spell, potion);

        // Controllo fine combattimento
        if (currentSession.isEnded()) {
            return handleCombatEnd(turnReport);
        }

        // Combattimento ancora in corso
        return new GameEvent(GameEventType.COMBAT_TURN, turnReport, null);
    }

    /**
     * Gestisce la conclusione di un combattimento, aggiornando mappa, stato e ricompense.
     */
    private GameEvent handleCombatEnd(String finalReport) {
        CombatResult result = currentSession.finalizeCombat();
        Location currentLocation = map.getLocation(getCurrentCombatLocationType());

        // Reset della sessione
        currentSession = null;

        if (result.isWitchWon()) {
            // Vittoria: marca il mostro come sconfitto e dà una pozione
            currentLocation.setMonsterDefeated();
            Potion reward = potionFactory.createRandomReward();
            witch.addPotion(reward);

            String msg = finalReport + "\nHai vinto! Hai trovato: " + reward.getType().getDisplayName() + ".";

            // Controllo vittoria globale
            if (map.isGameWon()) {
                gameState = GameState.WON;
                return new GameEvent(GameEventType.GAME_WON, msg + "\n\nHAI SCONFITTO TUTTI I MOSTRI! HAI VINTO LA PARTITA! ", result);
            }
            return new GameEvent(GameEventType.COMBAT_ENDED, msg, result);

        } else if (result.isFled()) {
            // Fuga
            return new GameEvent(GameEventType.COMBAT_ENDED, finalReport, result);

        } else {
            // Sconfitta (Morte)
            gameState = GameState.LOST;
            return new GameEvent(GameEventType.GAME_LOST, finalReport + "\n\nLa strega è caduta... GAME OVER.", result);
        }
    }

    /**
     * Recupera il tipo di location in cui si sta svolgendo il combattimento attuale.
     * Utilizzato internamente per aggiornare la mappa corretta alla fine dello scontro.
     */
    private LocationType getCurrentCombatLocationType() {
        for (LocationType type : LocationType.values()) {
            Location loc = map.getLocation(type);
            if (!loc.isSafeZone() && !loc.isMonsterDefeated() && loc.getMonster() == currentSession.getMonster()) {
                return type;
            }
        }
        return LocationType.HUT; // Fallback
    }

    //  getter per la UI
    /**
     * Restituisce la strega protagonista.
     * @return istanza della Witch
     */
    public Witch getWitch() {
        return witch;
    }

    /**
     * Restituisce la mappa del gioco.
     * @return istanza della ForestMap
     */
    public ForestMap getMap() {
        return map;
    }

    /**
     * Restituisce lo stato corrente della partita.
     * @return stato di gioco
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Verifica se c'è un combattimento in corso.
     * @return true se la strega sta combattendo
     */
    public boolean isInCombat() {
        return currentSession != null;
    }

    /**
     * Restituisce la sessione di combattimento attuale.
     * @return sessione di combattimento, o null se non in combattimento
     */
    public CombatSession getCurrentSession() {
        return currentSession;
    }

    /**
     * Ripristina lo stato della partita (utile per il caricamento).
     */
    public void restoreGameState(GameState state) {
        this.gameState = state;
    }

    /**
     * Estrae i dati correnti per creare un oggetto SaveData.
     */
    public SaveData extractSaveData() {
        List<String> potions = new ArrayList<>();
        for (Potion p : witch.getInventory()) {
            potions.add(p.getType().name());
        }

        List<String> defeated = new ArrayList<>();
        for (LocationType type : LocationType.values()) {
            if (type != LocationType.HUT && map.getLocation(type).isMonsterDefeated()) {
                defeated.add(type.name());
            }
        }

        return new SaveData(
                combatSystem.getRandom().getSeed(), // *Nota: aggiungi getSeed() a CombatSystem o GameRandom
                witch.getHealth(),
                witch.getMana(),
                witch.getPowerBonus(),
                potions,
                defeated,
                gameState.name()
        );
    }

    /**
     * Restituisce il generatore casuale utilizzato dal sistema.
     *
     * <p>Utilizzato principalmente per estrarre il seed durante
     * le operazioni di salvataggio della partita.</p>
     *
     * @return istanza del GameRandom
     */
    public GameRandom getRandom() {
        return random;
    }
}