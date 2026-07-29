package it.unicam.cs.mpgc.rpg130957.model.spell;

/**
 * Definisce gli incantesimi disponibili nel gioco tramite un'Enum.
 */
public enum SpellType implements Spell {
// Ho scelto di mantenere lo spelltype enum privilegiando kiss e dry perche gli incantesimi hanno comportamenti simili.
// Se dovessi aggiungere incantesimi con effetti diversi (curare, buff, debuff), userei classi separate.
    LANCIA_BLU("Lancia Blu", 10, 10, 15),
    FIAMMA_ARCANA("Fiamma Arcana", 25, 20, 30),
    FIORE_LUNARE("Fiore Lunare", 5, 5, 8);

    private final String name;
    private final int manaCost;
    private final int minDamage;
    private final int maxDamage;

    SpellType(String name, int manaCost, int minDamage, int maxDamage) {
        this.name = name;
        this.manaCost = manaCost;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public int getMinDamage() {
        return minDamage;
    }

    @Override
    public int getMaxDamage() {
        return maxDamage;
    }
}