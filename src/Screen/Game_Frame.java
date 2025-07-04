package Screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;

import Game.Bomb;
import Game.BossAI;
import Game.Character;
import Game.Game;
import Game.Missile;
import Game.NeutralBullet;
import Game.Projectile;
import Game.Sound;
import Game.myChar;

public class Game_Frame extends Root_Frame implements Runnable, MouseListener {

	int f_width = 1024;
	int f_height = 768;

	public int x, y; // 유도총알에서 참조 : public

	public int dir = 1; // 캐릭터의 좌표,방향 변수

	double spdx = 0, spdy = 0, maxSpd = 7;
	public int bx = 500, by;
	int radi;
	int xw, xh;
	boolean debugmode = true;
	int hitted = 0;
	boolean Zen_NeutralBullet = true;
	boolean on = true;
	boolean running = true;
	boolean KeyisDown[] = new boolean[256];
	boolean casting = false;
	boolean lives = true;
	boolean stopGaming = false;
	int noXkeyTimer = 0;
	int noYkeyTimer = 0;
	int NeutralBullet_timer;
	int next_CharFrame = 0;
	boolean hold_CharFrame = false;
	int magazine = 120;
	int maxMagazine = 120;

	int reload = 0;
	int reloadTime = 20;

	int bulletType = 0;
	int charState = 0;
	double charging = 0;
	int maxCharging = 0;
	boolean rainbow_on = false;
	int rainbow_num = 0;

	double dmgpercent = 1;
	int hp = 10;
	int maxhp = 10;
	int lv = 1;
	int earn = 0;
	// int[] charSkill_Lv = new int[5];
	int razerTimer = 0;
	int score = 0;
	int infoArr[] = new int[18];
	int skill_Cool[] = new int[4];
	int skill_CoolTime[] = new int[4];
	// 배경
	int thema;
	int leftmax;
	int rightmax;
	int topmax;
	int botmax;
	int maxby = 320;
	int maxbx = 2550;
	// 공격총알
	int nbullet_framerate;
	int nbullet_maxframe;
	int nbullet_sx;
	int nbullet_sy;
	int nbombsx;
	int nbombsy;
	int nbombfrate;
	int nbombmax;
	int zenBossScore;
	int bossZened = 0;
	int qaud1 = 400;
	int qaud3 = 800;
	int mapsx = 0;
	int mapsy = 0;

	int minimapMode = 1; // 0 전체표시 1 시야 내 총알표시 2 표시안함

	// int charSkill_Lv[] = new int[5];
	// int stats[] = new int[4];
	Thread th; // th레드ㅋ
	// KeyProcess pc;
	public Game pc;
	public ArrayList<Missile> Missile_List = new ArrayList<Missile>();
	public ArrayList<Projectile> NBullet_List = new ArrayList<Projectile>();
	public ArrayList<Bomb> Bomb_List = new ArrayList<Bomb>();
	public ArrayList<Character> Char_List = new ArrayList<Character>();

	public Bomb CharEffect_List[] = new Bomb[5];
	public boolean CharEffectOn[] = new boolean[5];
	public Bomb GameOverInfo;

	Toolkit tk = Toolkit.getDefaultToolkit();

	// 중립 총알 파괴 사운드[공통이름]
	String bulletBreakSound;
	String KeyArray[] = { "Space", "A", "S", "D" };
	int bbsNum;// 갯수

	Image bgimg;
	// 캐릭터 이미지
	Image charimg_r[] = new Image[5];
	Image charimg_l[] = new Image[5];
	Character charInfo[] = new Character[6];
	// 미사일 이미지
	Image missle_img;
	Image missle_img_l;
	Image rainbow_img;
	// 기본 중립 총알
	Image NeutralBullet1_img;
	Image effect_img[] = new Image[3];
	Image bomb_img[] = new Image[5];
	Image boss_img[] = new Image[1];
	Image skill_img[] = new Image[5];
	Image if_chargebar;
	Image if_chargebox;
	Image if_goldbox;
	Image if_lifebox;
	Image if_bulletbox;
	Image if_lifebar;
	Image if_bulletbar;
	Image if_lifeicon;
	Image if_bulleticon;

	Image if_gameover;
	Image if_gameover_ok;
	Image if_gamestop;
	Image if_gameover_no;
	Image if_minimapBack;
	Image skillCool[] = new Image[4];
	// 더블버퍼링용
	Image buff; // 버퍼
	public Graphics buffg; // 여기다 그림을 그림

	// 공용 레퍼런스 (temp)
	public Missile ms;
	public NeutralBullet nb;

	Font font = new Font("맑은 고딕", Font.BOLD, 20);

	Color themaColor;

	// TextField viewgold = new TextField(10);
	public Game_Frame(Game pc, int thema) {
		// 생성자
		this.pc = pc;
		this.thema = thema;
		infoArr = pc.getData();
		infoArr[4] = thema;
		System.out.print("Game_Frame : infoArr = ");
		for (int i = 0; i < infoArr.length; i++) {
			System.out.print(infoArr[i] + ",");
		}

		// 정보 전달 다 받음
		init();
	}

