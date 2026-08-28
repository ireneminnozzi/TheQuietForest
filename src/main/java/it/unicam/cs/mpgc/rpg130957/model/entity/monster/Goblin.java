// Goblin.java
package it.unicam.cs.mpgc.rpg130957.model.entity.monster;

/**
 * Rappresenta un Goblin, il guardiano delle cascate.
 *
 * <p>Il Goblin è un nemico debole ma agile, ideale come primo
 * scontro per il giocatore.</p>
 *
 * <ul>
 *   <li>HP: 30</li>
 *   <li>Danno: 5-10</li>
 * </ul>
 */
public final class Goblin extends Monster {

    public Goblin() {
        super("Goblin", 30, 5, 1)
;                }
}