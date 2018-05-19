void obectSettings(){
	rects = 4;	
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
				int index = ((k + j + i) % range);
				color fgC = colors_1[indexCol];

				selector = ((k + j + i) % 2 == 1);

				_r = (float)knob[9] + values[index];
				dist = 25.0;
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
					 sphereDetail(knob[10] / 4);
					 sphere(_r);
				 popMatrix();
			}	
		}	
	}
}