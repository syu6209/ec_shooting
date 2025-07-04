package Game;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

//playSound 지우고 이걸 넣어주세요
public class Sound implements Runnable {
	int loop;
	int EXTERNAL_BUFFER_SIZE;
	File soundFile;
	String audioFile;
	private boolean call_Stop;
	private Clip clip;
	public Sound(String filename,int loop) throws Exception{
		this.loop = loop;
		call_Stop = false;
		audioFile = filename; // 연결할
	}
	public Sound(String filename) throws Exception {// 메소드
		loop = 1;
		call_Stop = false;
		audioFile = filename; // 연결할
	}

	public void stop() {
		loop = -1;
		call_Stop = true;
	}

	public void play() {

		// wav파일

		EXTERNAL_BUFFER_SIZE = 128000;
		soundFile = new File(audioFile);
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundFile);
			
			AudioFormat audioFormat = audioInputStream.getFormat();
			
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			
			SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
			
			line.open(audioFormat);
			
			line.start();

			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (!call_Stop && nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0) {
					line.write(abData, 0, nBytesRead);
				}
			}
			line.drain();
			line.close();
			audioInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(loop>0){
			play();
			loop--;
		}
	}
}
