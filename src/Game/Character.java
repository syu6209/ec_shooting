package Game;


public abstract class Character {
	public double x,y,spd,maxspd,dir;
	public int sx,sy,radi;
	public int frame,rate,maxFrame;
	public int type;
	public int motionNum;
	public int state,statCounter;
	public boolean standby;
	int hw,hh;

	public int ix(){
		return (int)x;
	}
	public int iy(){
		return (int)y;
	}
	public Character(int dx,int dy,int sx,int sy,int type,int rate,int maxFrame){
		this.x = dx;
		this.y = dy;
		this.type = type;
		this.sx = sx;
		this.sy = sy;
		this.rate = rate;
		this.maxFrame = maxFrame;
		frame = 0;
		state=0;
		standby=true;
		motionNum=0;
		dir = 0;
		spd = 7;
		statCounter=0;
	}
	public abstract void think();
	public abstract void move();
}
