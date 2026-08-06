package it.unicam.cs.mpgc.rpg130957.model.combat;

import it.unicam.cs.mpgc.rpg130957.model.entity.Entity;
import it.unicam.cs.mpgc.rpg130957.model.game.GameRandom;

import java.util.Objects;

/**
 * Motore di calcolo per il sistema di combattimento.
 *
 * <p>Questa classe è stateless (senza stato): non memorizza
 * informazioni sui combattimenti in corso. Si occupa esclusivamente di
 * calcolare i danni e le probabilità basandosi sul {@link GameRandom}.</p>
 */
public final class CombatSystem {

    private final GameRandom random;

    /**
     * Crea un nuovo sistema di combattimento.
     *
     * @param random generatore casuale deterministico per i calcoli
     * @throws NullPointerException se il random è null
     */
    public CombatSystem(GameRandom random) {
        this.random = Objects.requireNonNull(random, "GameRandom non può essere nullo");
    }

    /**
     * Calcola il danno inflitto da un'entità in un singolo attacco.
     *
     * @param attacker entità che attacca
     * @return valore di danno casuale compreso tra min e max danni dell'entità
     */
    public int calculateDamage(Entity attacker) {
        return random.nextInt(attacker.getMinDamage(), attacker.getMaxDamage() + 1);
    }

    /**
     * Calcola l'esito di un tentativo di fuga.
     *
     * <p>La probabilità di fuga è fissata al 50%.</p>
     *
     * @return true se la fuga ha successo, false altrimenti
     */
    public boolean tryFlee() {
        return random.nextInt(2) == 0; // 50% di probabilità
    }

    /**
     * Calcola un valore di danno casuale compreso tra un minimo e un massimo.
     *
     * @param min danno minimo (incluso)
     * @param max danno massimo (incluso)
     * @return valore di danno casuale
     */
    public int calculateDamageFromRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Il minimo non può essere più grande del massimo");
        }
        return random.nextInt(min, max + 1);
    }



    /**
     * Restituisce il generatore casuale utilizzato dal sistema di combattimento.
     *
     * <p>Utilizzato principalmente per estrarre il seed durante
     * le operazioni di salvataggio della partita.</p>
     *
     * @return istanza del GameRandom
     */
    public GameRandom getRandom() {
        return random;
    }


}