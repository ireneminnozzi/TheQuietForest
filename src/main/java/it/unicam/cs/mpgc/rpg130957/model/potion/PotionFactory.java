package it.unicam.cs.mpgc.rpg130957.model.potion;

import it.unicam.cs.mpgc.rpg130957.model.game.GameRandom;
import java.util.Objects;

/**
 * Factory responsabile della creazione delle pozioni.
 *
 * <p>Incapsula la logica di generazione casuale delle ricompense.</p>
 */
public final class PotionFactory {

    private final GameRandom random;

    /**
     * Crea una nuova PotionFactory.
     *
     * @param random generatore casuale deterministico
     * @throws NullPointerException se il random è nullo
     */
    public PotionFactory(GameRandom random) {
        this.random = Objects.requireNonNull(random, "GameRandom non può essere nullo");
    }

    /**
     * Genera una pozione casuale come ricompensa per la vittoria.
     *
     * <p>Distribuzione attuale:</p>
     * <ul>
     *   <li>50% probabilità: Pozione di Salute</li>
     *   <li>30% probabilità: Pozione di Mana</li>
     *   <li>20% probabilità: Pozione di Potenza</li>
     * </ul>
     *
     * @return nuova istanza di Potion
     */
    public Potion createRandomReward() {
        int chance = random.nextInt(100);
        if (chance < 50) {
            return new Potion(PotionType.HEALTH);
        } else if (chance < 80) {
            return new Potion(PotionType.MANA);
        } else {
            return new Potion(PotionType.POWER);
        }
    }
}