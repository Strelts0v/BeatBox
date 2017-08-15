package com.vg.beatbox.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";

    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;

    private List<Sound> mSounds;

    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssets = context.getAssets();

        // This constructor is considered as deprecated
        // but we need him to provide capability
        // 1st param - count of sounds can be played simultaneously
        // 2nd param - type of audio stream
        // 3rd param - for sampling (in documentation said, it can be leaved as 0)
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);

            Log.i(TAG, "Found " + soundNames.length + " sounds");

            mSounds = new ArrayList<>(soundNames.length);
            for(String filename : soundNames){
                try {
                    String assetPath = SOUNDS_FOLDER + "/" + filename;
                    Sound sound = new Sound(assetPath);
                    load(sound);
                    mSounds.add(sound);
                } catch (IOException ioe) {
                    Log.e(TAG, "Could not load sound " + filename, ioe);
                }
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        // params:
        // 1st - id of sound
        // 2nd - volume from left
        // 3rd - volume from right
        // 4th - priority (ignore)
        // 5th - feature for cycle playing
        // 6th - speed of playing
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void release() {
        mSoundPool.release();
    }
}
