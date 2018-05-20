int stageW      = 900;
int stageH      = 900;
color bgC       = #2F2F2F;
String dataPATH = "../../data/";


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

void draw() {
	audioDataUpdate();
	audioMidiValueUpdate();
	camUpdate();

	// color alphaBg = (int)map(knob[15], 0, 100, 0, 255);
	background(bgC);
	objectRender(audioRange, audioData);
	
	// midiMonitor();
}

// ================================================================

void stop() {
	audio.close();
	minim.stop();
	super.stop();
}


