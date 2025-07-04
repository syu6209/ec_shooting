package Screen;

import java.awt.Image;

public class MyButton {
	int x,y,w,h,type;
	Image up,over,down;
	String label=null;
	public MyButton(){
		x=0;
		y=0;
		w=100;
		h=50;
	}
	public MyButton(int x,int y,int w,int h,int type){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.type = type;
	}
	public void setBounds(int x,int y,int w,int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public void setUpImage(Image img){
		up = img;
	}
	public void setOverImage(Image img){
		over = img;
	}
	public void setDownImage(Image img){
		down = img;
	}

	public Image getUpImage(){
		return(up);
	}
	public Image getOverImage(){
		return(over);
	}
	public Image getDownImage(){
		return(down);
	}
}
