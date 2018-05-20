void camSettings(){
	cam = new PeasyCam(this, 120000 * 10);
	cam.rotateX(35);
	cam.rotateY(45);
}

void camUpdate(){
	cam.rotateX((float)knob[0] / 1000);
	cam.rotateY((float)knob[1] / 1000);
	cam.rotateZ((float)knob[2] / 1000);
	cam.setDistance(map(knob[3], 0, 100, 100, 120000));
}