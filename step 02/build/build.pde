int stageW      = 700;
int stageH      = 700;
color bgC       = #2F2F2F;
String dataPATH = "../../data/";

// ================================================================

import ddf.minim.*;
import ddf.minim.analysis.*;

import hype.*;
import hype.extended.behavior.*;
import hype.extended.colorist.*;
import hype.extended.layout.*;
import hype.interfaces.*;

import peasy.*;

// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;
PeasyCam cam;
HColorPool colors_1;
HDrawablePool pool;
HRotate r;

// ================================================================

int audioRange 	= 8;
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
	objectRender(audioRange, audioData);
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
	H.init(this).background(bgC).use3D(true);
	colors_1 = new HColorPool(#ed6b5a, #f4f1bc, #9bc1bb, #5aa3a8, #e5eade);
	pool = new HDrawablePool(audioRange);
}

// ================================================================
void draw() {
	background(bgC);
	audioDataUpdate();
	
	H.drawStage();

	int i = 0;
	for(HDrawable d : pool){
		d
			.size(_r + (audioData[3] * 10));
		i++;
	}




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

