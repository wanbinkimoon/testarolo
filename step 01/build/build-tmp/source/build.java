import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int bgC       = 0xff2F2F2F;
String dataPATH = "../../data/";

// ================================================================




// ================================================================

Minim minim;
AudioPlayer audio;
FFT audioFFT;

// ================================================================

int audioRange 	= 8;
int audioMax = 100;

float audioAmp = 2200.0f;
float audioIndex = 0.05f;
float audioIndexAmp = audioIndex;
float audioIndexStep = 0.025f;

float[] audioData = new float[audioRange];

int rectS 			= 20;

// ================================================================

int stageM			= 100;
int stageW      = 700;
int stageH      = 700;

// ================================================================

int xStart 			= stageM;
int yStart 			= stageM;
int xSpace			= rectS;

// ================================================================

public void settings(){ 
	size(stageW, stageH);
}

// ================================================================

public void setup() {
	background(bgC);

	minim = new Minim(this);
	audio = minim.loadFile( dataPATH + "Who_the_Cap_Fit.wav");
	audio.loop();
	// audio.play();

	audioFFT = new FFT(audio.bufferSize(), audio.sampleRate());
	audioFFT.linAverages(audioRange);

	// audioFFT.window(FFT.NONE);
	audioFFT.window(FFT.GAUSS);
}	

// ================================================================
public void draw() {
	background(bgC);

	audioFFT.forward(audio.mix);

	audioDataUpdate();
}

// ================================================================

public void audioDataUpdate(){
	for (int i = 0; i < audioRange; ++i) {

		float indexAvg = (audioFFT.getAvg(i) * audioAmp) * audioIndexAmp;
		float indexCon = constrain(indexAvg, 0, audioMax);

		audioData[i] = indexCon;
		audioIndexAmp += audioIndexStep;
		
	}

		audioIndexAmp = audioIndex;

		println(audioData);
}

// ================================================================

public void stop() {
	audio.close();
	minim.stop();
	super.stop();
}

// ================================================================

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
