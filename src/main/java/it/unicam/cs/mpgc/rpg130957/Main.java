import it.unicam.cs.mpgc.rpg130957.model.game.GameEngine;
import it.unicam.cs.mpgc.rpg130957.model.game.GameFactory;
import it.unicam.cs.mpgc.rpg130957.model.persistence.GamePersistence;
import it.unicam.cs.mpgc.rpg130957.model.persistence.SaveData;

    public static void main(String[] args) throws Exception {
        GameFactory factory = new GameFactory();
        GamePersistence persistence = new GamePersistence("savegame.json");

        // 1. Crea nuova partita
        GameEngine engine = factory.createNewGame();
        System.out.println("Nuova partita creata. Seed: " + engine.getRandom().getSeed());

        // 2. Simula un'azione (es. la strega usa mana)
        engine.getWitch().consumeMana(10);

        // 3. Salva
        persistence.save(engine.extractSaveData());
        System.out.println("Partita salvata!");

        // 4. Carica
        SaveData data = persistence.load();
        GameEngine loadedEngine = factory.loadGame(data);

        System.out.println("Partita caricata. Mana strega: " + loadedEngine.getWitch().getMana());
        // Dovrebbe stampare 40 (50 iniziali - 10 consumati)
    }
