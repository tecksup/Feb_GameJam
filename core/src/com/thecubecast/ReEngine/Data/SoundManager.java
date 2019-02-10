package com.thecubecast.ReEngine.Data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundManager {

    public float MasterVolume = 1.0f;
    public float MusicVolume = 0.4f;
    public float SoundVolume = 0.8f;

    //Define all sound objects
    Sound Click;

    //Define all your music objects
    private List<Music> HandledMusic = new ArrayList<>();
    private List<Sound> HandledSound = new ArrayList<>();

    Map<String, Long> SoundIds = new HashMap<String, Long>();

    public void init() { //Create the folders that hold everything neatly

        Click = Gdx.audio.newSound(Gdx.files.internal("Music/Sound/menu-clik.wav"));

    }

    public void update() {
        for (int i = 0; i < HandledMusic.size(); i++) {
            HandledMusic.get(i).setVolume(MusicVolume * MasterVolume);
        }
    }

    public void playS(String soundName) {
        Sound temp = Gdx.audio.newSound(Gdx.files.internal("Music/Sound/" + soundName));
        HandledSound.add(temp);
        SoundIds.put(soundName, temp.play(1));
    }

    public void play(String soundName) {
        if (soundName.equals("Click")) {
            long temp = Click.play(SoundVolume * MasterVolume);
            SoundIds.put(soundName, temp);
        }
    }

    public void pause(String soundName) {
        if (soundName.equals("Click")) {
            long temp = SoundIds.get(soundName);
            Click.pause(temp);
        }
    }

    public void stop(String soundName) {
        if (soundName.equals("Click")) {
            long temp = SoundIds.get(soundName);
            Click.stop(temp);
        }
    }

    public int playMusic(String Music, boolean looping) {
        Music temp = Gdx.audio.newMusic(Gdx.files.internal("Music/" + Music));
        HandledMusic.add(temp);
        temp.play();
        temp.setVolume(MusicVolume * MasterVolume);
        if (looping) {
            temp.setLooping(looping);
        }
        return HandledMusic.size() - 1;
    }

    public boolean isPlaying(int MusicID) {
        return HandledMusic.get(MusicID).isPlaying();
    }

    public void pauseMusic(int MusicID) {
        HandledMusic.get(MusicID).pause();
    }

    public void stopMusic(int MusicID) {
        HandledMusic.get(MusicID).stop();
        HandledMusic.get(MusicID).dispose();
        HandledMusic.remove(MusicID);
    }

}