package it.unicam.cs.mpgc.rpg130957.model.entity;

import java.util.Objects;

/**
 * Rappresenta una generica entità combattente del gioco.
 *
 * <p>Fornisce la gestione della salute e delle informazioni
 * necessarie al sistema di combattimento. Sia la strega che i mostri
 * estendono questa classe.</p>
 *
 */
public abstract class Entity {

    private final String displayName;
    private int health;

    /**
     * Crea una nuova entità.
     *
     * @param displayName nome visualizzato dell'entità
     * @param health      salute iniziale
     * @throws IllegalArgumentException se il nome è vuoto o la salute è negativa
     * @throws NullPointerException     se il nome è null
     */
    protected Entity(String displayName, int health) {
        this.displayName = Objects.requireNonNull(displayName, "Display name cannot be null");
        if (displayName.isBlank()) {
            throw new IllegalArgumentException("Display name cannot be blank");
        }
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health = health;
    }

    /**
     * Restituisce il nome visualizzato dell'entità.
     *
     * @return nome visualizzato
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Restituisce la salute corrente dell'entità.
     *
     * @return salute corrente
     */
    public int getHealth() {
        return health;
    }

    /**
     * Riduce la salute dell'entità.
     *
     * <p>La salute non può scendere sotto zero.</p>
     *
     * @param damage danno subito
     * @throws IllegalArgumentException se il danno è negativo
     */
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        health = Math.max(0, health - damage);
    }

    /**
     * Verifica se l'entità è ancora viva.
     *
     * @return true se la salute è maggiore di zero
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Restituisce il danno minimo che l'entità può infliggere.
     *
     * @return danno minimo
     */
    public abstract int getMinDamage();

    /**
     * Restituisce il danno massimo che l'entità può infliggere.
     *
     * @return danno massimo
     */
    public abstract int getMaxDamage();

    /**
     * Incrementa la salute dell'entità.
     *
     * @param amount quantità da aggiungere
     */
    protected void increaseHealth(int amount) {
        if (amount <= 0) return;
        this.health += amount;
    }

    /**
     * Imposta direttamente la salute dell'entità.
     *
     * <p>Utilizzato principalmente durante operazioni di ripristino
     * dello stato di gioco (es. riposo nella capanna).</p>
     *
     * @param health nuova salute
     * @throws IllegalArgumentException se la salute è negativa
     */
    protected void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Health cannot be negative");
        }
        this.health = health;
    }
}