	public void init() {
		// 찾기 : 초기화
		Dimension screen = tk.getScreenSize();
		if (infoArr[6] == 0) {
			f_width = (int) screen.getWidth();
			f_height = (int) screen.getHeight();
		} else {
			f_width = (int) (1280 * pc.sss);
			f_height = (int) (880 * pc.sss);
		}
		setSize(f_width, f_height);
		setBackground(Color.BLACK);
		qaud1 = (int) ((double) f_width / 3.2);
		qaud3 = (int) ((double) f_width / 1.6);

		zenBossScore = 700;
		if_minimapBack = tk.getImage("res//interface//transp.png");
		if (thema == 0) {
			bulletBreakSound = "fire_br";
			bbsNum = 2;
			if (pc.infoArr[16] == 0) {
				pc.bgmPlay("res//bgm//bgm1.wav", 999);
				pc.bgmisPlaying = true;
			}
			bgimg = tk.getImage("res//background//background1.png");
			NeutralBullet1_img = tk.getImage("res//obj//Bullet1.png");
			nbullet_framerate = 3;
			nbullet_maxframe = 4;
			nbullet_sx = 65;
			nbullet_sy = 65;
			leftmax = 200;
			rightmax = 300;
			topmax = 50;
			botmax = 480;
			maxby = 320;
			maxbx = 2550;
			bomb_img[0] = tk.getImage("res//animate//bomb_1.png");

			nbombsx = 90;
			nbombsy = 92;
			nbombfrate = 2;
			nbombmax = 12;
			themaColor = Color.yellow;
		} else if (thema == 1) {
			bulletBreakSound = "ice_br";
			bbsNum = 4;
			if (pc.infoArr[16] == 0) {
				pc.bgmPlay("res//bgm//bgm2.wav", 999);
				pc.bgmisPlaying = true;
			}
			bgimg = tk.getImage("res//background//background2.jpg");
			NeutralBullet1_img = tk.getImage("res//obj//Bullet2.png");
			nbullet_framerate = 4;
			nbullet_maxframe = 4;
			nbullet_sx = 70;
			nbullet_sy = 70;
			leftmax = 200;
			rightmax = 300;
			topmax = 50;
			botmax = 510;
			maxby = 480;
			maxbx = 3230;
			bomb_img[0] = tk.getImage("res//animate//bomb_2.png");
			nbombsx = 78;
			nbombsy = 71;
			nbombfrate = 4;
			nbombmax = 10;
			themaColor = Color.white;
		} else if (thema == 2) {
			zenBossScore = 600;
			bossZened = 2;
			bulletBreakSound = "elect_br";
			bbsNum = 2;
			if (pc.infoArr[16] == 0) {
				pc.bgmPlay("res//bgm//bgm3.wav", 999);
				pc.bgmisPlaying = true;
			}
			bgimg = tk.getImage("res//background//background3.png");
			NeutralBullet1_img = tk.getImage("res//obj//Bullet3.png");
			nbullet_framerate = 4;
			nbullet_maxframe = 4;
			nbullet_sx = 55;
			nbullet_sy = 55;
			leftmax = 200;
			rightmax = 300;
			topmax = 50;
			botmax = 680;
			maxby = 0;
			maxbx = 3630;
			bomb_img[0] = tk.getImage("res//animate//bomb_3.png");
			nbombsx = 71;
			nbombsy = 70;
			nbombfrate = 3;
			nbombmax = 10;
			themaColor = Color.magenta;
			;
		}
		for (int i = 0; i < 5; i++) {
			charimg_r[i] = tk.getImage("res//animate//char" + i + "_r.png");
			charimg_l[i] = tk.getImage("res//animate//char" + i + "_l.png");
		}
		// 최대프레임은 최대 프레임-1
		// myChar(x, y, sx, sy, type, rate, maxfr);
		charInfo[0] = new myChar(0, 0, 110, 100, 0, 3, 12);
		charInfo[0].spd = 6 + (double) infoArr[11] / 5;
		charInfo[1] = new myChar(0, 0, 117, 138, 0, 3, 19);
		charInfo[1].spd = 1 + (double) infoArr[11] / 15;
		charInfo[2] = new myChar(0, 0, 111, 138, 0, 3, 15);
		charInfo[2].spd = 0;
		charInfo[3] = new myChar(0, 0, 110, 112, 0, 3, 15);
		charInfo[3].spd = infoArr[8] / 5 + 12 + (double) infoArr[11] / 5;

		charInfo[4] = new myChar(0, 0, 143, 135, 0, 3, 13);
		charInfo[4].spd = 0;// 게임오버 모션
		charInfo[5] = new myChar(0, 0, 111, 138, 0, 3, 15);
		charInfo[5].spd = 0;
		maxMagazine = 120 + infoArr[12] * 12;
		magazine = maxMagazine;
		maxhp = 10 + infoArr[13] * 3;
		hp = maxhp;
		reloadTime = 200 - infoArr[14] * 4 + infoArr[7] * 3;

		System.out.println("게임시작 : 특성 세팅 : " + charInfo[0].spd + "/"
				+ maxMagazine + "/" + maxhp + "/" + reloadTime);
		CharEffect_List[0] = new Bomb(xw - 5, -60, 128, 128, 0, 2, 20, 1);
		CharEffect_List[1] = new Bomb(-120, 10, 190, 115, 1, 1, 16, 1);
		CharEffect_List[2] = new Bomb(xw + 60, 10, 190, 115, 2, 1, 16, 1);

		skillCool[0] = tk.getImage("res//btn//sk_missle0001.png");
		skillCool[1] = tk.getImage("res//btn//sk_boost0001.png");
		skillCool[2] = tk.getImage("res//btn//sk_ball0001.png");
		skillCool[3] = tk.getImage("res//btn//sk_rainbow0001.png");

		skill_img[0] = tk.getImage("res//obj//skillball.png");
		skill_img[1] = tk.getImage("res//obj//skillball.png");
		missle_img = tk.getImage("res//obj//missile1_r.png");
		missle_img_l = tk.getImage("res//obj//missile1_l.png");
		rainbow_img = tk.getImage("res//obj//sk_rainbow.png");
		// bomb_img[0] = tk.getImage("res//animate//bomb_1.png");
		// 테마로 이동
		bomb_img[1] = tk.getImage("res//animate//razer_1.png");
		bomb_img[2] = tk.getImage("res//animate//eball_Shot_l.png");
		bomb_img[4] = tk.getImage("res//animate//eball_Shot_r.png");
		// 3 + dir 을하면 왼쪽일때 dir = -1 따라서 [1] 오른쪽일때 dir = 1 따라
		boss_img[0] = tk.getImage("res//animate//boss_1.png");
		effect_img[0] = tk.getImage("res//animate//charge.png");
		effect_img[1] = tk.getImage("res//animate//boost_blue_r.png");
		effect_img[2] = tk.getImage("res//animate//boost_blue_l.png");
		if_chargebox = tk.getImage("res//interface//charge01.png");
		if_chargebar = tk.getImage("res//interface//charge02.png");

		if_goldbox = tk.getImage("res//interface//gold.png");
		if_lifebox = tk.getImage("res//interface//lifebox.png");
		if_bulletbox = tk.getImage("res//interface//bulletbox.png");

		if_lifebar = tk.getImage("res//interface//lifebar.png");
		if_bulletbar = tk.getImage("res//interface//bulletbar.png");

		if_lifeicon = tk.getImage("res//interface//life.png");
		if_bulleticon = tk.getImage("res//interface//bullet.png");

		if_gameover = tk.getImage("res//interface//gameover.png");
		if_gameover_ok = tk.getImage("rsc//OK.png");

		if_gamestop = tk.getImage("res//interface//gameend.png");
		if_gameover_no = tk.getImage("rsc//NO.png");

		skill_CoolTime[0] = 0;
		skill_CoolTime[1] = 0;
		skill_CoolTime[2] = 0;
		skill_CoolTime[3] = 0;

		skill_Cool[0] = (int) (25 - (double) infoArr[7] * 2);
		skill_Cool[1] = 240;
		skill_Cool[2] = 340;
		skill_Cool[3] = 920;
		charState = 0;
		x = 100;
		y = 100;
		// f_width = 1024;
		// f_height = 768;
		hitted = 0;

		// 테스트
		score = 0;
		radi = 10;// charimg_r[charState].getWidth(null) / 2; 범위직접설정으로 좀더 안쪽에서
		xw = radi / 2;
		xh = charimg_r[charState].getHeight(null) / 2;
		addMouseListener(this);
		// new DrawThread(this).start();

		mapsx = 0;
		mapsy = 0;
		while (mapsx == 0) {
			mapsx = bgimg.getWidth(null) / 10;
			mapsy = bgimg.getHeight(null) / 10;
			System.out.println("set minimap : " + mapsx + "," + mapsy);
		}

		th = new Thread(this);
		th.start();
		nb = new NeutralBullet(100, random(f_height), 2, 1, nbullet_sx,
				nbullet_sy, nbullet_framerate, nbullet_maxframe);
		nb.spd = 100 + random(10);
		nb.radi = NeutralBullet1_img.getHeight(null);
		NBullet_List.add(nb);

	}

	public void start() {
		// 찾기 : 스레드 시작
		// kp = new KeyProcess(this);
		nb = new NeutralBullet(100, random(f_height), 2, 1, nbullet_sx,
				nbullet_sy, nbullet_framerate, nbullet_maxframe);
		nb.spd = 100 + random(10);
		nb.radi = NeutralBullet1_img.getHeight(null);
		NBullet_List.add(nb);
	}

