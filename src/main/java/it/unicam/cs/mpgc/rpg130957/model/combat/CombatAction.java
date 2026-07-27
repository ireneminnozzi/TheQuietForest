package it.unicam.cs.mpgc.rpg130957.model.combat;

/**
 * Rappresenta le azioni strategiche disponibili per la strega durante un combattimento.
 */
public enum CombatAction {
    /** Attacco fisico base (nessun costo di mana). */
    ATTACK,
    /** Lancio di un incantesimo (costa mana). */
    SPELL,
    /** Utilizzo di una pozione (cura, mana o potenza). */
    POTION,
    /** Tentativo di fuga (50% di probabilità di successo). */
    FLEE
}