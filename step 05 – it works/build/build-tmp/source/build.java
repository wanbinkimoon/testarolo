import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import peasy.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class build extends PApplet {

int stageW      = 700;
int stageH      = 700;
int bgC       = 0xff2F2F2F;
String dataPATH = "../../data/";

// ================================================================






// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;
PeasyCam cam;
int[] colors_1 = new int[5];

// ================================================================

int audioRange 	= 4;
int audioMax = 100;

float audioAmp = 2200.0f;
float audioIndex = 0.05f;
float audioIndexAmp = audioIndex;
float audioIndexStep = 0.025f;

float[] audioData = new float[audioRange];

// ================================================================

float x;
float y;
float z;
float _r;
int bounce;
int rects;

int alpha;
float dist;
float grid;
float area;
boolean selector;

int changes;
float target;

// ================================================================

public void settings(){ 
	size(stageW, stageH, P3D);
}

// ================================================================

public void setup() {
	background(bgC);

	audioSettings();
	camSettings();
	obectSettings();
}	

// ================================================================

public void audioSettings(){
	minim = new Minim(this);
  audio = minim.getLineIn(Minim.STEREO);

	audioFFT = new FFT(audio.bufferSize(), audio.sampleRate());
	audioFFT.linAverages(audioRange);

  audioFFT.window(FFT.NONE);
  // audioFFT.window(FFT.BARTLETT);
  // audioFFT.window(FFT.BARTLETTHANN);
  // audioFFT.window(FFT.BLACKMAN);
  // audioFFT.window(FFT.COSINE);
  // audioFFT.window(FFT.GAUSS);
  // audioFFT.window(FFT.HAMMING);
  // audioFFT.window(FFT.HANN);
  // audioFFT.window(FFT.LANCZOS);
  // audioFFT.window(FFT.TRIANGULAR);
}

public void camSettings(){
	cam = new PeasyCam(this, 1200);
	cam.rotateX(35);
	cam.rotateY(45);
}

public void obectSettings(){
	rects = 4;	
	colors_1[0] = 0xffed6b5a;
	colors_1[1] = 0xfff4f1bc;
	colors_1[2] = 0xff9bc1bb;
	colors_1[3] = 0xff5aa3a8;
	colors_1[4] = 0xffe5eade;
}

// ================================================================
public void draw() {
	background(bgC);
	audioDataUpdate();
	objectRender(audioRange, audioData);
}

// ================================================================

public void audioDataUpdate(){
	audioFFT.forward(audio.mix);
	updateAudio();
}

// ================================================================

	public void updateAudio(){
		for (int i = 0; i < audioRange; ++i) {
			float indexAvg = (audioFFT.getAvg(i) * audioAmp) * audioIndexAmp;
			float indexCon = constrain(indexAvg, 0, audioMax);
			audioData[i] = indexCon;
			audioIndexAmp += audioIndexStep;
		}

		audioIndexAmp = audioIndex;
	}

// ================================================================

public void stop() {
	audio.close();
	minim.stop();
	super.stop();
}

// ================================================================

public void objectRender(int range, float[] values){

	for (int i = 1; i < rects; ++i) {
		for (int j = 1; j < rects; ++j) {
			for (int k = 1; k < rects; ++k) {

				int indexCol = ((k + j + i) % 4);
				int index = ((k + j + i) % range);
				int fgC = colors_1[indexCol];

				selector = ((k + j + i) % 2 == 1);

				println("values: "+index);

				_r = 50.0f * values[index];
				bounce = 25;
				dist = 25.0f;
				grid = dist + _r;
				area = (_r + grid) * rects;
				
				x = map(i * _r, 0, rects * _r, -area, area);
				y = map(j * _r, 0, rects * _r, -area, area);
				z = map(k * _r, 0, rects * _r, -area, area);

				alpha = 255;
				target = selector ? z : y;

				pushMatrix();
					 translate(x, y, z);
					 stroke(fgC);
					 noFill();
					 sphere(_r);
				 popMatrix();
			}	
		}	
	}
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "build" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
