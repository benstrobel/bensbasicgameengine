package bensbasicgameengine.Sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

public class SoundManager {

    //TODO Adapt SoundManager

    private AudioInputStream audioIn;
    private HashMap<Sound, Clip> clips;
    private HashSet<Soundentry> toplay;
    public static final int SOUNDAUA = 0;
    public static final int SOUNDSWORD = 1;
    public static final int SOUNDITEM = 2;
    public static final int SOUNDMUSIC = 3;
    public static final int SOUNDDIED = 4;

    public static final int SOUNDSETTINGMUSIC = 100;
    public static final int SOUNDSETTINGCOMBAT = 101;
    public static final int SOUNDSETTINGMISC = 102;

    private float musicvolume = 0.8f;     // SOUNDSETTINGMUSIC
    private float combatvolume = 1f;    // SOUNDSETTINGCOMBAT
    private float miscvolume = 0.3f;      // SOUNDSETTINGMISC

    private boolean stopmusic = false;

    public float getModuleVolume(int i)
    {
        switch (i)
        {
            case 1:{return musicvolume;}
            case 2:{return combatvolume;}
            case 3:{return miscvolume;}
            default:{return 0.0f;}
        }
    }

    public SoundManager()
    {
        createSounds();
    }

    public void tick()
    {
        if(!stopmusic){
            playSoundID(SOUNDMUSIC);
        }
        for(Soundentry s: toplay)
        {
            executeClip(s.getClip(),s.getVolume());
        }
        toplay.clear();
    }

    public void playSoundID(int i)
    {
        switch(i)
        {
            case SOUNDAUA:{playSound(Sound.AUA, 0.2f, getModuleVolume(SOUNDSETTINGMISC));break;}
            case SOUNDSWORD:{playSound(Sound.SWORD, 0.1f, getModuleVolume(SOUNDSETTINGCOMBAT));break;}
            case SOUNDITEM:{playSound(Sound.ITEM, 0.2f, getModuleVolume(SOUNDSETTINGMISC));break;}
            case SOUNDMUSIC:{playSound(Sound.MUSIC, 0.08f, getModuleVolume(SOUNDSETTINGMUSIC));break;}
            case SOUNDDIED:{playSound(Sound.DIED, 0.2f, getModuleVolume(SOUNDSETTINGMISC));break;}
        }
    }

    public void stopMusic(){
        stopmusic = true;
        clips.get(Sound.MUSIC).stop();
    }

    public void unlockMusic(){
        stopmusic = false;
    }

    private boolean playSound(Sound sound) {return playClip(clips.get(sound));}

    private boolean playSound(Sound sound, float volume, float modulevolume) {return playClip(clips.get(sound), volume, modulevolume); }

    private boolean isPlaying(Clip c)
    {
        return c.isRunning();
    }

    private void createSounds()
    {
        clips = new HashMap<Sound, Clip>();
        for(Sound s: Sound.values())
        {
            loadSound(s);
        }
        toplay = new HashSet<Soundentry>();
    }

    private boolean playClip(Clip c)
    {
        return playClip(c, 1.0f, 1.0f);
    }

    private boolean playClip(Clip c, float volume, float modulevolume)
    {
        toplay.add(new Soundentry(c, volume, modulevolume));
        return true;
    }

    private void loadSound(Sound s)
    {
        Clip clip = null;
        InputStream in = SoundManager.class.getResourceAsStream("/" + s.getSoundName() + ".wav");
        InputStream bufferedIn = new BufferedInputStream(in);
        try
        {
            audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        catch (IOException | LineUnavailableException e) {
            System.out.println(s.getSoundName());
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clips.put(s, clip);
    }

    private boolean executeClip(Clip c)
    {
        return executeClip(c,1.0f);
    }

    private boolean executeClip(Clip c, float volume)
    {
        if(c != null)
        {
            if(!isPlaying(c))
            {
                c.setFramePosition(0);
                setVolume(c,volume);
                c.start();
            }

            return true;
        }
        else
            return false;
    }

    private void setVolume(Clip c, float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    private class Soundentry{
        private Clip clip;
        private float volume;
        private float modulevolume;

        public Soundentry(Clip clip, float volume, float modulevolume)
        {
            this.clip = clip;
            this.volume = volume;
            this.modulevolume = modulevolume;
        }

        public Clip getClip()
        {
            return clip;
        }

        public float getVolume()
        {
            return volume;
        }

        public float getModulevolume()
        {
            return modulevolume;
        }

        public void setVolume(float volume)
        {
            this.volume = volume;
        }
    }

}

