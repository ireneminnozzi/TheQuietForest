package it.unicam.cs.mpgc.rpg130957.model.game;

/**
 * Definisce lo stato corrente della partita.
 *
 * <p>Viene utilizzato dal {@link GameEngine} per segnalare all'interfaccia grafica
 * se il gioco è in corso, se il giocatore ha vinto o se è stato sconfitto.</p>
 */
public enum GameState {
    /** La partita è in corso. */
    RUNNING,
    /** Il giocatore ha sconfitto tutti i mostri e ha vinto. */
    WON,
    /** Il giocatore è morto in combattimento. */
    LOST
}