	public void run() {
		// 찾기 : 런, 스레드
		try {
			while (on) {
				repaint(); // 이미지 새로 그리기
				Thread.sleep(16);
			}
		} catch (Exception e) {
		}
	}

	public boolean hitTestPoint(int dx, int dy, int ox, int oy, int or) {
		int ddx = (dx - ox) * (dx - ox);
		int ddy = (dy - oy) * (dy - oy);
		int ddr = or * or;
		if (ddr / 2 >= ddx + ddy) {
			return true;
		}
		return false;
	}

	public boolean hitTestCircle(int dx, int dy, double dr, int ox, int oy,
			double or) {
		if (debugmode) {
			buffg.drawRect(ox, oy, (int) or * 2, (int) or * 2);
			buffg.drawOval(dx, dy, (int) dr * 2, (int) dr * 2);
		}
		dx += dr;
		dy += dr;
		ox += or;
		oy += or;
		buffg.fillOval(ox, oy, 2, 2);
		double ddx = (dx - ox) * (dx - ox);
		double ddy = (dy - oy) * (dy - oy);
		double ddr = ((dr + or) * (dr + or));

		if (ddr >= ddx + ddy) {
			return true;
		}
		return false;
	}

	public int random(int n) {
		return (int) (Math.floor(Math.random() * n));
	}

	//
	// Image rotate(BufferedImage image, int value) {
	// AffineTransform transform = AffineTransform.getRotateInstance(
	// Math.toRadians(value), image.getWidth(null) / 2,
	// image.getHeight(null) / 2);
	// AffineTransformOp op = new AffineTransformOp(transform,
	// AffineTransformOp.TYPE_BILINEAR);
	//
	// BufferedImage filteredimage = new BufferedImage(image.getWidth(null),
	// image.getHeight(null), image.getType());
	// op.filter(image, filteredimage);
	// return filteredimage;
	// }

	@Override
	public void paint(Graphics g) {
		// f_width = getWidth();
		// f_height = getHeight();
		buff = createImage(f_width, f_height);
		// buff = createImage(getWidth(), getHeight());
		buffg = buff.getGraphics();
		update(g);
	}

	public void Entry_Bomb(int type, int dx, int dy, int sx, int sy, int rate,
			int maxFrame, int loop) {
		Bomb_List.add(new Bomb(dx, dy, sx, sy, type, rate, maxFrame, loop));
	}

	public void Draw_Ani_Bomb() {
		Bomb t;
		int i;
		// buffg.drawImage(NeutralBullet1_img, 0, 0, null);
		for (i = 0; i < Bomb_List.size(); i++) {
			t = Bomb_List.get(i);
			// buffg.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
			// observer)
			buffg.drawImage(bomb_img[t.type], t.x - bx, t.y - by, t.x - bx
					+ t.sx, t.y - by + t.sy,
					(t.sx * (int) Math.floor(t.frame / t.rate)), 0, t.sx
							* (int) Math.floor(t.frame / t.rate) + t.sx, t.sy,
					null);
			if (t.frame < t.maxFrame * t.rate) {
				t.frame++;
			} else {
				t.frame = 0;
				t.loop--;
				if (t.loop < 1)
					Bomb_List.remove(i);
			}
		}
	}

	public void Entry_Boss(int type, int dx, int dy, int sx, int sy, int rate,
			int maxFrame, int motionNum) {
		BossAI ba = new BossAI(dx, dy, sx, sy, type, rate, maxFrame, this);
		boolean arr[] = { true, true, true };
		int pen = bossZened + (int) (infoArr[0] / 7);
		if (pen > 12)
			pen = 12;
		ba.BossSet(36 + infoArr[0] * 24 + zenBossScore / 11, pen, 200, arr);
		ba.BossHit(180, 80, 80, 230);
		ba.ox = bgimg.getWidth(null) / 2;
		Char_List.add(ba);
	}

	public void proc_Boss(int i) {
		if (!lives)
			return;
		BossAI t;
		double temp;
		t = (BossAI) Char_List.get(i);
		t.think();
		if (t.hp < 0) {
			t.hp = 0;
			t.ox = -200;
		}
		if (t.x < -100) {
			Char_List.remove(i);
		}
		if (hitTestRectToPoint((int) (t.x + t.hitx - bx),
				(int) (t.y + t.hity - by), t.hitsx, t.hitsy, (int) (x),
				(int) (y))) {
			charHit(1, 1);
			x -= maxSpd * dir * 3;
			spdx = maxSpd * dir * (-5);
		}
		for (int j = 0; j < Missile_List.size(); j++) {
			ms = (Missile) (Missile_List.get(j));
			if (ms == null)
				continue;
			temp = t.x + t.hitx;
			if (Math.abs(temp - ms.x) > ms.radi + t.hitsx)
				continue;
			temp = t.y + t.hity;
			if (Math.abs(temp - ms.y) > ms.radi + t.hitsy)
				continue;
			if (hitTestRectToPoint((int) (t.x + t.hitx - bx), (int) (t.y
					+ t.hity - by), t.hitsx, t.hitsy,
					(int) (ms.x + ms.hitx - bx), (int) (ms.y + ms.sy / 2 - by))) {

				Entry_Bomb(1, ms.x + 55, ms.y, 56, 92, 1, 24, 1);
				t.hp -= ms.penetration;
				if (ms.penetration == 0)
					t.hp--;
				ms.penetration = 0;
				Missile_List.remove(i);
			}
		}
	}

	public boolean hitTestRectToPoint(int rx, int ry, int rsx, int rsy, int tx,
			int ty) {
		buffg.drawRect(rx, ry, rsx, rsy);
		if (rx < tx && tx < rx + rsx) {
			if (ry < ty && ty < ry + rsy) {
				return true;
			}
		}
		return false;
	}

	public void Draw_Boss() {
		BossAI t = null;
		for (int i = 0; i < Char_List.size(); i++) {
			t = (BossAI) Char_List.get(i);
			if (t == null)
				continue;
			buffg.drawString(
					t.hp + "/" + t.maxhp + "(" + t.x + "," + t.y + ")",
					(int) t.x - bx, (int) t.y - 50 - by);
			buffg.drawRoundRect((int) t.x + 100 - bx, (int) t.y - by,
					t.sx - 40, 15, 5, 5);
			// int percent = (int)(((double)t.hp/t.maxhp)*100);
			buffg.setColor(Color.red);
			if (minimapMode < 2) {
				buffg.fillRect(f_width - mapsx + ((int) t.x + t.sx) / 10,
						10 + ((int) t.y + t.sy) / 10, 5, 5);
			}
			buffg.fillRoundRect((int) t.x + 100 - bx, (int) t.y - by,
					(int) (((double) t.hp / t.maxhp) * (t.sx - 40)), 15, 5, 5);
			buffg.setColor(Color.black);
			buffg.drawImage(boss_img[t.type], t.ix() - bx, t.iy() - by, t.ix()
					- bx + t.sx * 2, t.iy() - by + t.sy * 2,
					(t.sx * (int) Math.floor(t.frame / t.rate)), 0, t.sx
							* (int) Math.floor(t.frame / t.rate) + t.sx, t.sy,
					null);
			if (t.frame < t.maxFrame * t.rate) {
				t.frame++;
			} else {
				t.frame = 0;
			}
			proc_Boss(i);
		}
	}

