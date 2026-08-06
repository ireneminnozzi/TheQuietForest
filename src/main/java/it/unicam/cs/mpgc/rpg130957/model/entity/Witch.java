package it.unicam.cs.mpgc.rpg130957.model.entity;

import it.unicam.cs.mpgc.rpg130957.model.potion.Potion;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionType;
import it.unicam.cs.mpgc.rpg130957.model.spell.Spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta la strega, protagonista del gioco.
 *
 * <p>La strega possiede:</p>
 * <ul>
 *   <li>Punti vita e punti mana</li>
 *   <li>Un bonus di danno base</li>
 *   <li>un inventario di pozioni</li>
 *   <li>Una lista di incantesimi conosciuti</li>
 * </ul>
 *
 * <p>Il danno inflitto dalla strega in un attacco base è calcolato come
 * {@code baseDamage + bonusPotenza}, dove {@code baseDamage} è un valore
 * fisso (5-10) e {@code bonusPotenza} aumenta permanentemente
 * ogni volta che viene bevuta una pozione di potenza.</p>
 */
public final class Witch extends Entity {

    /** Danno base minimo della strega. */
    private static final int BASE_MIN_DAMAGE = 5;
    /** Danno base massimo della strega. */
    private static final int BASE_MAX_DAMAGE = 10;

    /** Salute massima. */
    private static final int MAX_HEALTH = 100;
    /** Mana massimo. */
    private static final int MAX_MANA = 50;

    private int mana;
    private int powerBonus;
    private final List<Potion> inventory;
    private final List<Spell> knownSpells;

    /**
     * Crea una nuova strega con salute e mana pieni.
     *
     * <p>La strega viene inizializzata con:</p>
     * <ul>
     *   <li>100 punti vita</li>
     *   <li>50 punti mana</li>
     *   <li>Inventario vuoto</li>
     *   <li>Lista incantesimi vuota (verrà popolata dal gioco)</li>
     * </ul>
     */
    public Witch() {
        super("Strega", MAX_HEALTH);
        this.mana = MAX_MANA;
        this.powerBonus = 0;
        this.inventory = new ArrayList<>();
        this.knownSpells = new ArrayList<>();
    }

    @Override
    public int getMinDamage() {
        return BASE_MIN_DAMAGE + powerBonus;
    }

    @Override
    public int getMaxDamage() {
        return BASE_MAX_DAMAGE + powerBonus;
    }

    /**
     * Restituisce il mana corrente della strega.
     *
     * @return mana corrente
     */
    public int getMana() {
        return mana;
    }

