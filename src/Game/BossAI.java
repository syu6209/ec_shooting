package Game;

import Screen.Game_Frame;

public class BossAI extends Character {
	// public double x,y,spd,maxspd,dir;
	// public int sx,sy,radi;
	// public int frame,rate,maxFrame;
	// public int type;
	// public int motionNum;
	// public int state,statCounter;
	// public boolean standby;
	// int hw,hh;
	Game_Frame gf;
	public int ox;
	double spdx, spdy;
	int attackTimer = 0;
	int attackTime = 200;
	int Maxpattern = 3;
	int keepatNum = 0;
	boolean keepAttacking = false;
	int keepatTime = 0;
	int keepatTimer = 0;
	int pattern = 0;
	int power = 10;
	public int hp,maxhp;
	public int hitx,hity,hitsx,hitsy;
	boolean ablept[] = new boolean[3];
	public BossAI(int dx, int dy, int sx, int sy, int type, int rate,
			int maxFrame, Game_Frame gf) {
		super(dx, dy, sx, sy, type, rate, maxFrame);
		this.gf = gf;
		spdx = spd;
		spdy = spd;
	}
	public void BossSet(int hp,int pen,int attackDelay,boolean ablePattern[]){
		this.maxhp = hp;
		this.hp = maxhp;
		this.power = pen;
		attackTime = attackDelay;
		attackTimer = attackDelay;
		ablept = ablePattern;
	}
	public void BossHit(int hitx,int hity,int hitsx,int hitsy){
		this.hitsx = hitsx;
		this.hitsy = hitsy;
		this.hitx = hitx;
		this.hity = hity;
	}
	public void move() {
		x -= spdx;
	}

	@Override
	public void think() {
		if (keepAttacking) {
			if (keepatTimer < keepatTime) {
				keepatTimer++;
			} else {
				keepatNum++;
				attack();
				keepatTimer = 0;
			}
		} else if (x > ox) {
			move();
		} else {
			if (attackTimer < attackTime) {
				attackTimer++;
			} else {
				pattern = (int) (Math.random() * Maxpattern);
				keepatNum = 0;
				attack();
				attackTimer = 0;
			}
		}
	}

	public void attack() {
		double spdx;
		double spdy;
		double rt;
		double pi = 180/Math.PI;
		int shotdir=13;
//		if(gf.x<x){
//			shotdir = 10;
//		}else{
//			shotdir = 20;
//		}
//		if(gf.y<y){
//			shotdir+=3;
//		}else{
//			shotdir+=4;
//		}
		// (int x, int y, int dir, int spd, int pen)
		switch (pattern) {
		case 0:

			spdx = gf.x - x+gf.bx;
			spdy = gf.y - y+gf.by;
			rt = Math.atan2(spdy, spdx) * pi;
			rt = Math.floor(rt*100)/100;
			System.out.println("rt = "+rt);
			spdx = 10 * Math.cos(rt / pi);
			spdy = spdx * Math.tan(rt / pi);

			gf.zen_Bullet((int) x + sx / 2, (int) y + sy / 2, shotdir, spdx, spdy,
					power);
			if (keepatNum < 12) {
				keepatTime = 10;
				keepAttacking = true;
			} else {
				keepAttacking = false;
			}
			attackTimer = 0;
			break;
		case 1:
			for (int i = 0; i < 18; i++) {
				rt = 10 * i;
				spdx = 10 * Math.cos(rt / pi);
				spdy = spdx * Math.tan(rt / pi);
				gf.zen_Bullet((int) x + sx / 2, (int) y + sy / 2, shotdir, spdx,
						spdy, power);
			}
			attackTimer = 0;
			if (Math.random() * 10 < 6) {
				if (keepatNum < 6) {
					keepatTime = 30;
					keepAttacking = true;
				} else {
					keepAttacking = false;
				}
			} else {
				keepAttacking = false;
				attackTimer = 30;
			}
			break;
		case 2:
			spdx = gf.x - x+gf.bx;
			spdy = gf.y - y+gf.by;
			rt = Math.atan2(spdy, spdx) * pi;
			spdx = 10 * Math.cos(rt / pi);
			spdy = spdx * Math.tan(rt / pi);
			gf.zen_Bullet((int) x + sx / 2, (int) y + sy / 2, shotdir, spdx, spdy,
					power);
			rt+=12;
			spdx = 10 * Math.cos(rt / pi);
			spdy = spdx * Math.tan(rt / pi);
			gf.zen_Bullet((int) x + sx / 2, (int) y + sy / 2, shotdir, spdx, spdy,
					power);
			rt-=24;
			spdx = 10 * Math.cos(rt / pi);
			spdy = spdx * Math.tan(rt / pi);
			gf.zen_Bullet((int) x + sx / 2, (int) y + sy / 2, shotdir, spdx, spdy,
					power);

			if (keepatNum < 12) {
				keepatTime = 10;
				keepAttacking = true;
			} else {
				keepAttacking = false;
			}
			attackTimer = 0;
			break;
		default:
			pattern = 0;
			break;
		}
	}
}
