package it.unicam.cs.mpgc.rpg130957.model.game;

import it.unicam.cs.mpgc.rpg130957.model.combat.CombatResult;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) che rappresenta un evento di gioco.
 *
 * <p>Incapsula il risultato di un'azione eseguita dal giocatore e trasporta le informazioni necessarie
 * al Controller JavaFX per aggiornare l'interfaccia utente.</p>
 *
 * <p>Questa classe è immutabile per garantire la sicurezza dei dati durante il trasferimento.</p>
 */
public final class GameEvent {

    private final GameEventType type;
    private final String message;
    private final CombatResult combatResult;

    /**
     * Crea un nuovo evento di gioco.
     *
     * @param type          tipologia dell'evento
     * @param message       messaggio testuale descrittivo (può essere null)
     * @param combatResult  risultato del combattimento, se l'evento è legato a un combattimento (può essere null)
     */
    public GameEvent(GameEventType type, String message, CombatResult combatResult) {
        this.type = Objects.requireNonNull(type, "Il tipo di evento non può essere nullo");
        this.message = message;
        this.combatResult = combatResult;
    }

    /**
     * Restituisce la tipologia dell'evento.
     * @return tipo di evento
     */
    public GameEventType getType() {
        return type;
    }

    /**
     * Restituisce il messaggio descrittivo dell'evento.
     * @return messaggio testuale
     */
    public String getMessage() {
        return message;
    }

    /**
     * Restituisce il risultato del combattimento associato all'evento.
     * @return risultato del combattimento, o null se non applicabile
     */
    public CombatResult getCombatResult() {
        return combatResult;
    }
}