package it.unicam.cs.mpgc.rpg130957.model.location;

import it.unicam.cs.mpgc.rpg130957.model.entity.monster.*;
import java.util.EnumMap;
import java.util.Map;

/**
 * Rappresenta la mappa del gioco.
 *
 * <p>Gestisce le cinque location fisse del gioco e fornisce metodi
 * per recuperarle in base al loro tipo.</p>
 */
public class ForestMap {

    private final Map<LocationType, Location> locations;

    /**
     * Crea la mappa del bosco inizializzando le 5 location con i rispettivi mostri.
     */
    public ForestMap() {

        this.locations = new EnumMap<>(LocationType.class);

        locations.put(LocationType.WATERFALL, new Location(LocationType.WATERFALL, new Goblin()));
        locations.put(LocationType.SWAMP, new Location(LocationType.SWAMP, new Troll()));
        locations.put(LocationType.FOREST, new Location(LocationType.FOREST, new Dragon()));
        locations.put(LocationType.CEMETERY, new Location(LocationType.CEMETERY, new Spirit()));
        locations.put(LocationType.HUT, new Location(LocationType.HUT, null));
    }

    /**
     * Restituisce la location corrispondente al tipo richiesto.
     *
     * @param type il tipo di luogo da cercare
     * @return la location associata
     */
    public Location getLocation(LocationType type) {
        return locations.get(type);
    }

    /**
     * Verifica se il giocatore ha sconfitto tutti e 4 i mostri del bosco.
     *
     * @return true se tutti i mostri (esclusa la Capanna) sono stati sconfitti
     */
    public boolean isGameWon() {
        for (Map.Entry<LocationType, Location> entry : locations.entrySet()) {
            // ignoriamo la capanna
            if (entry.getKey() == LocationType.HUT) {
                continue;
            }
            // se troviamo un mostro vivo, non abbiamo vinto
            if (!entry.getValue().isMonsterDefeated()) {
                return false;
            }
        }
        return true;
    }
}