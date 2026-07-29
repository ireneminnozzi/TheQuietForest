package it.unicam.cs.mpgc.rpg130957.model.location;

/**
 * Definisce i cinque luoghi disponibili nella mappa del bosco.
 *
 * <p>Ogni luogo ha un nome visualizzabile e un percorso per l'icona grafica,
 * utili per l'interfaccia grafica.</p>
 */
public enum LocationType {

    WATERFALL("Cascate"),
    SWAMP("Palude"),
    FOREST("Bosco"),
    CEMETERY("Cimitero"),
    HUT("Capanna");

    private final String displayName;

    LocationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}