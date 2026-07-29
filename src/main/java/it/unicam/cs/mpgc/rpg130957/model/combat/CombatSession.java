package it.unicam.cs.mpgc.rpg130957.model.combat;

import it.unicam.cs.mpgc.rpg130957.model.entity.Entity;
import it.unicam.cs.mpgc.rpg130957.model.entity.Witch;
import it.unicam.cs.mpgc.rpg130957.model.entity.monster.Monster;
import it.unicam.cs.mpgc.rpg130957.model.potion.Potion;
import it.unicam.cs.mpgc.rpg130957.model.potion.PotionType;
import it.unicam.cs.mpgc.rpg130957.model.spell.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//singola responsabilità: gestire lo stato di una sessione di combattimento in corso

/**
 * Rappresenta una sessione di combattimento in corso tra la strega e un mostro.
 */
public final class CombatSession {

    private final Witch witch;
    private final Monster monster;
    private final CombatSystem combatSystem;
    private final List<CombatEntry> history;
    private boolean ended;

    /**
     * Crea una nuova sessione di combattimento.
     *
     * @param witch        la strega protagonista
     * @param monster      il mostro da affrontare
     * @param combatSystem motore di calcolo per danni e probabilità
     */
    public CombatSession(Witch witch, Monster monster, CombatSystem combatSystem) {
        this.witch = Objects.requireNonNull(witch, "La strega non può essere nulla");
        this.monster = Objects.requireNonNull(monster, "Il mostro non può essere nullo");
        this.combatSystem = Objects.requireNonNull(combatSystem, "CombatSystem non può essere nullo");
        this.history = new ArrayList<>();
        this.ended = false;
    }

    public Monster getMonster() {
        return monster;
    }

    /**
     * Esegue un singolo turno di combattimento basato sulla scelta del giocatore.
     *
     * @param action azione scelta dalla strega
     * @param spell  incantesimo da lanciare (necessario solo se action è SPELL)
     * @param potion tipo di pozione da usare (necessario solo se action è POTION)
     * @return descrizione testuale dell'esito del turno
     * @throws IllegalStateException    se il combattimento è già terminato
     * @throws IllegalArgumentException se l'azione richiede parametri mancanti o non validi
     */
    public String executeTurn(CombatAction action, Spell spell, PotionType potion) {
        if (ended) {
            throw new IllegalStateException("Il combattimento è già terminato.");
        }

        StringBuilder turnReport = new StringBuilder();
        boolean playerSuccessfullyFled = false;

        // 1. Esecuzione azione del giocatore
        switch (action) {
            case ATTACK -> {
                int damage = combatSystem.calculateDamage(witch);
                monster.takeDamage(damage);
                history.add(new CombatEntry("Attacco fisico", damage));
                turnReport.append("La strega attacca fisicamente infliggendo ").append(damage).append(" danni.");
            }
            case SPELL -> {
                Objects.requireNonNull(spell, "L'incantesimo è richiesto per l'azione SPELL");
                if (!witch.knowsSpell(spell)) throw new IllegalArgumentException("La strega non conosce questo incantesimo");
                if (!witch.canCast(spell)) throw new IllegalArgumentException("Mana insufficiente");

                witch.consumeMana(spell.getManaCost());
                int damage = combatSystem.calculateDamageFromRange(spell.getMinDamage(), spell.getMaxDamage());
                monster.takeDamage(damage);
                history.add(new CombatEntry("Lancio di " + spell.getName(), damage));
                turnReport.append("La strega lancia ").append(spell.getName()).append(" infliggendo ").append(damage).append(" danni!");
            }
            case POTION -> {
                Objects.requireNonNull(potion, "Il tipo di pozione è richiesto per l'azione POTION");
                if (!witch.hasPotion(potion)) throw new IllegalArgumentException("Pozione non disponibile nell'inventario");

                Potion consumed = witch.consumePotion(potion);
                applyPotionEffect(consumed);
                history.add(new CombatEntry("Uso di " + consumed.getType().getDisplayName(), 0));
                turnReport.append("La strega usa una ").append(consumed.getType().getDisplayName()).append(".");
            }
            case FLEE -> {
                boolean success = combatSystem.tryFlee();
                if (success) {
                    playerSuccessfullyFled = true;
                    history.add(new CombatEntry("Fuga riuscita", 0));
                    turnReport.append("La strega riesce a fuggire dal combattimento!");
                } else {
                    history.add(new CombatEntry("Tentativo di fuga fallito", 0));
                    turnReport.append("La strega tenta di fuggire, ma fallisce!");
                }
            }
        }

        // 2. Controllo morte del mostro (dopo l'azione del giocatore)
        if (!monster.isAlive()) {
            ended = true;
            turnReport.append("\nIl ").append(monster.getDisplayName()).append(" è stato sconfitto!");
            return turnReport.toString();
        }

        // 3. Se la strega è fuggita con successo, il turno finisce qui e il mostro non attacca.
        if (playerSuccessfullyFled) {
            ended = true;
            return turnReport.toString();
        }

        // 4. Turno del mostro (eseguito solo se il mostro è vivo e la strega non è fuggita)
        int monsterDamage = combatSystem.calculateDamage(monster);
        witch.takeDamage(monsterDamage);
        history.add(new CombatEntry(monster.getDisplayName() + " attacca", monsterDamage));
        turnReport.append("\nIl ").append(monster.getDisplayName()).append(" contrattacca infliggendo ").append(monsterDamage).append(" danni!");

        // 5. Controllo morte della strega
        if (!witch.isAlive()) {
            ended = true;
            turnReport.append("\nLa strega è stata sconfitta...");
        }

        return turnReport.toString();
    }

    /**
     * Applica l'effetto di una pozione alla strega.
     * Metodo di supporto per mantenere executeTurn pulito.
     */
    private void applyPotionEffect(Potion potion) {
        switch (potion.getType()) {
            case HEALTH -> witch.heal(potion.getType().getHealthRestore());
            case MANA   -> witch.recoverMana(potion.getType().getManaRestore());
            case POWER  -> witch.increasePower(potion.getType().getPowerBonus());
        }
    }
    private String processMonsterAttack() {
        int damage = combatSystem.calculateDamage(monster);
        witch.takeDamage(damage);
        history.add(new CombatEntry(monster.getDisplayName() + " attacca", damage));
        return monster.getDisplayName() + " contrattacca infliggendo " + damage + " danni!";
    }

    /**
     * Finalizza la sessione e restituisce il DTO con il risultato completo.
     *
     * @return risultato del combattimento
     * @throws IllegalStateException se il combattimento non è ancora terminato
     */
    public CombatResult finalizeCombat() {
        if (!ended) {
            throw new IllegalStateException("Il combattimento non è ancora finito");
        }
        boolean won = witch.isAlive() && monster.getHealth() == 0;
        boolean fled = !won && witch.isAlive(); // Se è viva ma non ha vinto, è fuggita
        return new CombatResult(won, fled, history);
    }

    public boolean isEnded() {
        return ended;
    }

}