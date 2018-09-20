package controller;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * class for the managing of sounds
 * 
 * @author Rossolini Andrea
 */
public final class SoundController {
	private static final float VOLUME_REDUCE = -18f;
	private Clip clip;
	private Clip soundClip;
	private boolean musicMute;
	private boolean soundsMute;

	/**
	 * SoundController constructor.
	 * 
	 * @param filePath
	 *            path of the file to play
	 */
	public SoundController(final String filePath) {
		try {
			
			final AudioInputStream ais = AudioSystem.getAudioInputStream(SoundController.class.getResource(filePath));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		new Thread() {
			public void run() {
				final FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				volume.setValue(VOLUME_REDUCE);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}.start();
		
	}

	/**
	 * Playing music or sound using thread, if loop is false, a sound is played
	 * 
	 * @param loop
	 *            flag to determine if music should be played in loop
	 */
	public void playSound(final String soundPath) {
		try {
			final AudioInputStream ais = AudioSystem.getAudioInputStream(SoundController.class.getResource(soundPath));
			soundClip = AudioSystem.getClip();
			soundClip.open(ais);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		new Thread() {
			public void run() {
				if (!isSoundMute()) {
					final FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					volume.setValue(VOLUME_REDUCE);
					soundClip.start();
				}
			}
		}.start();
	}

	/**
	 * Set music mute if there's volume, set volume otherwise.
	 */
	public void changeMusicMute() {
		this.musicMute = !this.musicMute;
		if (this.musicMute) {
			clip.stop();
		} else {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Set sounds mute if there's sounds, set sounds otherwise.
	 */
	public void changeSoundsMute() {
		this.soundsMute = !this.soundsMute;
	}

	/**
	 * Returns if the music is activated or not.
	 * 
	 * @return true if it's muted, false otherwise
	 */
	public boolean isMusicMute() {
		return this.musicMute;
	}

	/**
	 * Returns if the sounds are activated or not.
	 * 
	 * @return true if they're muted, false otherwise
	 */
	public boolean isSoundMute() {
		return this.soundsMute;
	}

}
