package Game;

import java.awt.Color;

public class NeutralBullet extends Projectile {
	int type;
	public int pattern[] = { -1, -1, -1, -1, -1 };
	public int patternTime = 0;
	int patternTimer = 0;
	int patternNum = 0;
	public Color c;

	public void patternMove() {
		if (patternTime == 0)
			return;
		
		patternTimer++;
		if (patternTimer > patternTime) {
			patternNum++;
			if (pattern[patternNum] == -1) {
				patternNum = 0;
			}
			dir = pattern[patternNum];
			patternTimer = 0;
		}
	}

	public NeutralBullet(int x, int y, int dir, int type, int sx, int sy,
			int framerate, int maxframe) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.type = type;
		this.penetration = 0;
		this.framerate = framerate;
		this.maxframe = maxframe;
		this.sx = sx;
		this.sy = sy;
		frame = 0;
		fx = x;
		fy = y;
	}
}