	public void Draw_BackGround() {
		// 찾기 : 배경 그리기
		// buffg.drawImage(bgimg, 0, 0, this);
		buffg.drawImage(bgimg, 0, 0, f_width, f_height, bx, by, (bx + f_width),
				(by + f_height), null);
		if (minimapMode < 2) {
			buffg.drawImage(if_minimapBack, f_width - mapsx, 10, mapsx, mapsy,
					null);
		}
	}

	int skillPan_y = 45;

	public void Draw_Interface() {
		// 미니맵

		if (minimapMode < 2) {
			buffg.setColor(Color.white);
			buffg.drawRect(f_width - mapsx, 10, mapsx, mapsy);
			// if(minimapMode==1){
			// buffg.drawRect(f_width - mapsx +(x+bx)/10-f_width/20,
			// 10+(y+by)/10-f_height/20, f_width/10,f_height/10);
			// }
			buffg.setColor(Color.green);
			buffg.fillRect(f_width - mapsx + (x + bx) / 10, 10 + (y + by) / 10,
					5, 5);
		}
		buffg.setColor(Color.white);
		for (int i = 0; i < skill_Cool.length; i++) {
			if (infoArr[7 + i] > 0) {
				buffg.drawString(KeyArray[i], 10 + 55 * i, skillPan_y);
				buffg.fillRect(
						10 + 55 * i,
						skillPan_y,
						(int) ((double) skill_CoolTime[i] / skill_Cool[i] * 50),
						5);
				if (skill_Cool[i] <= skill_CoolTime[i]) {
					buffg.drawImage(skillCool[i], 10 + 55 * i, skillPan_y, 50,
							50, null);
				}
			}
		}
		buffg.drawImage(if_goldbox, 5, 10, null);
		buffg.drawImage(if_lifebox, 165, 10, null);
		buffg.drawImage(if_bulletbox, 310, 10, null);
		Font temp = buffg.getFont();
		buffg.setFont(font);
		buffg.setColor(Color.white);
		String str = (int) (earn * 4) + "";
		String fin = "";
		for (int i = 0; i < 16 - str.length() * 1.5; i++) {
			fin = " " + fin;
		}
		fin = fin + earn;
		buffg.drawString(fin + "", 15, 28);
		buffg.setColor(Color.black);
		buffg.setFont(temp);
		buffg.drawImage(if_lifebar, 165, 10,
				(int) (((double) hp / maxhp) * 124), 23, null);

		if (reload > 0) {
			// buffg.drawRect(x, y-20, 100, 15);
			buffg.setColor(Color.BLACK);
			buffg.drawString("Reloading...", x, y - 22);
			buffg.setColor(Color.CYAN);
			buffg.drawRoundRect(x, y - 20, 100, 15, 5, 5);

			buffg.fillRoundRect(x, y - 20,
					(int) ((reload / (double) reloadTime) * 100), 15, 5, 5);
			buffg.drawImage(if_bulletbar, 310, 10,
					(int) (((double) reload / reloadTime) * 124), 23, null);
		} else {
			buffg.drawImage(if_bulletbar, 310, 10,
					(int) (((double) magazine / maxMagazine) * 124), 23, null);
		}

		buffg.drawImage(if_lifeicon, 150, 10, null);
		buffg.drawImage(if_bulleticon, 295, 0, null);
	}

	void Draw_stopGaming() {

		if (KeyisDown[32] || KeyisDown[13]) {
			th.stop();
			// sth.stop();
			buffg.clearRect(0, 0, f_width, f_height);
			on = false;
			// pc.bgmth.stop();
			pc.gameReadyFrame();
			return;
		}
		double hw = if_gameover.getWidth(null);
		double hh = if_gameover.getHeight(null);
		int panx = (int) ((getWidth() - hw) / 2);
		int pany = (int) ((getHeight() - hh) / 2);
		buffg.drawString("★", panx, pany);
		buffg.drawString("★", (int) (panx + hw), (int) (pany + hh));
		buffg.drawImage(if_gamestop, panx, pany, null);
		Font font = new Font("맑은 고딕", Font.BOLD, 30);
		Font temp = buffg.getFont();
		buffg.setFont(font);
		buffg.setColor(Color.blue);
		buffg.drawString(earn + "", panx + 220, pany + 130);
		buffg.setFont(temp);
		buffg.setColor(Color.black);
		buffg.drawImage(
				if_gameover_ok,
				(int) (panx + hw / 2 - if_gameover_ok.getWidth(null) / 2) - 100,
				pany + 220, null);
		buffg.drawImage(
				if_gameover_no,
				(int) (panx + hw / 2 - if_gameover_no.getWidth(null) / 2) + 100,
				pany + 220, null);

	}

	public void update(Graphics g) {
		if (running) {
			// 찾기 : 최종 그리기 , 업데이트
			buffg.clearRect(0, 0, f_width, f_height);
			// 그리는 순서
			if (on) {
				keyProcess();
				skillTimer();// 스킬 타이머
				CharBullet();
				Draw_BackGround();
				Draw_Boss();
				Draw_Missile();
				Draw_NBullet();
				Draw_Char();
				Draw_Ani_Bomb();
				Draw_Interface();
				Draw_Vars();
				Draw_GameOver();
			}
			if (stopGaming) {
				Draw_stopGaming();
			}
			double scaledW, scaledH;
			scaledW = getWidth();
			scaledH = getHeight();
			if (scaledH < scaledW * 0.7) {
				scaledW = (scaledH / 0.7);
			} else {
				scaledH = (scaledW * 0.7);
			}
			// g.drawImage(buff, 0, 0, (int) scaledW, (int) scaledH, null);
			// 화면 크기에 따라 확대 축소 (안함! 화면이 깨진다..)
			g.drawImage(buff, 0, 0, null);
		}
	}

	public void Draw_Vars() {
		// 찾기 : 변수 그리기
		if (debugmode) {
			buffg.setColor(Color.black);
			buffg.drawString("캐릭터 미사일 :" + Missile_List.size(),
					(f_width - 150), 50);
			buffg.drawString("중립 미사일 :" + (NBullet_List.size()) + "/"
					+ NBullet_List.size(), (f_width - 150), 65);
			buffg.drawString("hp:" + hp + "/" + maxhp, f_width - 150, 80);
			buffg.drawString("캐릭터 좌표:(" + x + "," + y + ")", f_width - 150, 95);
			buffg.drawString("배경 좌표:(" + bx + "," + by + ")", f_width - 150,
					110);
			buffg.drawString("반지름/방향:" + radi + "/" + dir, f_width - 150, 125);
			buffg.drawString("스피드:" + spdx + "/" + spdy, f_width - 150, 140);
			buffg.drawString("총알:" + magazine + "/" + maxMagazine + " "
					+ (int) (((double) magazine / maxMagazine) * 100) + "%",
					f_width - 150, 155);
			buffg.drawString("맵 :" + thema, f_width - 150, 170);
		}
	}

	public void charHit(int dmg, int type) {
		hp -= (int) (dmg * dmgpercent);
		pc.soundPlay("res//bgm//char_hit1.wav", 1);
		if (lives) {
			if (hp < 0) {
				reload = 0;
				casting = false;
				hold_CharFrame = true;
				charState = 4;
				magazine = 0;// 프레임 변수로 재활용
				hp = 0;
				lives = false;
				pc.soundPlay("res//bgm//char_hit2.wav", 1);
			}
		} else {
			hp = 0;
		}
	}

