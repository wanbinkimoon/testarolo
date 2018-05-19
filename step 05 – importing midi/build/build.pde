int stageW      = 700;
int stageH      = 700;
color bgC       = #2F2F2F;
String dataPATH = "../../data/";

// ================================================================

import ddf.minim.*;
import ddf.minim.analysis.*;

import peasy.*;

import themidibus.*; 

// ================================================================

Minim minim;
AudioInput audio;
FFT audioFFT;
PeasyCam cam;
MidiBus myBus; 


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

void settings(){ 
	size(stageW, stageH, P3D);
}

// ================================================================

void setup() {
	background(bgC);
	audioSettings();
	camSettings();
	obectSettings();
	midiSetup();
}	

// ================================================================

void controllerChange(int channel, int number, int value) {  

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

void noteOn(int channel, int pitch, int velocity) {
    println(channel, pitch, velocity);
}

// ================================================================

void draw() {
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

void stop() {
	audio.close();
	minim.stop();
	super.stop();
}


