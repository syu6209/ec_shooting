package Game;

import java.awt.Image;

public class Projectile {
	public int x=0;
	public int y=0;
	public double fx=0;
	public double fy=0;
	public double spd=0;
	public double spdx=0,spdy=0;
	public int sx=0;
	public int sy=0;
	public double radi=0;
	public int dir=0;
	public int penetration=0;//°üÅë
	public Image img;
	public int frame;
	public int framerate;
	public int maxframe;
	public void move() {
		switch (dir) {
		case 1:
			fx+=spd;
			break;
		case 2:
			fx-=spd;
			break;
		case 3:
			fy+=spd;
			break;
		case 4:
			fy-=spd;
			break;
		case 13:
			fx += spdx;
			fy += spdy;
			break;
		case 14:
			fx += spdx;
			fy -= spdy;
			break;
		case 23:
			fx-= spdx;
			fy+= spdy;
			break;
		case 24:
			fx-= spdx;
			fy-= spdy;
			break;
		default:
			dir = 1;
			break;
		}
		x = (int)fx;
		y = (int)fy;
	}
}