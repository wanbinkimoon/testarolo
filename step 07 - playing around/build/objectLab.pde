void obectSettings(){
	rectsI = audioData[6];	
	rectsJ = audioData[6];	
	rectsK = audioData[6];	
	colors_1[0] = #ed6b5a;
	colors_1[1] = #f4f1bc;
	colors_1[2] = #9bc1bb;
	colors_1[3] = #5aa3a8;
	colors_1[4] = #e5eade;
}

// ================================================================

void objectRender(int range, float[] values){

	for (int i = 1; i < rects; ++i) {
		for (int j = 1; j < rects; ++j) {
			for (int k = 1; k < rects; ++k) {

				int indexCol = ((k + j + i) % 4);
				color fgC = colors_1[indexCol];

				selector = ((k + j + i) % 2 == 1);

				_r = (float)knob[8] + values[6];
				dist = map(audioData[4], 0, 100, 25, 50);
				grid = dist + _r;
				areaI = (_r + grid) * rectsI;
				areaJ = (_r + grid) * rectsJ;
				areaK = (_r + grid) * rectsK;
				
				x = map(i * _r, 0, rectsI * _r, -area, area);
				y = map(j * _r, 0, rectsJ * _r, -area, area);
				z = map(k * _r, 0, rectsK * _r, -area, area);

				alpha = 255;
				target = selector ? z : y;

				// int detail = (int)map(knob[9], 0, 100, 3, 60);
				int detail = (int)map(audioData[5], 0, 100, 3, 8);

				pushMatrix();
					 translate(x, y, z);
					 stroke(fgC);
					 noFill();
					 sphereDetail(detail);
					 sphere(_r);
				 popMatrix();
			}	
		}	
	}
}