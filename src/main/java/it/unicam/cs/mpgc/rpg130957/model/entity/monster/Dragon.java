// Dragon.java
package it.unicam.cs.mpgc.rpg130957.model.entity.monster;

/**
 * Rappresenta un Drago, il mostro del bosco.
 *
 * <p>Il Drago è il nemico più potente del gioco, un boss finale
 * che richiede strategia e pozioni per essere sconfitto.</p>
 *
 * <ul>
 *   <li>HP: 100</li>
 *   <li>Danno: 15-30</li>
 * </ul>
 */
public final class Dragon extends Monster {

    public Dragon() {
        super("Drago", 100, 15, 30);
    }
}