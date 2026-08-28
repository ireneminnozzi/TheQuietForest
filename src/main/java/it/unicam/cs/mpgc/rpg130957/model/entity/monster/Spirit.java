// Spirit.java
package it.unicam.cs.mpgc.rpg130957.model.entity.monster;

/**
 * Rappresenta uno Spirito, l'entità che infesta il cimitero.
 *
 * <p>Lo Spirito è un nemico magico con danno medio-alto e variabile.</p>
 *
 * <ul>
 *   <li>HP: 50</li>
 *   <li>Danno: 8-18</li>
 * </ul>
 */
public final class Spirit extends Monster {

    public Spirit() {
        super("Spirito", 50, 8, 18);
    }
}