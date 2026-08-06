package it.unicam.cs.mpgc.rpg130957.model.game;

import java.util.Objects;
import java.util.Random;

/**
 * Wrapper del generatore casuale utilizzato dal gioco.
 *
 * <p>Incapsula un'istanza di {@link Random} inizializzata tramite seed,
 * garantendo la riproducibilità della generazione degli eventi casuali
 * (danni, pozioni, combattimenti).</p>
 *
 */
public final class GameRandom {
//  L'uso di un seed deterministico è fondamentale per:
// -Riprodurre esattamente una partita durante i test
// -Salvare e ricaricare lo stato del gioco in modo coerente

    private final Random random;
    private final long seed;

    /**
     * Crea un nuovo generatore casuale utilizzando il seed specificato.
     *
     * @param seed seed utilizzato per inizializzare il generatore
     */
    public GameRandom(long seed) {
        this.seed = seed;
        this.random = new Random(seed);
    }

    /**
     * Restituisce un numero casuale compreso tra 0 (incluso) e bound (escluso).
     *
     * @param bound limite superiore escluso
     * @return numero casuale generato
     * @throws IllegalArgumentException se bound è minore o uguale a zero
     */
    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Limite deve essere positivo");
        }
        return random.nextInt(bound);
        // metodo fail fast
    }

    /**
     * Restituisce un numero casuale compreso tra origin (incluso) e bound (escluso).
     *
     * @param origin limite inferiore incluso
     * @param bound  limite superiore escluso
     * @return numero casuale generato
     * @throws IllegalArgumentException se origin è maggiore o uguale a bound
     */
    public int nextInt(int origin, int bound) {
        if (origin >= bound) {
            throw new IllegalArgumentException(
                    "Origine (" + origin + ") deve essere minore del limite (" + bound + ")");
        }
        return random.nextInt(origin, bound);
    }

    /**
     * Restituisce il seed associato al generatore.
     *
     * @return seed utilizzato
     */
    public long getSeed() {
        return seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameRandom other)) return false;
        return seed == other.seed;
    }
//    Quando salvi la partita in JSON, salvi il seed (es. 12345). Quando ricarichi,
//    crei un nuovo GameRandom(12345). Non è lo stesso oggetto in memoria,
//    ma equals() dirà true perché il seed è uguale, garantendo che il gioco
//    continui con la stessa "fortuna/sfortuna".

    @Override
    public int hashCode() {
        return Objects.hash(seed);
    }

}