	public void Draw_NBullet() {
		// 찾기 : 중립 총알 그리기

		// Image timg = createImage(nbullet_sx, nbullet_sy);
		// Graphics tg = timg.getGraphics();
		// nb = (NeutralBullet) NBullet_List.get(0);
		// tg.drawImage(NeutralBullet1_img, 0, 0,nb.sx,nb.sy,
		// (nb.sx * (int) Math.floor(nb.frame / nb.framerate)), 0,
		// nb.sx * (int) Math.floor(nb.frame / nb.framerate) + nb.sx,
		// nb.sy, this);
		//
		// Color c = new Color(255,0,0);
		// int t = c.getRGB(); //투명색으로 변환할 색
		//
		// //RGB값을 0(투명색)으로 변환
		// for (int y = 0; y < nbullet_sy; y++) {
		// for (int x = 0; x < nbullet_sx; x++) {
		// if (((BufferedImage) timg).getRGB(x,y) == t) ((BufferedImage)
		// timg).setRGB(x,y, 0);
		// }
		// }

		// 아.. 별짓을 다해봐도 자바에서는 png에 자체 투명색 말고는 투명색을 못만드는거같다..

		for (int i = 0; i < NBullet_List.size(); i++) {
			nb = (NeutralBullet) NBullet_List.get(i);
			if (nb == null)
				continue;
			//
			// buffg.drawImage(timg, nb.x - bx, nb.y - by,
			// (int) nb.radi * 2, (int) nb.radi * 2, null);
			buffg.drawImage(NeutralBullet1_img, nb.x - bx, nb.y - by, nb.x - bx
					+ nb.sx, nb.y - by + nb.sy,
					(nb.sx * (int) Math.floor(nb.frame / nb.framerate)), 0,
					nb.sx * (int) Math.floor(nb.frame / nb.framerate) + nb.sx,
					nb.sy, this);
			if (debugmode) {
				Color c = buffg.getColor();
				buffg.setColor(nb.c);
				buffg.drawString("(" + (nb.x - bx) + "," + (nb.y - by) + "/"
						+ nb.frame + "/" + nb.radi + nb.patternTime + ")", nb.x
						- bx, nb.y - by);
				buffg.setColor(c);
			}
			buffg.setColor(themaColor);
			if (minimapMode == 0) {
				buffg.fillRect(f_width - mapsx + nb.x / 10, 10 + nb.y / 10, 3,
						3);
			} else if (minimapMode == 1)
				if (nb.x - bx > 0 && nb.x - bx < f_width) {
					buffg.fillRect(f_width - mapsx + nb.x / 10, 10 + nb.y / 10,
							3, 3);
				}
			if (nb.frame + 1 < nb.maxframe * nb.framerate) {
				nb.frame++;
			} else {
				nb.frame = 0;
			}
			// buffg.drawOval(nb.x, nb.y, NeutralBullet1_img.getWidth(null),
			// NeutralBullet1_img.getHeight(null));
			nb.move();
			nb.patternMove();
			if (nb.x > bgimg.getWidth(null) + 100
					|| nb.x < -100 - NeutralBullet1_img.getWidth(null) / 2) {
				// Test_List.add(new Bomb((int) (nb.x + nb.radi),
				// (int) (nb.y + nb.radi), 0, 0, 0, 0, 0, 0));
				NBullet_List.remove(i);
				continue;
			}
			if (nb.y > bgimg.getHeight(null) + 100) {
				nb.fy = -10;
			}
			/*
			 * ox += or; oy += or; dy-=dr/2; dx-=dr/2;
			 */
			// if (hitTestCircle(x + radi, y + radi, radi, nb.x - bx, nb.y -
			// by,nb.radi)) {

			// 찾기 : 충돌
			if (Math.abs((x + xw + 5) - (nb.x - bx)) < 100) {
				if (Math.abs((y + xh / 2) - (nb.y - by)) < 100) {
					if (hitTestCircle(x + 30, y + 30, radi, nb.x - bx, nb.y
							- by, nb.radi)) {
						hitted = (int) nb.spd * 2;
						charHit((int) nb.spd, 1);
						// Test_List.add(new Bomb((int) (nb.x + nb.radi),
						// (int) (nb.y + nb.radi), 0, 0, 0, 0, 0));
						Entry_Bomb(0, nb.x, nb.y, nbombsx, nbombsy, nbombfrate,
								nbombmax, 1);
						NBullet_List.remove(i);
						bulletBreak();
						continue;
					}
				}
			}
			double temp;
			for (int j = 0; j < Missile_List.size(); j++) {
				ms = (Missile) (Missile_List.get(j));
				if (ms == null)
					continue;
				temp = ms.x + ms.radi;
				if (Math.abs(temp - nb.x) > ms.radi + nb.radi + 20)
					continue;
				temp = ms.y + ms.radi;
				if (Math.abs(temp - nb.y) > ms.radi + nb.radi + 20)
					continue;
				if (hitTestCircle((int) (ms.x + ms.hitx - bx), (int) (ms.y
						+ ms.hity - by), ms.radi, nb.x - bx, nb.y - by, nb.radi)) {
					// hitted = true;
					// Test_List.add(new Bomb((int) (nb.x + nb.radi),
					// (int) (nb.y + nb.radi), 0, 0, 0, 0, 0));
					try {
						while (ms.penetration > -1 && nb.penetration > -1) {
							ms.penetration--;
							nb.penetration--;
						}
						if (ms.penetration < 1) {
							Entry_Bomb(1, ms.x + 55, ms.y, 56, 92, 1, 24, 1);
							Missile_List.remove(j);
						}
						if (nb.penetration < 1) {
							earn += Math.abs(nb.spd + nb.radi / 5);
							score += 2 + Math.abs(nb.spd + nb.radi / 5);
							Entry_Bomb(0, nb.x, nb.y, nbombsx, nbombsy,
									nbombfrate, nbombmax, 1);
							NBullet_List.remove(i);
							bulletBreak();
						}
					} catch (IndexOutOfBoundsException e) {
						System.out.println("으엥");
					}
				}
			}
		}
	}

	void bulletBreak() {
		pc.soundPlay("res//bgm//" + bulletBreakSound
				+ (int) ((Math.random() * bbsNum)) + ".wav", 1);
	}

	public void Draw_Missile() {
		// 찾기 : 미사일 그리기
		for (int i = 0; i < Missile_List.size(); i++) {
			ms = (Missile) (Missile_List.get(i));
			buffg.drawImage(ms.img, ms.x - bx, ms.y - by, ms.sx, ms.sy, null);
			if (debugmode) {
				buffg.drawString("(" + ms.x + "," + ms.y + "," + ms.hitx + ","
						+ ms.hity + ")", ms.x - bx, ms.y - by);
			}
			ms.move();
			if (ms.x + bx > f_width + bgimg.getWidth(null)
					+ ms.img.getWidth(null) + maxbx / 5
					|| ms.x + bx < -50 - ms.img.getWidth(null)) {
				Missile_List.remove(i);
			}
		}
	}

