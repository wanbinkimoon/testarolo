import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import hype.*; 
import hype.extended.behavior.*; 
import hype.extended.colorist.*; 
import hype.extended.layout.*; 
import hype.interfaces.*; 
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
HColorPool colors_1;
HDrawablePool pool;
HRotate r;

// ================================================================

int audioRange 	= 8;
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
	objectRender(audioRange, audioData);
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
	H.init(this).background(bgC).use3D(true);
	colors_1 = new HColorPool(0xffed6b5a, 0xfff4f1bc, 0xff9bc1bb, 0xff5aa3a8, 0xffe5eade);
	pool = new HDrawablePool(audioRange);
}

// ================================================================
public void draw() {
	background(bgC);
	audioDataUpdate();

	H.drawStage();

	int i = 0;
	for(HDrawable d : pool){
		print(pool);
		
		d
			.size(_r + (audioData[3]));
		i++;
	}

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
	pool.autoAddToStage();

	for (int i = 1; i < rects; ++i) {
		for (int j = 1; j < rects; ++j) {
			for (int k = 1; k < rects; ++k) {
			
				selector = ((k + j + i) % 2 == 1);

				_r = 50.0f;
				bounce = 25;
				dist = 25.0f;
				grid = dist + _r;
				area = (_r + grid) * rects;
				
				x = map(i * _r, 0, rects * _r, -area, area);
				y = map(j * _r, 0, rects * _r, -area, area);
				z = map(k * _r, 0, rects * _r, -area, area);

				alpha = 255;
				changes = selector ? H.Z : H.Y;
				target = selector ? z : y;

				pool
					.add(new HSphere()
						.loc(x,y,z)
						.anchorAt(H.CENTER)
					);
			}	
		}	
	}

	println("________________");		

	pool.onCreate(
		new HCallback() {
			public void run(Object obj){
				int fgC = color(colors_1.getColor(), alpha);
				HDrawable3D d = (HDrawable3D) obj;

				d
					.size(_r)
					.anchorAt(H.CENTER)
					// .fill(fgC)
					.stroke(fgC)
					.noFill();

				new HRotate()
					.target(d)
					.speedY(random(-.5f, .5f));

				// new HOscillator()
				// 	.target(d)
				// 	.property(H.SIZE)
				// 	.relativeVal(_r)
				// 	.range( _r - bounce, _r + bounce)
				// 	.speed(random(0, .2))
				// 	.freq(10);
			}	
		}
	)
	.requestAll();

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
