int stageW      = 700;
int stageH      = 700;
color bgC       = #2F2F2F;
String dataPATH = "../../data/";

// ================================================================

import ddf.minim.*;
import ddf.minim.analysis.*;

import peasy.*;

// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;
PeasyCam cam;
color[] colors_1 = new color[5];

// ================================================================

int audioRange 	= 4;
int audioMax = 100;

float audioAmp = 2200.0;
float audioIndex = 0.05;
float audioIndexAmp = audioIndex;
float audioIndexStep = 0.025;

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

void settings(){ 
	size(stageW, stageH, P3D);
}

// ================================================================

void setup() {
	background(bgC);

	audioSettings();
	camSettings();
	obectSettings();
}	

// ================================================================

void audioSettings(){
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

void camSettings(){
	cam = new PeasyCam(this, 1200);
	cam.rotateX(35);
	cam.rotateY(45);
}

void obectSettings(){
	rects = 4;	
	colors_1[0] = #ed6b5a;
	colors_1[1] = #f4f1bc;
	colors_1[2] = #9bc1bb;
	colors_1[3] = #5aa3a8;
	colors_1[4] = #e5eade;
}

// ================================================================
void draw() {
	background(bgC);
	audioDataUpdate();
	objectRender(audioRange, audioData);
}

// ================================================================

void audioDataUpdate(){
	audioFFT.forward(audio.mix);
	updateAudio();
}

// ================================================================

	void updateAudio(){
		for (int i = 0; i < audioRange; ++i) {
			float indexAvg = (audioFFT.getAvg(i) * audioAmp) * audioIndexAmp;
			float indexCon = constrain(indexAvg, 0, audioMax);
			audioData[i] = indexCon;
			audioIndexAmp += audioIndexStep;
		}

		audioIndexAmp = audioIndex;
	}

// ================================================================

void stop() {
	audio.close();
	minim.stop();
	super.stop();
}

// ================================================================