	public void Draw_Char() {
		// 캐릭터 그리기
		if (hitted > 0) {
			hitted--;
			bx += 3 - (int) (Math.random() * 7);
			// by+=3-(int)(Math.random()*7);
			x += 4 - (int) (Math.random() * 8);
			y += 4 - (int) (Math.random() * 8);
		}
		Character t = charInfo[charState];

		if (dir == 1) {
			// buffg.drawImage(charimg_r[charState], x, y, this);
			buffg.drawImage(charimg_r[charState], x, y, x + t.sx, y + t.sy,
					(t.sx * (int) Math.floor(t.frame / t.rate)), 0, t.sx
							* (int) Math.floor(t.frame / t.rate) + t.sx, t.sy,
					this);
		} else {
			// buffg.drawImage(charimg_l[charState], x, y, this);
			buffg.drawImage(charimg_l[charState], x, y, x + t.sx, y + t.sy,
					(t.sx * (int) Math.floor(t.frame / t.rate)), 0, t.sx
							* (int) Math.floor(t.frame / t.rate) + t.sx, t.sy,
					this);
		}
		// 캐릭터 프레임(또는 스킬) 진행중
		if (t.frame < t.maxFrame * t.rate) {
			t.frame++;
			if (charState == 1) {
				// 에너지볼 기모으기
				casting = true;
				charging = 0;
			} else if (charState == 2) {
				// 에너지볼 발사 후딜레이
				spdx = -dir;
				x -= dir;
			} else if (charState == 3) {
				// 부스터
				dmgpercent = 0.8 - infoArr[8] * 0.05;
				if (dir == 1) {
					CharEffectOn[1] = true;
					CharEffectOn[2] = false;
				} else {
					CharEffectOn[1] = false;
					CharEffectOn[2] = true;
				}
			} else if (charState == 4) {
				casting = false;
				CharEffectOn[1] = false;
				CharEffectOn[2] = false;
				if (y > 5) {
					y -= 5;
				}
			}
		} else {
			// 캐릭터 애니메이션 프레임 종료 시
			dmgpercent = 1;
			if (!hold_CharFrame) {
				if (charState == 3) {
					CharEffectOn[1] = false;
					CharEffectOn[2] = false;
				} else if (charState == 1) {
					// 지금 프레임 종료된 모션이 1 번(기모으기) 일 때
					if (next_CharFrame == 2) {
						// 다음이 2번이면 스킬3 발사
						Entry_Bomb(3 + dir, x + bx + dir * 30, y + by - 30,
								112, 220, 2, 3, 3);
						KeyisDown[16] = false;
						Missile temp = new Missile(skill_img[0], x + bx + dir
								* 70, y + by + 50 - (int) (60 + charging * 0.7)
								- infoArr[9] * 4,
								(int) (120 + infoArr[9] * 8 + charging * 1.4),
								(int) (120 + infoArr[9] * 8 + charging * 1.4),
								dir);
						temp.spd = -10;
						temp.penetration = 16 + (int) (infoArr[9] * 5)
								+ (int) (charging * 0.2);
						temp.radi = (int) (60 + infoArr[9] * 4 + charging * 0.7);
						temp.hitx = 15;
						temp.hity = 20;
						Missile_List.add(temp);
						magazine -= 5 + charging * 0.2;
						charging = 0;
						skill_CoolTime[2] = 0;
						KeyisDown[83] = false;
						if (CharEffectOn[0])
							CharEffectOn[0] = false;
					} else {
						// 다음이 2번이 아니면 5번임..5번밖에없음 스킬 4 발사
						Entry_Bomb(3 + dir, x + bx + dir * 30, y + by - 30,
								112, 220, 2, 3, 3);
						rainbow_on = true;
						rainbow_num = 6 + (int) ((infoArr[10] + charging / 5) * 3);
						KeyisDown[16] = false;
						charging = 0;
						if (CharEffectOn[0])
							CharEffectOn[0] = false;
						next_CharFrame = 2;
						skill_CoolTime[3] = 0;
						spdy = 0;
						spdx = 0;
						KeyisDown[68] = false;
					}
				} else if (charState == 2) {
					next_CharFrame = 0;
					KeyisDown[16] = false;
				}
				t.frame = 0;
				charState = next_CharFrame;
			} else {
				// 홀드프레임 프레임진행을 막고 대기
				if (charState == 1) {
					if (charging < 3) {
						CharEffectOn[0] = true;
					}
				} else if (charState == 4) {
					if (y < f_height + maxby) {
						y += 1 + (int) ((double) y * 0.02);
					}
				}
			}
		}
		if (debugmode) {
			buffg.drawString("r" + radi, x, y);
			buffg.drawLine(x, y, x + radi, y + radi);
		}
		if (casting) {
			buffg.drawImage(if_chargebox, x, y - 50, 120, 15, null);

			buffg.drawImage(if_chargebar, x, y - 50,
					(int) (((double) charging / maxCharging) * 120), 15, null);
		}
		// 최후의 섬광 스킬
		if (rainbow_on) {
			if (rainbow_num > 0) {
				if (rainbow_num % 4 == 0) {
					Missile temp = new Missile(rainbow_img, x + bx + dir * 70,
							y + by + 10, 360, 120, dir);
					temp.spd = 45;
					temp.penetration = 10 + infoArr[10];
					temp.radi = 60 + infoArr[10];
					temp.hitx = 15;
					temp.hity = 20;
					Missile_List.add(temp);
				}
				rainbow_num--;
			} else {
				rainbow_on = false;
				rainbow_num = 0;
			}
		}
		// 캐릭터위에 캐릭터를 따라다니는 이펙트
		Bomb tp;
		for (int i = 0; i < CharEffect_List.length; i++) {
			if (CharEffectOn[i]) {
				tp = CharEffect_List[i];
				buffg.drawImage(effect_img[tp.type], x + tp.x, y + tp.y, x
						+ tp.x + tp.sx, y + tp.y + tp.sy,
						(tp.sx * (int) Math.floor(tp.frame / tp.rate)), 0,
						tp.sx * (int) Math.floor(tp.frame / tp.rate) + tp.sx,
						tp.sy, null);
				if (tp.frame < tp.maxFrame * tp.rate) {
					tp.frame++;
				} else {
					tp.frame = 0;
					// Bomb_List.remove(i);
				}
			}
		}
		// buffg.drawOval(x + 25, y + 15, radi - 40, radi - 40);
	}

	public void skillTimer() {
		if (!lives)
			return;
		// 찾기 : 스킬타이머
		if (reload > 0) {
			if (reload < reloadTime) {
				reload++;
			} else {
				reload = 0;
				magazine = maxMagazine;
			}
		}
		for (int i = 0; i < skill_Cool.length; i++) {
			if (skill_CoolTime[i] < skill_Cool[i]) {
				skill_CoolTime[i]++;
			}
		}
		NeutralBullet_timer++;
	}

