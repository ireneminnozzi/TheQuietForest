package it.unicam.cs.mpgc.rpg130957.model.persistence;

import java.util.List;

/**
 * Data Transfer Object (DTO) per la persistenza della partita.
 *
 * <p>Contiene esclusivamente i dati primitivi e le liste di stringhe
 * necessari per salvare e ricaricare lo stato del gioco tramite GSON.
 * Non contiene riferimenti a oggetti complessi del model.</p>
 */
public class SaveData {

    private long seed;
    private int witchHealth;
    private int witchMana;
    private int powerBonus;
    private List<String> inventoryPotions;   // nomi pozioni
    private List<String> defeatedLocations;  // nomi location
    private String gameState;                // "RUNNING", "WON", "LOST"

    /**
     * Costruttore vuoto richiesto da GSON per la deserializzazione.
     */
    public SaveData() {
    }

    /**
     * Costruttore completo utilizzato per il salvataggio.
     */
    public SaveData(long seed, int witchHealth, int witchMana, int powerBonus,
                    List<String> inventoryPotions, List<String> defeatedLocations, String gameState) {
        this.seed = seed;
        this.witchHealth = witchHealth;
        this.witchMana = witchMana;
        this.powerBonus = powerBonus;
        this.inventoryPotions = inventoryPotions;
        this.defeatedLocations = defeatedLocations;
        this.gameState = gameState;
    }

    public long getSeed() { return seed; }
    public int getWitchHealth() { return witchHealth; }
    public int getWitchMana() { return witchMana; }
    public int getPowerBonus() { return powerBonus; }
    public List<String> getInventoryPotions() { return inventoryPotions; }
    public List<String> getDefeatedLocations() { return defeatedLocations; }
    public String getGameState() { return gameState; }
}