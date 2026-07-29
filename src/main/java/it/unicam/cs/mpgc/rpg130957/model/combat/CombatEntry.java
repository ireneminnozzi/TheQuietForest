package it.unicam.cs.mpgc.rpg130957.model.combat;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) che rappresenta una singola azione avvenuta durante un combattimento.
 *
 * <p>Questa classe è immutabile e viene utilizzata per costruire lo storico
 * (log) del combattimento, utile per mostrare all'utente cosa è successo turno per turno.</p>
 */
public final class CombatEntry {

    private final String description;
    private final int damageDealt;

    /**
     * Crea una nuova voce del registro di combattimento.
     *
     * @param description descrizione testuale dell'azione
     * @param damageDealt danno inflitto dall'azione (0 se l'azione è di cura o fuga)
     */
    public CombatEntry(String description, int damageDealt) {
        this.description = Objects.requireNonNull(description, "La descrizione non può essere null");
        if (damageDealt < 0) {
            throw new IllegalArgumentException("I danni inflitti non possono essere negativi");
        }
        this.damageDealt = damageDealt;
    }

    public String getDescription() {
        return description;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombatEntry that)) return false;
        return damageDealt == that.damageDealt && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, damageDealt);
    }

    @Override
    public String toString() {
        return description + " (" + damageDealt + " danno)";
    }
}