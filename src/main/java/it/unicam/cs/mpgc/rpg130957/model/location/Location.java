package it.unicam.cs.mpgc.rpg130957.model.location;

import it.unicam.cs.mpgc.rpg130957.model.entity.monster.Monster;
import java.util.Objects;

/**
 * Rappresenta una singola location della mappa.
 *
 * <p>Contiene il tipo di luogo, il mostro che lo presidia
 * e lo stato di avanzamento del giocatore in quel luogo.</p>
 */
public class Location {

    private final LocationType type;
    private final Monster monster; // null per la Capanna
    private boolean monsterDefeated;

    /**
     * Crea una nuova location.
     *
     * @param type    il tipo di luogo
     * @param monster il mostro presente, oppure null se è una zona sicura
     */
    public Location(LocationType type, Monster monster) {
        this.type = Objects.requireNonNull(type, "Il tipo di location non può essere nullo");
        this.monster = monster;
        this.monsterDefeated = false;
    }

    public LocationType getType() {
        return type;
    }

    public Monster getMonster() {
        return monster;
    }

    /**
     * Verifica se la location è una zona sicura (la capanna).
     *
     * @return true se non c'è nessun mostro
     */
    public boolean isSafeZone() {
        return monster == null;
    }

    public boolean isMonsterDefeated() {
        return monsterDefeated;
    }

    /**
     * Marca il mostro di questa location come sconfitto.
     * Utilizzato dal GameEngine dopo un combattimento vinto.
     */
    public void setMonsterDefeated() {
        this.monsterDefeated = true;
    }

    /**
     * Ripristina lo stato della location (utile per il caricamento di una partita salvata).
     *
     * @param defeated stato del mostro
     */
    public void restoreDefeatedState(boolean defeated) {
        this.monsterDefeated = defeated;
    }
}