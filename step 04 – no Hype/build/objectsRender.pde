void objectRender(int range, float[] values){

	for (int i = 1; i < rects; ++i) {
		for (int j = 1; j < rects; ++j) {
			for (int k = 1; k < rects; ++k) {

				selector = ((k + j + i) % 2 == 1);

				_r = 50.0;
				bounce = 25;
				dist = 25.0;
				grid = dist + _r;
				area = (_r + grid) * rects;
				
				x = map(i * _r, 0, rects * _r, -area, area);
				y = map(j * _r, 0, rects * _r, -area, area);
				z = map(k * _r, 0, rects * _r, -area, area);

				alpha = 255;
				changes = selector ? H.Z : H.Y;
				target = selector ? z : y;

			}	
		}	
	}
}