package it.unicam.cs.mpgc.rpg130957.model.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Gestisce il salvataggio e il caricamento della partita su file JSON.
 *
 * <p>Utilizza la libreria GSON per serializzare e deserializzare
 * l'oggetto {@link SaveData}.</p>
 */
public class GamePersistence {

    private final Gson gson;
    private final String filePath;

    /**
     * Crea un nuovo gestore di persistenza.
     *
     * @param filePath percorso del file di salvataggio
     */
    public GamePersistence(String filePath) {
        this.filePath = filePath;
        // setPrettyPrinting rende il JSON leggibile da noi
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Salva i dati della partita su file.
     *
     * @param data dati da salvare
     * @throws IOException se si verifica un errore di scrittura
     */
    public void save(SaveData data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        }
    }

    /**
     * Carica i dati della partita da file.
     *
     * @return i dati salvati
     * @throws IOException se il file non esiste o si verifica un errore di lettura
     */
    public SaveData load() throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, SaveData.class);
        }
    }

    /**
     * Verifica se esiste un file di salvataggio.
     *
     * @return true se il file esiste
     */
    public boolean saveExists() {
        java.io.File file = new java.io.File(filePath);
        return file.exists();
    }
}