package it.unicam.cs.mpgc.rpg130957.model.location;

/**
 * Definisce i cinque luoghi disponibili nella mappa del bosco.
 *
 * <p>Ogni luogo ha un nome visualizzabile e un percorso per l'icona grafica,
 * utili per l'interfaccia grafica.</p>
 */
public enum LocationType {

    WATERFALL("Cascate", "waterfall_icon.png"),
    SWAMP("Palude", "swamp_icon.png"),
    FOREST("Bosco", "forest_icon.png"),
    CEMETERY("Cimitero", "cemetery_icon.png"),
    HUT("Capanna", "hut_icon.png");

    private final String displayName;
    private final String iconPath;

    LocationType(String displayName, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIconPath() {
        return iconPath;
    }
}