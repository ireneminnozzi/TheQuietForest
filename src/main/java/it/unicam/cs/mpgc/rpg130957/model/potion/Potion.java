
package it.unicam.cs.mpgc.rpg130957.model.potion;

import java.util.Objects;

/**
 * Rappresenta una pozione consumabile utilizzabile dalla strega.
 *
 * <p>Le pozioni sono oggetti immutabili: una volta creata, una pozione
 * non può cambiare il suo tipo.</p>
 */
public final class Potion {

    private final PotionType type;

    /**
     * Crea una nuova pozione del tipo specificato.
     *
     * @param type tipo della pozione
     * @throws NullPointerException se il tipo è null
     */
    public Potion(PotionType type) {
        this.type = Objects.requireNonNull(type, "Il tipo della pozione non può essere null");
    }

    /**
     * Restituisce il tipo della pozione.
     *
     * @return tipo della pozione
     */
    public PotionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Potion other)) return false;
        return type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return type.getDisplayName();
    }
}