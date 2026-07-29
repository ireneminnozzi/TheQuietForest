package it.unicam.cs.mpgc.rpg130957.model.game;

/**
 * Singleton che mantiene il riferimento alla partita attualmente in esecuzione.
 *
 * <p>Viene utilizzato dai Controller JavaFX per condividere la stessa istanza
 * di {@link GameEngine} durante la navigazione tra le varie schermate
 * dell'applicazione (es. Dalla mappa alla schermata di combattimento).</p>
 *
 * <p>Implementa il pattern Singleton thread-safe per garantire un unico punto di accesso.</p>
 */
public final class GameSession {

    private static volatile GameSession instance;
    private GameEngine engine;

    /**
     * Costruttore privato per impedire l'istanziazione esterna.
     */
    private GameSession() {}

    /**
     * Restituisce l'unica istanza della sessione di gioco.
     *
     * @return istanza singleton di GameSession
     */
    public static GameSession getInstance() {
        if (instance == null) {
            synchronized (GameSession.class) {
                if (instance == null) {
                    instance = new GameSession();
                }
            }
        }
        return instance;
    }

    /**
     * Restituisce il motore di gioco attualmente caricato.
     *
     * @return GameEngine corrente
     */
    public GameEngine getEngine() {
        return engine;
    }

    /**
     * Imposta il motore di gioco corrente.
     *
     * @param engine partita da memorizzare nella sessione
     */
    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Rimuove la partita attualmente associata alla sessione.
     */
    public void clear() {
        this.engine = null;
    }
}