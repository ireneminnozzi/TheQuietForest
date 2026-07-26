package it.unicam.cs.mpgc.rpg130957.model.potion;

/**
 * Tipologie di pozione disponibili nel gioco.
 *
 * <p>Ogni tipologia ha un effetto specifico:</p>
 * <ul>
 *   <li>{@link #HEALTH}: ripristina punti vita</li>
 *   <li>{@link #MANA}: ripristina punti mana</li>
 *   <li>{@link #POWER}: aumenta permanentemente il danno della strega</li>
 * </ul>
 */
public enum PotionType {

    HEALTH(30, 0, 0, "Pozione di Salute"),
    MANA(0, 20, 0, "Pozione di Mana"),
    POWER(0, 0, 5, "Pozione di Potenza");

    private final int healthRestore;
    private final int manaRestore;
    private final int powerBonus;
    private final String displayName;

    PotionType(int healthRestore, int manaRestore, int powerBonus, String displayName) {
        this.healthRestore = healthRestore;
        this.manaRestore = manaRestore;
        this.powerBonus = powerBonus;
        this.displayName = displayName;
    }

    public int getHealthRestore() { return healthRestore; }
    public int getManaRestore() { return manaRestore; }
    public int getPowerBonus() { return powerBonus; }
    public String getDisplayName() { return displayName; }
}