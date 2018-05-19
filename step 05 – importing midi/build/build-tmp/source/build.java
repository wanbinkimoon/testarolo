import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import peasy.*; 
import themidibus.*; 

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
MidiBus myBus; 


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
int rects;

int alpha;
float dist;
float grid;
float area;
boolean selector;

float target;

// ================================================================

int knob1 = 0; int knob1b = 0;
int knob2 = 0; int knob2b = 0;
int knob3 = 0; int knob3b = 0;
int knob4 = 0; int knob4b = 0;
int knob5 = 0; int knob5b = 0;
int knob6 = 0; int knob6b = 0;
int knob7 = 0; int knob7b = 0;
int knob8 = 0; int knob8b = 0;

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
	midiSetup();
}	

// ================================================================

public void controllerChange(int channel, int number, int value) {  

	midiUpdate(channel, number, value);

  // Receive a controllerChange
  // println();
  // println("Controller Change:");
  // println("--------");
  // println("Channel:" + channel);
  // println("Number:" + number);
  // println("Value:" + value);
}

// ================================================================

public void noteOn(int channel, int pitch, int velocity) {
    println(channel, pitch, velocity);
}

// ================================================================

public void draw() {
	background(bgC);
	audioDataUpdate();
	objectRender(audioRange, audioData);
	
	// println("knob1 - " + knob1);
	// println("knob2 - " + knob2);
	// println("knob3 - " + knob3);
	// println("knob4 - " + knob4);
	// println("knob5 - " + knob5);
	// println("knob6 - " + knob6);
	// println("knob7 - " + knob7);
	// println("knob8 - " + knob8);
	// println("knob1b - " + knob1b);
	// println("knob2b - " + knob2b);
	// println("knob3b - " + knob3b);
	// println("knob4b - " + knob4b);
	// println("knob5b - " + knob5b);
	// println("knob6b - " + knob6b);
	// println("knob7b - " + knob7b);
	// println("knob8b - " + knob8b);

}

// ================================================================

public void stop() {
	audio.close();
	minim.stop();
	super.stop();
}


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
public void camSettings(){
	cam = new PeasyCam(this, 1200);
	cam.rotateX(35);
	cam.rotateY(45);
}
public void midiSetup(){
  MidiBus.list(); 
  myBus = new MidiBus(this, 0, 1);
}

public void midiUpdate(int channel, int number, int value){
	if(number == 21) knob1 = value;
	if(number == 22) knob2 = value;
	if(number == 23) knob3 = value;
	if(number == 24) knob4 = value;
	if(number == 25) knob5 = value;
	if(number == 26) knob6 = value;
	if(number == 27) knob7 = value;
	if(number == 28) knob8 = value;
	if(number == 41) knob1b = value;
	if(number == 42) knob2b = value;
	if(number == 43) knob3b = value;
	if(number == 44) knob4b = value;
	if(number == 45) knob5b = value;
	if(number == 46) knob6b = value;
	if(number == 47) knob7b = value;
	if(number == 48) knob8b = value;
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

public void objectRender(int range, float[] values){

	for (int i = 1; i < rects; ++i) {
		for (int j = 1; j < rects; ++j) {
			for (int k = 1; k < rects; ++k) {

				int indexCol = ((k + j + i) % 4);
				int index = ((k + j + i) % range);
				int fgC = colors_1[indexCol];

				selector = ((k + j + i) % 2 == 1);

				_r = (float)knob1 + values[index];
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
					 sphereDetail(knob2 / 4);
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
