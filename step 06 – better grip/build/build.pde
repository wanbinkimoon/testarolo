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

int knobNumb = 16;
int[] knob = new int[knobNumb];
String knobTable;

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
	
	midiMonitor();
}

// ================================================================

void stop() {
	audio.close();
	minim.stop();
	super.stop();
}


