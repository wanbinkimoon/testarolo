void midiSetup(){
  MidiBus.list(); 
  myBus = new MidiBus(this, 0, 1);
}

void midiUpdate(int channel, int number, int value){
	if(number == 21) knob1 = map(value, 0, 127, 0, 100);
	if(number == 22) knob2 = map(value, 0, 127, 0, 100);
	if(number == 23) knob3 = map(value, 0, 127, 0, 100);
	if(number == 24) knob4 = map(value, 0, 127, 0, 100);
	if(number == 25) knob5 = map(value, 0, 127, 0, 100);
	if(number == 26) knob6 = map(value, 0, 127, 0, 100);
	if(number == 27) knob7 = map(value, 0, 127, 0, 100);
	if(number == 28) knob8 = map(value, 0, 127, 0, 100);
	if(number == 41) knob1b = map(value, 0, 127, 0, 100);
	if(number == 42) knob2b = map(value, 0, 127, 0, 100);
	if(number == 43) knob3b = map(value, 0, 127, 0, 100);
	if(number == 44) knob4b = map(value, 0, 127, 0, 100);
	if(number == 45) knob5b = map(value, 0, 127, 0, 100);
	if(number == 46) knob6b = map(value, 0, 127, 0, 100);
	if(number == 47) knob7b = map(value, 0, 127, 0, 100);
	if(number == 48) knob8b = map(value, 0, 127, 0, 100);
}