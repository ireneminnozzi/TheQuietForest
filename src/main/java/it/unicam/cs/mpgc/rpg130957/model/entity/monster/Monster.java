package it.unicam.cs.mpgc.rpg130957.model.entity.monster;

import it.unicam.cs.mpgc.rpg130957.model.entity.Entity;

/**
 * Rappresenta un mostro generico del bosco.
 *
 * <p>Un mostro è una {@link Entity} caratterizzata da:</p>
 * <ul>
 *   <li>Nome e salute (ereditati da Entity)</li>
 *   <li>Danno minimo e massimo infliggibile</li>
 * </ul>
 *
 * <p>Questa è una sealed class: può essere estesa
 * esclusivamente dalle quattro classi autorizzate
 * ({@link Goblin}, {@link Troll}, {@link Dragon}, {@link Spirit}).
 * Il compilatore garantisce che nessun altro tipo di mostro possa
 * essere aggiunto senza modificare esplicitamente questa dichiarazione,
 * rendendo l'architettura del gioco sicura e prevedibile.</p>
 */
public abstract sealed class Monster extends Entity
        permits Goblin, Troll, Dragon, Spirit {

    private final int minDamage;
    private final int maxDamage;

    /**
     * Crea un nuovo mostro con valori di base per il combattimento.
     *
     * @param name        nome del mostro
     * @param health      punti vita iniziali
     * @param minDamage   danno minimo infliggibile
     * @param maxDamage   danno massimo infliggibile
     * @throws IllegalArgumentException se i danni sono negativi o min > max
     */
    protected Monster(String name, int health, int minDamage, int maxDamage) {
        super(name, health);
        if (minDamage < 0 || maxDamage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        if (minDamage > maxDamage) {
            throw new IllegalArgumentException(
                    "minDamage (" + minDamage + ") cannot be greater than maxDamage (" + maxDamage + ")");
        }
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public int getMinDamage() {
        return minDamage;
    }

    @Override
    public int getMaxDamage() {
        return maxDamage;
    }

}