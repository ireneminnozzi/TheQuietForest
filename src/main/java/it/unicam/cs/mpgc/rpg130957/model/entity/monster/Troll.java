// Troll.java
package it.unicam.cs.mpgc.rpg130957.model.entity.monster;

/**
 * Rappresenta un Troll, il custode della palude.
 *
 * <p>Il Troll è un nemico robusto e lento, con un alto potenziale di danno.</p>
 *
 * <ul>
 *   <li>HP: 60</li>
 *   <li>Danno: 10-20</li>
 * </ul>
 */
public final class Troll extends Monster {

    public Troll() {
        super("Troll", 60, 10, 20);
    }
}