	public void CharBullet() {
		// 찾기 : 캐릭터 총알 생성
		if (!lives)
			return;
		if (KeyisDown[68]) {
			if (infoArr[10] > 0) {
				if (skill_CoolTime[3] >= skill_Cool[3]) {
					if (charState == 0 || charState == 1) {
						maxCharging = 50;
						if (charging < maxCharging) {
							charState = 1;
							hold_CharFrame = true;
							next_CharFrame = 5;
							charging += 1 + (double) infoArr[14] / 25;
							casting = true;
						} else {
							// charging = 0;
							skill_CoolTime[3] = 0;
							hold_CharFrame = false;
							casting = false;
						}
					}
				}
			}
		} else if (KeyisDown[65]) {
			if (infoArr[8] > 0) {
				if (skill_CoolTime[1] >= skill_Cool[1]) {
					if (charState == 0) {
						charState = 3;
						Entry_Bomb(3 - dir, x + bx - dir * 30, y + by - 30,
								112, 220, 2, 3, 2);
						hold_CharFrame = true;
						next_CharFrame = 0;
						skill_CoolTime[1] = 0;
						KeyisDown[65] = false;
					}
				} else {
					hold_CharFrame = false;
					casting = false;
				}
			}
		} else if (KeyisDown[83]) {
			if (infoArr[9] > 0 && magazine > 5) {
				if (skill_CoolTime[2] >= skill_Cool[2]) {
					if (charState == 0 || charState == 1) {
						maxCharging = 100;
						if (charging < maxCharging) {
							charState = 1;
							hold_CharFrame = true;
							next_CharFrame = 2;
							charging += 1 + (double) infoArr[14] / 25;
							casting = true;
						} else {
							// charging = 0;
							skill_CoolTime[2] = 0;
							hold_CharFrame = false;
							casting = false;
						}
					}
				}
			}
		} else {
			// charging = 0;
			hold_CharFrame = false;
			casting = false;
		}
		if (magazine > 0) {
			if (KeyisDown[32]) {
				if (reload == 0) {
					if (skill_CoolTime[0] >= skill_Cool[0]) {
						if (infoArr[7] > 1) {
							razerTimer++;
						}
						if (infoArr[7] > 8 || razerTimer == 9 - infoArr[7]) {
							Missile t = new Missile(null, (x + bx),
									(y + by + 30), 131, 29, dir);
							if (dir == 1) {
								t.img = missle_img;
								t.hitx = 70;
							} else {
								t.img = missle_img_l;
								t.hitx = 50;
							}
							t.spdy = -1.5;
							t.penetration = 0 + (int) (Math
									.round((double) infoArr[7] * 0.3));
							t.radi = 2;
							t.hity = 10;
							if (infoArr[7] != 9)
								magazine--;
							Missile_List.add(t);
						}
						if (infoArr[7] > 8 || razerTimer == 18 - infoArr[7] * 2) {
							razerTimer = 0;
							Missile t = new Missile(null, (x + bx),
									(y + by + 50), 131, 29, dir);
							if (dir == 1) {
								t.img = missle_img;
								t.hitx = 70;
							} else {
								t.img = missle_img_l;
								t.hitx = 50;
							}
							t.spdy = 1.5;
							t.penetration = 0 + (int) (infoArr[7] * 0.1);
							t.radi = 2;
							t.hity = 10;
							magazine--;
							Missile_List.add(t);

						}
						Missile t = new Missile(null, (x + bx), (y + by + 40),
								131, 29, dir);
						if (dir == 1) {
							t.img = missle_img;
							t.hitx = 70;
						} else {
							t.img = missle_img_l;
							t.hitx = 50;
						}
						t.penetration = 0 + (int) (infoArr[7] * 0.1);
						t.radi = 2;
						t.hity = 10;
						Missile_List.add(t);
						magazine--;
						skill_CoolTime[0] = 0;
					}
				}
			}
		} else {
			if (reload < 1) {
				reload = 1;
			}
		}
		if (NeutralBullet_timer > 30) {
			NeutralBullet_timer = 0;
		}
		if (Zen_NeutralBullet) {
			if (NBullet_List.size() < 512) {
				switch (NeutralBullet_timer) {
				case 10:
					if (infoArr[0] > 4
							&& (Math.random() * 200 < infoArr[0] * 15)) {
						nb = new NeutralBullet(-80, random(f_height), 1, 1,
								nbullet_sx, nbullet_sy, nbullet_framerate,
								nbullet_maxframe);
						nb.radi = NeutralBullet1_img.getHeight(null) / 2;
						nb.penetration = bossZened + 3 + (int) (infoArr[0] / 3);
					} else {
						nb = new NeutralBullet(-80, random(f_height), 1, 1,
								nbullet_sx, nbullet_sy, nbullet_framerate,
								nbullet_maxframe);
						nb.radi = NeutralBullet1_img.getHeight(null) / 2;
						nb.penetration = bossZened + (int) (infoArr[0] / 3) - 1;
					}

					if (infoArr[0] < 15) {
						nb.spd = bossZened + infoArr[0] / 5 + 1 + random(8);
					} else {
						nb.spd = bossZened + 4 + random(8);
						NeutralBullet_timer++;
					}

					nb.c = Color.magenta;
					nb.patternTime = 0;
					if (thema == 1) {
						if (Math.floor(Math.random() * 10) < 5) {
							nb.dir = 13;
							nb.spdx = (int) ((double) nb.spd * 1.2);
							nb.spdy = 1 + Math.random() * nb.spd / 2;
							nb.fy -= 300;
							nb.c = Color.green;
						}
					} else if (infoArr[0] > 10 || score > 1000) {
						if (Math.random() * 12 < 6) {
							nb.dir = 13;
							nb.pattern[0] = 13;
							nb.pattern[1] = 14;
							nb.pattern[2] = 13;
							nb.pattern[3] = 14;
							nb.patternTime = 20 + (int) (Math.random() * 30);
							nb.spdx = nb.spd;
							nb.spdy = 2;
							nb.c = Color.orange;
						}
					}

					// nb.spd = 0;
					NBullet_List.add(nb);
					break;
				case 20:
					nb = new NeutralBullet(bgimg.getWidth(null),
							random(f_height), 2, 1, nbullet_sx, nbullet_sy,
							nbullet_framerate, nbullet_maxframe);
					if (infoArr[0] < 10) {
						nb.spd = bossZened + 1 + infoArr[0] / 5 + random(7);
					} else {
						nb.spd = bossZened + 2 + random(8);
					}
					nb.c = Color.yellow;
					nb.patternTime = 0;
					if (thema == 1) {
						if (Math.floor(Math.random() * 10) < 5) {
							nb.dir = 14;
							nb.spdx = (int) ((double) nb.spd * 1.2);
							nb.spdy = 1 + Math.random() * nb.spd / 2;
							nb.fy -= 300;
							nb.c = Color.white;
						}
					}
					if (infoArr[0] > 10 || score > 1000) {
						if (Math.random() * 12 < 6) {
							nb.dir = 14;
							nb.pattern[0] = 23;
							nb.pattern[1] = 24;
							nb.pattern[2] = 23;
							nb.pattern[3] = 24;
							nb.patternTime = 20 + (int) (Math.random() * 30);
							nb.spdx = 1 + nb.spd;
							nb.spdy = 1 + nb.spd * 0.5;
							nb.c = Color.red;
						}
					}
					// nb.spd = 0;
					nb.radi = NeutralBullet1_img.getHeight(null) / 2;
					nb.penetration = bossZened + (int) (infoArr[0] / 7);
					NBullet_List.add(nb);
					NeutralBullet_timer = 0;
					break;
				}
			}
		}

		if (noXkeyTimer > 1) {
			spdx /= 1.1;
			if (spdx < 1 && spdx > (-1)) {
				spdx = 0;
			}
			noXkeyTimer = 0;
		}
		if (noYkeyTimer > 3) {
			spdy /= 5;
			if (spdy < 2 && spdy > (-2)) {
				spdy = 0;
			}
			noYkeyTimer = 0;
		}
		if (y < topmax) {
			y = topmax;
			spdy = 0;
		} else if (y > botmax) {
			y = botmax;
			spdy = 0;
		} else if (x < leftmax) {
			x = leftmax;
			spdx = 0;
		} else if (x > pc.getWidth() - rightmax) {
			x = pc.getWidth() - rightmax;
			spdx = 0;
		}
		if (y < 200 && by > 3) {
			y = 200;
			by += spdy;
			// spdy = 0;
		} else if (y > 400 && by < maxby) {
			y = 400;
			by += spdy;
			// spdy=0;
		}
		if (x > qaud1 && bx < maxbx - f_width && dir == 1) {
			if (x > qaud1) {
				x += (qaud1 - x) / 20;
				bx -= (qaud1 - x) / 20;
			}
			// bx += spdx;
			// spdx=0;
		} else if (x < qaud3 && bx > 10 && dir == -1) {
			if (x < qaud3) {
				x += (qaud3 - x) / 20;
				bx -= (qaud3 - x) / 20;
			}
			// bx += spdx;
			// spdx=0;
		}
		x += spdx;
		y += spdy;
	}

