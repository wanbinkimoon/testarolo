void objectRender(int range, float[] values){
	pool.autoAddToStage();

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
				color fgC = color(colors_1.getColor(), alpha);
				HDrawable3D d = (HDrawable3D) obj;

				d
					.size(_r)
					.anchorAt(H.CENTER)
					// .fill(fgC)
					.stroke(fgC)
					.noFill();

				new HRotate()
					.target(d)
					.speedY(random(-.5, .5));

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