void camSettings(){
	cam = new PeasyCam(this, 1200);
	cam.rotateX(35);
	cam.rotateY(45);
}

void camUpdate(){
	cam.rotateX(knob[0]);
	cam.rotateY(knob[1]);
	cam.rotateZ(knob[2]);
}