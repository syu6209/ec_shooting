package Game;

import java.awt.Image;

public class Missile extends Projectile {
	public double hitx;
	public double hity;
	public Missile(Image img,int x, int y, int sx,int sy,int dir) {
		this.img= img;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.sx = sx;
		this.sy = sy;
		spd=0;
		hitx=0;
		hity=0;
		fx= x;
		fy =y;
	}
	public Missile(Image img,int x, int y, int sx,int sy,double hitx,double hity,int dir) {
		this.img= img;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.sx = sx;
		this.sy = sy;
		this.hitx=hitx;
		this.hity=hity;
		fx= x;
		fy =y;
		spd=0;
	}
	public Missile(Image img,int x, int y, int sx,int sy,int spd,int dir) {
		this.img= img;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.sx = sx;
		this.sy = sy;
		this.spd=spd;
		hitx=0;
		hity=0;
		fx= x;
		fy =y;
	}
	public Missile(Image img,int x, int y, int sx,int sy,int spd,double hitx,double hity,int dir) {
		this.img= img;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.sx = sx;
		this.sy = sy;
		this.spd=spd;
		this.hitx=hitx;
		this.hity=hity;
		fx= x;
		fy =y;
	}
	@Override
	public void move() {
		if(spd<14)spd++;
		switch (dir) {
		case 1:
			fx+=spd;
			break;
		case -1:
			fx-=spd;
			break;
		case 3:
			fy+=spd;
			break;
		case 4:
			fy-=spd;
			break;
		default:
			fy=0;
			fx+=1000;
			break;
		}
		if(spdy!=0){
			fy+=spdy;
		}
		x = (int)fx;
		y = (int)fy;
	}
}