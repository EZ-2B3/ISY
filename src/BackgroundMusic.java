import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class BackgroundMusic {

    private Clip[] clips;
    private int currentClipIndex; // the index of the currently playing clip

    public BackgroundMusic(String[] fileNames) { // fileNames is an array of file names
        clips = new Clip[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            clips[i] = loadClip(fileNames[i]);
        }
        currentClipIndex = 0;
    }

    private Clip loadClip(String fileName) { // fileName is the name of the file to load
        Clip clip = null;
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(fileName));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public void play() { // plays the current clip
        clips[currentClipIndex].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() { // stops the current clip
        clips[currentClipIndex].stop();
    }

    public void nextTrack() { // plays the next clip
        clips[currentClipIndex].stop();
        currentClipIndex = (currentClipIndex + 1) % clips.length;
        clips[currentClipIndex].loop(Clip.LOOP_CONTINUOUSLY);
    }
}
