package Game;

import Game.Sound;

public class Bomb {
	public int type;
	public int x;
	public int y;
	public int frame;
	public int sx, sy;
	public int maxFrame;
	public int rate;
	public int loop;
	Sound es;
	Thread th;

	public Bomb(int dx, int dy, int sx, int sy, int type, int rate,
			int maxFrame, int loop) {
		this.x = dx;
		this.y = dy;
		this.type = type;
		this.sx = sx;
		this.sy = sy;
		this.rate = rate;
		this.maxFrame = maxFrame;
		this.loop = loop;
		frame = 0;

		// 여기부터
//		try {
//			es = new Sound("rsc/katalk(1).wav");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		th = new Thread(es);
//		th.start();
		// 여기까지 2014/05/23
	}
}