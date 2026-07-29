package it.unicam.cs.mpgc.rpg130957.model.game;

/**
 * Definisce le tipologie di eventi generati dal motore di gioco.
 *
 * <p>Questi eventi vengono utilizzati come Data Transfer Object (DTO)
 * per comunicare in modo disaccoppiato le azioni avvenute nel model
 * verso il controller dell'interfaccia grafica JavaFX.</p>
 */
public enum GameEventType {
    /** Iniziato un combattimento. */
    COMBAT_STARTED,
    /** Turno di combattimento eseguito. */
    COMBAT_TURN,
    /** Combattimento concluso (vittoria, sconfitta o fuga). */
    COMBAT_ENDED,
    /** La strega si è riposata nella capanna. */
    RESTED,
    /** Errore o azione non valida. */
    ERROR,
    /** Partita vinta. */
    GAME_WON,
    /** Partita persa. */
    GAME_LOST
}