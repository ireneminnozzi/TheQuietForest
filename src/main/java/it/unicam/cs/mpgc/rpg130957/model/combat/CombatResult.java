package it.unicam.cs.mpgc.rpg130957.model.combat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) che contiene il risultato finale di un combattimento.
 *
 * <p>Viene restituito dalla {@link CombatSession} al termine dello scontro
 * e utilizzato dal {@code GameEngine} per aggiornare lo stato del gioco
 * (vittoria, sconfitta, fuga) e generare le eventuali ricompense.</p>
 */
public final class CombatResult {

    private final boolean witchWon;
    private final boolean fled;
    private final List<CombatEntry> history;

    /**
     * Crea un nuovo risultato di combattimento.
     *
     * @param witchWon true se la strega ha sconfitto il mostro
     * @param fled     true se la strega è fuggita
     * @param history  lista immutabile delle azioni avvenute durante il combattimento
     */
    public CombatResult(boolean witchWon, boolean fled, List<CombatEntry> history) {
        this.witchWon = witchWon;
        this.fled = fled;
        this.history = Collections.unmodifiableList(Objects.requireNonNull(history, "History cannot be null"));
    }

    public boolean isWitchWon() {
        return witchWon;
    }

    public boolean isFled() {
        return fled;
    }

    /**
     * Restituisce una vista immutabile dello storico del combattimento.
     *
     * @return lista delle azioni registrate
     */
    public List<CombatEntry> getHistory() {
        return history;
    }
}