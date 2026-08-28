package it.unicam.cs.mpgc.rpg130957;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Gestisce la musica di sottofondo del gioco.
 */
public class SoundManager {

    private static MediaPlayer mediaPlayer;

    /**
     * Avvia la musica di sottofondo in loop.
     *
     * @param audioPath percorso del file audio
     */
    public static void playBackgroundMusic(String audioPath) {
        try {
            String url = SoundManager.class.getResource(audioPath).toExternalForm();
            if (url == null) {
                System.err.println(" File audio non trovato: " + audioPath);
                System.err.println("Controlla che il file esista in src/main/resources" + audioPath);
                return;
            }

            Media media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop infinito
            mediaPlayer.play();
            System.out.println(" Musica avviata: " + audioPath);

        } catch (Exception e) {
            System.err.println(" Errore nel caricamento della musica: " + e.getMessage());
            e.printStackTrace();
        }
    }

}