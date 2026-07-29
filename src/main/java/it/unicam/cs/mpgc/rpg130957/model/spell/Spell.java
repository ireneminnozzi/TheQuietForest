package it.unicam.cs.mpgc.rpg130957.model.spell;

/**
 * Rappresenta un incantesimo utilizzabile dalla strega.
 *
 * <p>Ogni incantesimo ha un costo in mana e un intervallo di danno.</p>
 */
public interface Spell {


    String getName();

//costo in mana dell'incantesimo
    int getManaCost();

//danno minimo/massimo inflitto dall'incantesimo
    int getMinDamage();

    int getMaxDamage();
}