	public void zen_Bullet(int x, int y, int dir, double spdx, double spdy,
			int pen) {
		nb = new NeutralBullet(x, y, dir, 1, nbullet_sx, nbullet_sy,
				nbullet_framerate, nbullet_maxframe);
		nb.radi = NeutralBullet1_img.getHeight(null) / 2;
		nb.penetration = pen;
		nb.spd = 1 + pc.infoArr[0] / 2;
		nb.spdx = spdx;
		nb.spdy = spdy;
		nb.dir = dir;
		NBullet_List.add(nb);
	}

	public void Draw_GameOver() {
		if (lives)
			return;
		if (magazine < 100) {
			magazine++;
		} else {
			if (KeyisDown[32]) {
				if (on) {
					sendData();
				}
				on = false;
				th.stop();
				// pc.bgmth.stop();

				buffg.clearRect(0, 0, f_width, f_height);
				pc.gameReadyFrame();
				return;
			}
		}
		double hw = if_gameover.getWidth(null);
		double hh = if_gameover.getHeight(null);
		int panx = (int) ((getWidth() - hw) / 2);
		int pany = (int) ((getHeight() - hh) / 2);
		buffg.drawString("★", panx, pany);
		buffg.drawString("★", (int) (panx + hw), (int) (pany + hh));
		if (magazine < 30) {
			if (magazine < 20)
				magazine += (int) (magazine / 10);
			buffg.drawImage(if_gameover, panx + 100 - magazine * 3, pany + 70
					- magazine * 2, 300 + magazine * 6, 140 + magazine * 4,
					null);
			// (int)(panx+hw-200+magazine*6),(int)(pany+hh-200+magazine*6)
		} else {
			buffg.drawImage(if_gameover, panx, pany, null);
			Font font = new Font("맑은 고딕", Font.BOLD, 30);
			Font temp = buffg.getFont();
			buffg.setFont(font);
			buffg.setColor(Color.blue);
			buffg.drawString(score + "", panx + 220, pany + 130);
			buffg.drawString(earn * 4 + "", panx + 220, pany + 190);
			buffg.setFont(temp);
			buffg.setColor(Color.black);
			buffg.drawImage(if_gameover_ok,
					(int) (panx + hw / 2 - if_gameover_ok.getWidth(null) / 2),
					pany + 220, null);
		}
	}

	public void keyProcess() {
		if (!lives)
			return;

		if (score > zenBossScore) {
			pc.soundPlay("res//bgm//laugh.wav", 2);
			Entry_Boss(
					0,
					bgimg.getWidth(null) + Char_List.size() * 80,
					(int) (bgimg.getHeight(null) / 2 - 300 + Math.random() * 40),
					220, 181, 2, 34, 0);
			zenBossScore += zenBossScore + 4200;
			bossZened++;
		}
		maxSpd = charInfo[charState].spd;
		if (Math.abs(spdx) > maxSpd) {
			spdx = 0;
		}
		if (Math.abs(spdy) > maxSpd) {
			spdy = 0;
		}
		noXkeyTimer++;
		noYkeyTimer++;
		if (charState == 1 || charState == 2) {
			KeyisDown[16] = true;
		}
		if (KeyisDown[38] == true) {
			// spdy -= 5;
			noYkeyTimer = 0;
			// if (spdy > maxSpd * (-1))
			spdy--;
			if (spdy < maxSpd * (-1))
				spdy = maxSpd * (-1);
		}
		if (KeyisDown[40] == true) {
			// spdy += 5;
			noYkeyTimer = 0;
			// if (spdy < maxSpd)
			spdy++;
			if (spdy > maxSpd)
				spdy = maxSpd;
		}
		if (KeyisDown[37] == true) {
			// if (spdx > maxSpd * (-1))
			spdx--;
			if (spdx < maxSpd * (-1))
				spdx = maxSpd * (-1);
			noXkeyTimer = 0;
			if (!KeyisDown[16])
				dir = -1;
		}
		if (KeyisDown[39] == true) {
			// x += 5;
			noXkeyTimer = 0;
			// if (spdx < maxSpd)
			spdx++;
			if (spdx > maxSpd)
				spdx = maxSpd;
			if (!KeyisDown[16])
				dir = 1;
		}
		if (KeyisDown[27]) {
			// esc
			stopGaming = true;
		}
		if (KeyisDown[81] && KeyisDown[87] && KeyisDown[69]) {
			debugmode = !debugmode;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 250)
			KeyisDown[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 250)
			KeyisDown[e.getKeyCode()] = false;
	}

	char prevc;

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (prevc == c) {
			if (c == 'ㄴ') {
				KeyisDown[83] = false;
			} else if (c == 'ㅁ') {
				KeyisDown[65] = false;
			} else if (c == 'ㅇ') {
				KeyisDown[68] = false;
			}
			return;
		}
		if (c == 'ㄴ') {
			KeyisDown[83] = true;
			prevc = c;
		} else if (c == 'ㅁ') {
			KeyisDown[65] = true;
			prevc = c;
		} else if (c == 'ㅇ') {
			KeyisDown[68] = true;
			prevc = c;
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!lives) {
			Point p = e.getPoint();
			int left = (int) ((getWidth() - 500) / 2) + 160;
			int right = left + 160;
			int up = (int) ((getHeight() - 250) / 2) + 120;
			int down = up + 150;
			if (left < p.x && p.x < right) {
				if (up < p.y && p.y < down) {
					if (on) {
						sendData();
					}
					on = false;
					if (this != null)
						removeMouseListener(this);
					th.stop();
					// pc.bgmth.stop();
					buffg.clearRect(0, 0, f_width, f_height);
					pc.gameReadyFrame();
				}
			}
		}
		if (stopGaming) {
			Point p = e.getPoint();
			int left = (int) ((getWidth() - 500) / 2) + 60;
			int right = left + 182;

			int left2 = (int) ((getWidth() - 500) / 2) + 260;
			int right2 = left2 + 182;

			int up = (int) ((getHeight() - 250) / 2) + 120;
			int down = up + 150;
			if (up < p.y && p.y < down) {
				if (left < p.x && p.x < right) {
					on = false;
					removeMouseListener(this);
					th.stop();
					// sth.stop();
					buffg.clearRect(0, 0, f_width, f_height);
					pc.gameReadyFrame();
				} else if (left2 < p.x && p.x < right2) {
					stopGaming = false;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	public void sendData() {
		pc.infoArr[1] += (int) (earn * 4);
		pc.infoArr[5] += Math.floor(score / 5);
		pc.setData(infoArr);
		pc.SaveData();
	}
}