    /**
     * Riduce il mana della strega.
     *
     * @param amount quantità di mana da consumare
     * @throws IllegalArgumentException se il mana diventasse negativo
     */
    public void consumeMana(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("La quantità di mana non può essere negativa");
        }
        if (amount > mana) {
            throw new IllegalStateException("Mana insufficiente");
        }
        mana -= amount;
    }

    /**
     * Ripristina il mana della strega aggiungendo una quantità, senza superare il massimo.
     *
     * <p>Utilizzato quando la strega beve una pozione di mana.</p>
     *
     * @param amount quantità di mana da aggiungere
     */
    public void recoverMana(int amount) {
        if (amount <= 0) return;
        mana = Math.min(MAX_MANA, mana + amount);
    }

    /**
     * Ripristina la salute della strega, senza superare il massimo.
     *
     * @param amount quantità di salute da ripristinare
     */
    public void heal(int amount) {
        if (amount <= 0) return;
        int newHealth = Math.min(MAX_HEALTH, getHealth() + amount);
        setHealth(newHealth);
    }

    /**
     * Ripristina completamente salute e mana (riposo nella capanna).
     */
    public void rest() {
        setHealth(MAX_HEALTH);
        mana = MAX_MANA;
    }

    /**
     * Aumenta permanentemente il bonus di danno della strega.
     *
     * @param amount quantità di bonus da aggiungere
     */
    public void increasePower(int amount) {
        if (amount <= 0) return;
        powerBonus += amount;
    }

    /**
     * Restituisce il bonus di danno attuale.
     *
     * @return bonus di danno
     */
    public int getPowerBonus() {
        return powerBonus;
    }

    /**
     * Aggiunge una pozione all'inventario della strega.
     *
     * @param potion pozione da aggiungere
     * @throws NullPointerException se la pozione è null
     */
    public void addPotion(Potion potion) {
        Objects.requireNonNull(potion, "La pozione non può essere null");
        inventory.add(potion);
    }

    /**
     * Rimuove e restituisce una pozione del tipo specificato dall'inventario.
     *
     * @param type tipo di pozione da consumare
     * @return la pozione consumata
     * @throws IllegalStateException se non ci sono pozioni del tipo richiesto
     */
    public Potion consumePotion(PotionType type) {
        Potion found = inventory.stream()
                .filter(p -> p.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Nessuna pozione di tipo " + type + " nell'inventario"));
        inventory.remove(found);
        return found;
    }

    /**
     * Verifica se la strega possiede almeno una pozione del tipo specificato.
     *
     * @param type tipo di pozione da verificare
     * @return true se la pozione è presente nell'inventario
     */
    public boolean hasPotion(PotionType type) {
        return inventory.stream().anyMatch(p -> p.getType() == type);
    }

    /**
     * Restituisce una vista immutabile dell'inventario delle pozioni.
     *
     * @return lista immutabile delle pozioni
     */
    public List<Potion> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    /**
     * Aggiunge un incantesimo alla lista degli incantesimi conosciuti.
     *
     * @param spell incantesimo da aggiungere
     * @throws NullPointerException se l'incantesimo è null
     */
    public void learnSpell(Spell spell) {
        Objects.requireNonNull(spell, "L'incantesimo non può essere nullo");
        if (!knownSpells.contains(spell)) {
            knownSpells.add(spell);
        }
    }

    /**
     * Verifica se la strega conosce un determinato incantesimo.
     *
     * @param spell incantesimo da verificare
     * @return true se l'incantesimo è conosciuto
     */
    public boolean knowsSpell(Spell spell) {
        return knownSpells.contains(spell);
    }

    /**
     * Verifica se la strega ha abbastanza mana per lanciare un incantesimo.
     *
     * @param spell incantesimo da verificare
     * @return true se il mana è sufficiente
     */
    public boolean canCast(Spell spell) {
        return mana >= spell.getManaCost();
    }

    /**
     * Restituisce una vista immutabile degli incantesimi conosciuti.
     *
     * @return lista immutabile degli incantesimi
     */
    public List<Spell> getKnownSpells() {
        return Collections.unmodifiableList(knownSpells);
    }

    /**
     * Ripristina l'inventario delle pozioni durante il caricamento di una partita.
     *
     * @param potions lista di pozioni da ripristinare
     */
    public void restoreInventory(List<Potion> potions) {
        inventory.clear();
        inventory.addAll(potions);
    }

    /**
     * Imposta il mana della strega durante il caricamento di una partita.
     *
     * @param mana valore del mana da impostare
     * @throws IllegalArgumentException se il mana non è compreso tra 0 e MAX_MANA
     */
    public void restoreMana(int mana) {
        if (mana < 0 || mana > MAX_MANA) {
            throw new IllegalArgumentException("Valore mana errato: " + mana);
        }
        this.mana = mana;
    }

    /**
     * Ripristina il bonus di potenza durante il caricamento di una partita.
     *
     * @param powerBonus valore del bonus da ripristinare
     * @throws IllegalArgumentException se il bonus è negativo
     */
    public void restorePowerBonus(int powerBonus) {
        if (powerBonus < 0) {
            throw new IllegalArgumentException("Il bonus non può essere negativo");
        }
        this.powerBonus = powerBonus;
    }

    /**
     * Imposta direttamente la salute per il caricamento di una partita salvata.
     *
     * @param health valore della salute da impostare
     * @throws IllegalArgumentException se il valore non è compreso tra 0 e 100
     */
    public void restoreHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Invalid health value: " + health);
        }
        setHealth(health);
    }
}