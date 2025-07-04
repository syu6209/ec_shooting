package Screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import Game.Game;
import Game.Registry;
import Game.Sound;

public class StageSelect_Frame extends Root_Frame implements Runnable,
		MouseMotionListener, MouseListener {
	boolean on = true;

	Toolkit tk = Toolkit.getDefaultToolkit();

	Thread th;
	Thread sth;
	MyButton btn;
	MyButton Selectbtn;
	ArrayList<MyButton> Button_List = new ArrayList<MyButton>();
	ArrayList<MyButton> StatButton_List = new ArrayList<MyButton>();
	ArrayList<MyButton> SkillButton_List = new ArrayList<MyButton>();
	Point p;
	Game g;
	Font font;
	Font bigfont;
	Font temp;
	Font bolds;
	boolean pressed = false;
	boolean noStart = true;
	boolean intro = false;
	boolean startable = false;
	// int infoArr[] = new int[16];

	int startCounter, introcount;
	int mapx, mtimer;
	int movingy;
	int movingx;
	int f_width;
	int f_height;
	int mainView = 0;
	int selected = 0;
	int mstat[] = new int[4];
	int mskill[] = new int[4];
	int needgold[] = new int[8];
	int plusneedgold[] = new int[8];
	int selectedGold = 0;
	int max = 100;
	int now = 0;
	int percent = 0;
	int exp = 0;
	int levelUped = 0;
	int thema = 0;
	int maxthema = 2;
	int widoww = 1280;
	int widowh = 805;
	public double sss = 0.8;// ScreenSizeScale

	String mentSkill[] = new String[8];
	String mentStat[] = new String[8];
	String ment = "";
	String ment2 = "";

	Dimension screen = tk.getScreenSize();

	Image bimg;
	int prevthema;
	int selectx = 0;
	Image gold = tk.getImage("res//icon//gold.png");
	Image buff;
	Image backgimg[] = new Image[3];

	Graphics buffg;

	String mapname[] = { "광활한 평야", "얼음 왕국", "어둠의 다크" };

	public StageSelect_Frame(Game g) {
		this.g = g;
		p = new Point();
		max = 15 + ((g.infoArr[0] - 1) * 25 + 5 * (g.infoArr[0] - 1));
		now = max - (int) (g.infoArr[2] - (g.infoArr[5]));
		percent = (int) Math.floor(((double) now / max) * 100);
		exp = 0;
		thema = g.infoArr[4];
		while (now >= max) {
			levelup();
			levelUped = 120;
		}

		mstat[0] = 10;
		mstat[1] = 25;
		mstat[2] = 50;
		mstat[3] = 25;

		mskill[0] = 9;
		mskill[1] = 10;
		mskill[2] = 20;
		mskill[3] = 12;

		needgold[0] = 1000;
		needgold[1] = 900;
		needgold[2] = 600;
		needgold[3] = 1100;

		needgold[4] = 700;
		needgold[5] = 1500;
		needgold[6] = 2000;
		needgold[7] = 5000;

		plusneedgold[0] = 300;
		plusneedgold[1] = 150;
		plusneedgold[2] = 120;
		plusneedgold[3] = 200;

		plusneedgold[4] = 900;
		plusneedgold[5] = 100;
		plusneedgold[6] = 220;
		plusneedgold[7] = 500;

		mentSkill[0] = "[발동 : 스페이스바]";
		mentSkill[1] = "[발동 : A키]";
		mentSkill[2] = "[발동 : S키]";
		mentSkill[3] = "[발동 : D키]";

		mentSkill[4] = "기본공격입니다. 레벨업 할 수록 공격력,연사속도,발사수가 증가합니다.";
		mentSkill[5] = "순간적으로 빠르게 이동합니다. 이동중 데미지를 "
				+ (int) ((0.8 - g.infoArr[8] * 0.05) * 100) + "%만 입습니다.";
		mentSkill[6] = "에너지볼을 날립니다. 키를 꾹 눌러 충전 하여 더 강한 볼을 날릴 수 있습니다.";
		mentSkill[7] = "캐릭터 전방으로 빛을 발사하여 데미지를 입힙니다.";

		mentStat[0] = "캐릭터의 전반적인 이동속도가 빨라집니다.";
		mentStat[1] = "캐릭터의 에너지 최대충전량이 증가합니다.";
		mentStat[2] = "캐릭터의 최대체력이 증가합니다.";
		mentStat[3] = "캐릭터의 모든 스킬충전 및 에너지 충전속도가 빨라집니다.";

		mentStat[4] = "";
		mentStat[5] = "에너지는 캐릭터가 스킬을 사용할 때 소모되는 비용입니다.";
		mentStat[6] = "";
		mentStat[7] = "";
		g.resetSize();
		setLayout(null);
		backgimg[0] = tk.getImage("res//background//background1_sm.png");
		backgimg[1] = tk.getImage("res//background//background2_sm.png");
		backgimg[2] = tk.getImage("res//background//background3_sm.png");
		bimg = tk.getImage("res//background//select_bimg.png");
		// ----------------------------------------------------
		// 메인 화면 버튼 등록
		// ----------------------------------------------------
		btn = new MyButton(780, 480, 185, 53, 0);
		// btn.setBounds(780, 480, 185, 53);
		btn.setUpImage(tk.getImage("res//btn//startBtn_Up.png"));
		btn.setOverImage(tk.getImage("res//btn//startBtn_Over.png"));
		btn.setDownImage(tk.getImage("res//btn//startBtn_Down.png"));
		Button_List.add(btn);
		// 게임시작버튼
		btn = new MyButton(1255, 3, 20, 16, 9);
		// btn.setBounds(1255, 3, 20, 16);
		btn.setUpImage(tk.getImage("res//btn//X_up.png"));
		btn.setOverImage(tk.getImage("res//btn//X_over.png"));
		btn.setDownImage(tk.getImage("res//btn//X_up.png"));
		Button_List.add(btn);
		// 오른쪽위 x 종료 버튼
		btn = new MyButton(22, 721, 245, 31, 9);
		// btn.setBounds(22, 721, 245, 31);
		btn.setUpImage(tk.getImage("res//btn//lolExit_up.png"));
		btn.setOverImage(tk.getImage("res//btn//lolExit_over.png"));
		btn.setDownImage(tk.getImage("res//btn//lolExit_up.png"));
		Button_List.add(btn);
		// 왼쪽아래 종료 버튼
		btn = new MyButton(558, 490, 162, 59, 1);
		// btn.setBounds(558, 490, 162, 59);
		btn.setUpImage(tk.getImage("res//btn//skillBtn_up.png"));
		btn.setOverImage(tk.getImage("res//btn//skillBtn_over.png"));
		btn.setDownImage(tk.getImage("res//btn//skillBtn_down.png"));
		Button_List.add(btn);
		// 주문 수련 버튼
		btn = new MyButton(509, 515, 28, 28, 2);
		btn.setUpImage(tk.getImage("res//btn//Stat_up.png"));
		btn.setOverImage(tk.getImage("res//btn//Stat_over.png"));
		btn.setDownImage(tk.getImage("res//btn//Stat_down.png"));
		Button_List.add(btn);
		// 특성 강화 버튼

		// 맵 왼쪽

		btn = new MyButton(1055, 760, 68, 34, 3);
		btn.setUpImage(tk.getImage("res//btn//left0001.png"));
		btn.setOverImage(tk.getImage("res//btn//left0002.png"));
		btn.setDownImage(tk.getImage("res//btn//left0003.png"));
		Button_List.add(btn);

		// 맵 오른쪽

		btn = new MyButton(1130, 760, 68, 34, 4);
		btn.setUpImage(tk.getImage("res//btn//right0001.png"));
		btn.setOverImage(tk.getImage("res//btn//right0002.png"));
		btn.setDownImage(tk.getImage("res//btn//right0003.png"));
		Button_List.add(btn);

		// 처음으로
		btn = new MyButton(1204, 760, 68, 34, 5);
		btn.setUpImage(tk.getImage("res//btn//down0001.png"));
		btn.setOverImage(tk.getImage("res//btn//down0002.png"));
		btn.setDownImage(tk.getImage("res//btn//down0003.png"));
		Button_List.add(btn);

		// ----------------------------------------------------
		// 캐릭터 특성 버튼 등록
		// ----------------------------------------------------
		btn = new MyButton(290, 175, 60, 60, 10);
		btn.setUpImage(tk.getImage("res//btn//ic_MoveSpd0001.png"));
		btn.setOverImage(tk.getImage("res//btn//ic_MoveSpd0002.png"));
		btn.setDownImage(tk.getImage("res//btn//ic_MoveSpd0003.png"));
		btn.label = "이동 속도";
		StatButton_List.add(btn);

		btn = new MyButton(290, 240, 60, 60, 11);
		btn.setUpImage(tk.getImage("res//btn//ic_MaxCharge0001.png"));
		btn.setOverImage(tk.getImage("res//btn//ic_MaxCharge0002.png"));
		btn.setDownImage(tk.getImage("res//btn//ic_MaxCharge0003.png"));
		btn.label = "최대 충전량";
		StatButton_List.add(btn);

		btn = new MyButton(290, 305, 60, 60, 12);
		btn.setUpImage(tk.getImage("res//btn//ic_MaxHp0001.png"));
		btn.setOverImage(tk.getImage("res//btn//ic_MaxHp0002.png"));
		btn.setDownImage(tk.getImage("res//btn//ic_MaxHp0003.png"));
		btn.label = "최대 체력";
		StatButton_List.add(btn);

		btn = new MyButton(290, 370, 60, 60, 13);
		btn.setUpImage(tk.getImage("res//btn//ic_ChargeSpd0001.png"));
		btn.setOverImage(tk.getImage("res//btn//ic_ChargeSpd0002.png"));
		btn.setDownImage(tk.getImage("res//btn//ic_ChargeSpd0003.png"));
		btn.label = "충전 속도";
		StatButton_List.add(btn);

		btn = new MyButton(690, 350, 207, 60, 15);
		btn.setUpImage(tk.getImage("res//btn//reinforce_up.png"));
		btn.setOverImage(tk.getImage("res//btn//reinforce_over.png"));
		btn.setDownImage(tk.getImage("res//btn//reinforce_down.png"));
		StatButton_List.add(btn);

		// 스킬 버튼 등록
		btn = new MyButton(290, 175, 60, 60, 20);
		btn.setUpImage(tk.getImage("res//btn//sk_missle0001.png"));
		btn.setOverImage(tk.getImage("res//btn//sk_missle0002.png"));
		btn.setDownImage(tk.getImage("res//btn//sk_missle0003.png"));
		btn.label = "기본 레이저";
		SkillButton_List.add(btn);

		btn = new MyButton(290, 240, 60, 60, 21);
		btn.setUpImage(tk.getImage("res//btn//sk_boost0001.png"));
		btn.setOverImage(tk.getImage("res//btn//sk_boost0002.png"));
		btn.setDownImage(tk.getImage("res//btn//sk_boost0003.png"));
		btn.label = "부스트";
		SkillButton_List.add(btn);

		btn = new MyButton(290, 305, 60, 60, 22);
		btn.setUpImage(tk.getImage("res//btn//sk_ball0001.png"));
		btn.setOverImage(tk.getImage("res//btn//sk_ball0002.png"));
		btn.setDownImage(tk.getImage("res//btn//sk_ball0003.png"));
		btn.label = "에너지 볼";
		SkillButton_List.add(btn);

		btn = new MyButton(290, 370, 60, 60, 23);
		btn.setUpImage(tk.getImage("res//btn//sk_rainbow0001.png"));
		btn.setOverImage(tk.getImage("res//btn//sk_rainbow0002.png"));
		btn.setDownImage(tk.getImage("res//btn//sk_rainbow0003.png"));
		btn.label = "최후의 섬광";
		SkillButton_List.add(btn);

		btn = new MyButton(690, 350, 207, 60, 25);
		btn.setUpImage(tk.getImage("res//btn//reinforce_up.png"));
		btn.setOverImage(tk.getImage("res//btn//reinforce_over.png"));
		btn.setDownImage(tk.getImage("res//btn//reinforce_down.png"));
		SkillButton_List.add(btn);

		addMouseMotionListener(this);
		addMouseListener(this);
		setSize(g.getWidth(), g.getHeight());
		setVisible(true);
		startCounter = 0;
		intro = true;
		introcount = 0;
		// infoArr = g.getData();

		System.out.println("롤화면 : 값전달 확인");
		for (int i = 0; i < g.infoArr.length; i++) {
			System.out.print(g.infoArr[i] + ",");
		}
		// exp = 0;
		// charSkill_Lv = g.getSkillData();
		th = new Thread(this);
		th.start();
	}

	// public void sendData() {
	// g.SaveData();
	// }

	void Draw_defualt() {
		buffg.setColor(Color.WHITE);
		buffg.drawString("  메인 화면", 290, 140);
	}

	void Draw_CharSkill() {
		MyButton t;
		Image timg;
		buffg.setColor(Color.WHITE);
		buffg.drawString("  스킬 수련", 290, 140);
		for (int i = 0; i < SkillButton_List.size(); i++) {
			t = SkillButton_List.get(i);
			if (t == null)
				continue;
			if (p == null)
				continue;
			if (selected == 0 && t.type == 25)
				continue;
			if (t.x * sss < p.x && p.x < (t.x + t.w) * sss && t.y * sss < p.y
					&& p.y < (t.y + t.h) * sss) {
				if (pressed) {
					if (t.type != 25) {
						if (selected != i + 1) {
							selected = i + 1;
							ment = mentSkill[i];
							ment2 = mentSkill[i + 4];
							g.soundPlay("res//bgm//btn_over.wav", 1);
							if (selectx < 5)
								selectx = 50;
						}
					}
					timg = t.getDownImage();
					MouseDownEvent(t.type);
				} else {
					timg = t.getOverImage();
					MouseOverEvent(t.type);
				}
			} else {
				timg = t.getUpImage();
			}
			if (movingx > 0) {
				movingx -= 1 + movingx / 10;
			} else {
				movingx = 0;
			}
			if (t.type == 25) {
				buffg.drawImage(timg, t.x + movingx, t.y - selectx, t.w, t.h,
						null);
			} else {
				buffg.drawImage(timg, t.x + movingx, t.y, t.w, t.h, null);
			}
			if (selected - 1 == i) {
				if (selectx > 0)
					selectx -= 1 + selectx / 2;
				Font tfont = buffg.getFont();
				buffg.setFont(bigfont);
				buffg.drawImage(t.getUpImage(), 620 - selectx, 180, 100, 100,
						null);
				buffg.drawImage(gold, 730, 230 + (int) (selectx / 2), null);
				buffg.drawString(""
						+ (needgold[3 + selected] + g.infoArr[i + 7]
								* plusneedgold[3 + selected]), 800,
						250 + (int) (selectx / 2));
				selectedGold = needgold[3 + selected] + g.infoArr[i + 7]
						* plusneedgold[3 + selected];
				buffg.drawString(t.label + "", 730 + selectx, 210);
				buffg.setFont(tfont);
			}
			if (i < 4) {
				if (g.infoArr[i + 7] == mskill[i]) {
					buffg.setColor(Color.green);
				} else {
					buffg.setColor(Color.white);
				}
				buffg.drawRoundRect(t.x + 80 + movingx, t.y + 30, 200, 15, 5, 5);
				buffg.fillRoundRect(t.x + 80 + movingx, t.y + 30,
						(int) (((double) g.infoArr[i + 7] / mskill[i]) * 200),
						15, 5, 5);

				buffg.setColor(Color.white);
				buffg.drawString(t.label + "(" + g.infoArr[i + 7] + "/"
						+ mskill[i] + ")", t.x + 80 + movingx, t.y + 20);
			}
			// 그냥 sx,sy는 충돌에만 쓰고 그릴땐 씹고 왼쪽위좌표에서 다그림
		}
	}

	void Draw_CharStat() {
		MyButton t;
		Image timg;
		Font tfont;
		buffg.setColor(Color.WHITE);
		buffg.drawString("캐릭터 특성", 290, 140);
		for (int i = 0; i < StatButton_List.size(); i++) {
			t = StatButton_List.get(i);
			if (t == null)
				continue;
			if (p == null)
				continue;
			if (selected == 0 && t.type == 15)
				continue;
			if (t.x * sss < p.x && p.x < (t.x + t.w) * sss && t.y * sss < p.y
					&& p.y < (t.y + t.h) * sss) {
				if (pressed) {
					if (t.type != 15) {
						if (selected != i + 1) {
							selected = i + 1;
							ment = mentStat[i];
							ment2 = mentStat[i + 4];
							g.soundPlay("res//bgm//btn_over.wav", 1);
							if (selectx == 0)
								selectx = 50;
						}
					}
					timg = t.getDownImage();

					MouseDownEvent(t.type);
				} else {
					MouseOverEvent(t.type);
					timg = t.getOverImage();
				}
			} else {
				timg = t.getUpImage();
			}

			if (movingx > 0) {
				movingx -= 1 + movingx / 10;
			} else {
				movingx = 0;
			}
			if (t.type == 15) {
				buffg.drawImage(timg, t.x + movingx, t.y - selectx, t.w, t.h,
						null);
			} else {
				buffg.drawImage(timg, t.x + movingx, t.y, t.w, t.h, null);
			}
			if (selected - 1 == i) {
				if (selectx > 0)
					selectx -= 1 + selectx / 2;
				tfont = buffg.getFont();
				buffg.setFont(bigfont);
				buffg.drawImage(t.getUpImage(), 620 - selectx, 180, 100, 100,
						null);
				buffg.drawImage(gold, 730, 230 + (int) (selectx / 2), null);
				buffg.drawString(""
						+ (needgold[selected - 1] + g.infoArr[i + 11]
								* plusneedgold[selected - 1]), 800,
						250 + (int) (selectx / 2));
				selectedGold = needgold[selected - 1] + g.infoArr[i + 11]
						* plusneedgold[selected - 1];
				buffg.drawString(t.label + " 강화", 730 + selectx, 210);
				buffg.setFont(tfont);
			}
			if (i < 4) {
				if (g.infoArr[i + 11] == mstat[i]) {
					buffg.setColor(Color.green);
				} else {
					buffg.setColor(Color.white);
				}
				buffg.drawRoundRect(t.x + 80 + movingx, t.y + 30, 200, 15, 5, 5);
				buffg.fillRoundRect(t.x + 80 + movingx, t.y + 30,
						(int) (((double) g.infoArr[i + 11] / mstat[i]) * 200),
						15, 5, 5);

				buffg.setColor(Color.white);
				buffg.drawString(t.label + "(" + g.infoArr[i + 11] + "/"
						+ mstat[i] + ")", t.x + 80 + movingx, t.y + 20);
			}
			// 그냥 sx,sy는 충돌에만 쓰고 그릴땐 씹고 왼쪽위좌표에서 다그림
		}
	}

	void levelup() {
		// 레벨업
		exp = 0;
		g.infoArr[0]++;
		g.infoArr[3] += 3;
		g.infoArr[2] += 5 + g.infoArr[0] * 25;
		g.infoArr[1] += 1000;
		max = 15 + ((g.infoArr[0] - 1) * 25 + 5 * (g.infoArr[0] - 1));
		now = max - (int) (g.infoArr[2] - (g.infoArr[5]));
		percent = (int) Math.floor(((double) now / max) * 100);
		g.soundPlay("res//bgm//lvup1.wav", 1);
	}

	void set_info() {
		if (levelUped > 0)
			return;
		if (exp < percent) {
			exp += 1 + (int) ((double) exp / 10);
			if (exp > percent)
				exp = percent;
		} else {
			exp = percent;
			if (!startable) {
				g.SaveData();
				startable = true;
			}
		}
	}

	public void paint(Graphics g) {
		f_width = 1280;
		f_height = 1024;
		buff = createImage(f_width, f_height);
		// buff = createImage(getWidth(), getHeight());
		buffg = buff.getGraphics();
		update(g);
	}

	public void Draw_backImg() {
		buffg.setColor(Color.white);
		buffg.drawImage(bimg, 0, 0, null);
		if (mtimer < 0) {
			mtimer++;
			mapx -= 1 + mapx / 3;
			buffg.drawImage(backgimg[thema], 1015 + mapx, 588, 240 - mapx, 160,
					null);
			buffg.drawImage(backgimg[prevthema], 1015, 588, mapx, 160, null);
		} else if (mtimer > 0) {
			mtimer--;
			mapx -= 1 + mapx / 3;
			buffg.drawImage(backgimg[thema], 1015, 588, 240 - mapx, 160, null);
			buffg.drawImage(backgimg[prevthema], 1015 + 240 - mapx, 588, mapx,
					160, null);
		} else {
			mtimer = 0;
			buffg.drawImage(backgimg[thema], 1015, 588, null);
		}
		// buffg.drawImage(backgimg[thema], 1015, 588, null);
		buffg.drawString(mapname[thema], 1015, 575);
	}

	public void update(Graphics g) {
		if (noStart) {
			set_info();
			buffg.clearRect(0, 0, f_width, f_height);
			Draw_backImg();
			Draw_Buttons();
			switch (mainView) {
			case 0:
				// 메인화면
				Draw_defualt();
				break;
			case 1:
				// 캐릭터 스킬(주문)
				Draw_CharSkill();
				break;
			case 2:
				// 캐릭터 특성
				Draw_CharStat();
				break;
			case 3:
				// 저장 데이터 관련
				break;
			}
		}
		// g.drawImage(buff, 0, 0, null);
		g.drawImage(buff, 0, 0, (int) (1280 * sss), (int) (1024 * sss), null);
	}

	private void MouseOverEvent(int i) {
		switch (i) {
		case 0:
			ment = "게임을 시작합니다.";
			ment2 = "";
			break;
		case 1:
			ment = "스킬을 구입하거나 강화 할 수 있습니다.";
			ment2 = "";
			break;
		case 2:
			ment = "캐릭터 능력을 향상 시킵니다.";
			ment2 = "";
			break;
		case 3:
			ment = mapname[thema] + " 맵이 선택되었습니다.";
			ment2 = "이전 맵을 선택합니다.";
			break;
		case 4:
			ment = mapname[thema] + " 맵이 선택되었습니다.";
			ment2 = "다음 맵을 선택합니다.";
			break;
		case 5:
			ment = "초기화면으로 돌아갑니다.";
			ment2 = "모든 정보는 자동으로 저장됩니다.";
			break;
		case 9:
			ment = "게임을 종료합니다.";
			ment2 = "모든 정보는 자동으로 저장됩니다.";
			break;
		case 15:
			break;
		case 25:
			break;
		}
	}

	private void MouseDownEvent(int i) {
		// 내 이벤트
		System.out.println("내 이벤트 : " + i + "/mainView : " + mainView);

		switch (i) {
		case 0:
			if (g.infoArr[15] > 1) {
				ment = "중복실행입니다. 모든 작업은 거절됩니다.";
				ment2 = "실행중이던 게임을 종료해 주세요.";
				return;
			}
			if (startable) {
				g.SaveData();
				removeMouseMotionListener(this);
				removeMouseListener(this);
				buffg.setFont(font);
				buffg.setColor(Color.white);
				buffg.drawString("잠시후 게임이 시작됩니다!", 355, 90);
				try {
					Sound es = new Sound("res//bgm//playbutton.wav");
					sth = new Thread(es);
					sth.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// on = false;
				startCounter = 1;
				mainView = 0;
				// g.nextFrame();
			}
			break;
		case 1:
			if (g.infoArr[15] > 1) {
				ment = "중복실행입니다. 모든 작업은 거절됩니다.";
				ment2 = "실행중이던 게임을 종료해 주세요.";
				return;
			}
			if (mainView != 1) {
				movingx = 120;
				if (g.infoArr[17] == 0) {
					try {
						Sound es = new Sound("res//bgm//openstore.wav");
						sth = new Thread(es);
						sth.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			selected = 0;
			mainView = 1;
			pressed = false;
			break;
		case 2:
			if (g.infoArr[15] > 1) {
				ment = "중복실행입니다. 모든 작업은 거절됩니다.";
				ment2 = "실행중이던 게임을 종료해 주세요.";
				return;
			}
			if (mainView != 2) {
				movingx = 120;
				if (g.infoArr[17] == 0) {
					try {
						Sound es = new Sound("res//bgm//openstore.wav");
						sth = new Thread(es);
						sth.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			selected = 0;
			mainView = 2;
			pressed = false;
			break;
		case 3:
			prevthema = thema;
			thema--;
			if (thema < 0) {
				thema = maxthema;
			}
			mapx = 240;
			mtimer = -10;
			g.soundPlay("res//bgm//btn_over.wav", 1);
			ment = mapname[thema] + " 맵이 선택되었습니다.";
			ment2 = "";
			pressed = false;
			break;
		case 4:
			prevthema = thema;
			thema++;
			if (thema > maxthema) {
				thema = 0;
			}
			mapx = 240;
			mtimer = 10;
			g.soundPlay("res//bgm//btn_over.wav", 1);
			ment = mapname[thema] + " 맵이 선택되었습니다.";
			ment2 = "";
			pressed = false;
			break;
		case 5:
			g.introFrame();
			break;
		case 9:
			exitWindow();
			break;
		case 15:
			System.out.println("g.infoArr.length = " + g.infoArr.length);
			for (int si = 0; si < g.infoArr.length; si++) {
				System.out.print(g.infoArr[si]);
			}

			if (g.infoArr[(selected + 10)] < mstat[selected - 1]) {
				if (g.infoArr[1] >= selectedGold) {
					// if(charSkill_Lv[3]>0)
					g.infoArr[selected + 10]++;
					g.infoArr[1] -= selectedGold;
					pressed = false;
					g.soundPlay("res//bgm//lvup0.wav", 1);
					g.SaveData();
					// charSkill_Lv[3]--;
				} else {
					g.soundPlay("res//bgm//gold.wav", 1);
					ment = "골드가 부족합니다.";
					ment2 = "게임을 시작 하여 골드를 더 버셔야 합니다.";
					pressed = false;
				}
			} else {
				g.soundPlay("res//bgm//btn_over.wav", 1);
				ment = "강화가 최대치입니다.";
				ment2 = "더이상 강화 할 수 없습니다.";
				pressed = false;
			}
			break;
		case 25:
			System.out.println("g.infoArr.length = " + g.infoArr.length);
			for (int si = 0; si < g.infoArr.length; si++) {
				System.out.print(g.infoArr[si]);
			}
			if (selected == 2) {
				mentSkill[5] = "순간적으로 빠르게 이동합니다. 이동중 데미지를 "
						+ (int) (((0.8 - (double) g.infoArr[8] * 0.05) * 100))
						+ "%만 입습니다.";
				ment2 = mentSkill[5];
			}
			if (g.infoArr[(selected + 6)] < mskill[selected - 1]) {
				if (g.infoArr[(selected + 6)] < g.infoArr[0]) {
					if (g.infoArr[1] >= selectedGold) {
						// if(charSkill_Lv[3]>0)
						g.soundPlay("res//bgm//lvup0.wav", 1);
						g.infoArr[selected + 6]++;
						g.infoArr[1] -= selectedGold;
						pressed = false;
						g.SaveData();
						// charSkill_Lv[3]--;
					} else {
						g.soundPlay("res//bgm//gold.wav", 1);
						ment = "골드가 부족합니다.";
						ment2 = "게임을 시작 하여 골드를 더 버셔야 합니다.";
						pressed = false;
					}
				} else {
					g.soundPlay("res//bgm//btn_over.wav", 1);
					ment = "캐릭터의 레벨이 부족합니다.";
					ment2 = "캐릭터 레벨보다 스킬레벨을 높힐 수 없습니다.";
					pressed = false;
				}
			} else {
				g.soundPlay("res//bgm//btn_over.wav", 1);
				ment = "스킬레벨이 최대치 입니다.";
				ment2 = "더이상 레벨업 할 수 없습니다.";
				pressed = false;
			}
			break;
		}
	}

	private void exitWindow() {
		removeMouseMotionListener(this);
		removeMouseListener(this);
		on = false;
		g.closeWindow();
	}

	private void Draw_Buttons() {
		MyButton t;
		Image timg;
		for (int i = 0; i < Button_List.size(); i++) {
			t = Button_List.get(i);
			if (t == null)
				continue;
			if (p == null)
				continue;
			if (t.x * sss < p.x && p.x < (t.x + t.w) * sss && t.y * sss < p.y
					&& p.y < (t.y + t.h) * sss) {
				if (pressed) {
					timg = t.getDownImage();
					MouseDownEvent(t.type);
				} else {
					timg = t.getOverImage();
					MouseOverEvent(t.type);
				}
			} else {
				timg = t.getUpImage();
			}
			buffg.drawImage(timg, t.x, t.y, null);
			// buffg.drawString("이벤트 : " + pressed, 20, 20);

			buffg.setFont(font);
			buffg.setColor(Color.white);
			if (levelUped > 1) {
				ment = "레벨업! 축하드립니다.";
				ment2 = "특성버튼 과 수련 버튼으로 캐릭터와 스킬을 강하하세요!";
			}
			if (g.infoArr[15] > 1) {
				ment = "중복실행입니다. 모든 작업은 거절됩니다.";
				ment2 = "실행중이던 게임을 종료해 주세요. 계속 안된다면 SHIFT 키로 초기화하세요.";
			}
			buffg.drawString(ment, 295, 630);
			if (!pressed) {
				buffg.drawString("시작하려면 준비 완료 버튼을 누르세요!", 355, 90);
			}
			buffg.setFont(temp);
			buffg.drawString(ment2, 295, 670);
			buffg.drawString("레벨 :  " + g.infoArr[0] + " (" + g.infoArr[5]
					+ "/" + g.infoArr[2] + ")", 25, 605);
			buffg.drawString("골드 :  " + g.infoArr[1], 25, 625);
			buffg.drawString("최대 체력 :  " + 10+g.infoArr[13] * 3, 25, 645);
			buffg.drawString(
					"충전 속도 :  "
							+ Math.floor(((double) g.infoArr[3] / 200) * 100)
							+ "%", 25, 665);
			buffg.drawString("1회 충전량 :  " + (int)(120 + g.infoArr[12] * 12), 25, 685);
			buffg.drawString("이동속도 :  " + (6 + (double) g.infoArr[11] / 5), 25, 705);
			buffg.drawString("상세 정보", 22, 575);

			Color c = new Color(0, 200, 0);
			buffg.setColor(c);
			if (exp > 100)
				exp = 100;
			if (levelUped < 1) {
				buffg.fillRoundRect(305, 517, exp * 2, 23, 5, 5);
			} else {
				if (levelUped % 8 < 4) {
					buffg.setColor(Color.ORANGE);
					buffg.fillRoundRect(305, 517, 200, 23, 5, 5);
				} else {
					buffg.setColor(Color.YELLOW);
					buffg.fillRoundRect(305, 517, 200, 23, 5, 5);
				}
				levelUped--;
			}
			buffg.setFont(bolds);
			buffg.setColor(Color.ORANGE);
			if (percent < 0)
				percent = 0;
			buffg.drawString("경험치 " + percent + "% (" + now + "/" + max + ")",
					310, 533);

			buffg.drawString(p.x + " , " + p.y, 10, 20);
		}
	}

	@Override
	public void run() {
		while (on) {
			try {
				if (intro) {
					if (g.infoArr[6] == 0) {
						widoww = 1280;
						widowh = 801;
					} else {
						widowh = 832;
						widoww = 1285;
					}
					if (introcount < 10) {
						introcount++;

						int px;
						int py;
						px = (int) ((widoww * sss - g.getWidth()) * 0.5);
						py = (int) ((widowh * sss - g.getHeight()) * 1.2);
						if (px < 2) {
							px = 2;
						}
						if (py < 2) {
							py = 2;
						}
						g.setSize((getWidth() + px), (getHeight() + py));
						if(g.infoArr[6]==1){
							g.shiftLocation(11,3);
						}
						// if (g.getWidth() < 1290) {
						// g.setSize(g.getWidth() + 5, g.getHeight());
						// }
						// if (g.getHeight() < 805) {
						// g.setSize(g.getWidth(), g.getHeight() + 5);
						// }
						g.midLocation();
					} else {
						intro = false;
						g.setSize((int) (widoww * sss), (int) (widowh * sss));
						// setSize((int) (1280 * sss), (int) (800 * sss));
						if(g.infoArr[6]==0){g.midLocation();}
						System.out.println("화면크기 설정 완료" + sss);
						if (sss < 1) {
							font = new Font("맑은 고딕", Font.BOLD, 32);
							bigfont = new Font("맑은 고딕", Font.BOLD, 38);
							temp = new Font("맑은 고딕", Font.BOLD, 18);
							bolds = new Font("맑은 고딕", Font.BOLD, 18);
							System.out.println("폰트 커짐");
						} else {
							font = new Font("맑은 고딕", Font.PLAIN, 26);
							bigfont = new Font("맑은 고딕", Font.BOLD, 32);
							temp = new Font("맑은 고딕", Font.PLAIN, 15);
							bolds = new Font("맑은 고딕", Font.BOLD, 16);
						}
					}
				}
				if (startCounter == 0) {
					repaint();
				} else {
					noStart = false;
					buffg.setColor(Color.BLACK);

					if (startCounter < 55) {
						startCounter++;
						if (startCounter > 40) {
							if (g.infoArr[6] == 0) {
								g.setSize(
										(int) (getWidth() + (screen.getWidth() - getWidth()) * 0.4),
										(int) (getHeight() + (screen
												.getHeight() - getHeight()) * 0.9));
							} else {
								g.setSize(widoww, widowh);
							}
							g.midLocation();
							// buffg.clearRect(0, 0, 1290, 805);
							// buffg.setColor(Color.BLACK);
							// buffg.fillRect(0, 0, 1290, 805);
							repaint();
						}
					} else {
						// 게임시작 화면 넘기기 다음 화면
						g.infoArr[4] = thema;
						on = false;
						g.gameFrame(thema);
					}
				}
				Thread.sleep(32);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(p==null) p = new Point();
		if (e.getKeyCode() == KeyEvent.VK_1) {
			p.x = 320;
			p.y = 205;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			p.x =320;
			p.y = 270;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			p.x = 320;
			p.y = 338;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_4) {
			p.x = 320;
			p.y = 406;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_5) {
			p.x = 800;
			p.y = 380;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			p.x = 1070;
			p.y = 775;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_R) {
			p.x = 1150;
			p.y = 775;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_T) {
			p.x = 1230;
			p.y = 775;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			p.x = 520;
			p.y = 530;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			p.x = 640;
			p.y = 520;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			g.infoArr[0] = 1;
			g.infoArr[1] = 1000;
			g.infoArr[2] = 15;
			g.infoArr[3] = 0;
			g.infoArr[4] = 0;
			g.infoArr[5] = 0;
			g.infoArr[6] = 0;
			g.infoArr[7] = 0;
			g.infoArr[8] = 0;
			g.infoArr[9] = 0;
			g.infoArr[10] = 0;
			g.infoArr[11] = 0;
			g.infoArr[12] = 0;
			g.infoArr[13] = 0;
			g.infoArr[14] = 0;
			g.infoArr[15] = 1;
			g.SaveData();
			g.deleteData();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			p.x = 850;
			p.y = 500;
			pressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_F7) {
			g.infoArr[1] += 100;
		} else if (e.getKeyCode() == KeyEvent.VK_F8) {
			g.infoArr[1] += 300;
			g.infoArr[5] += 3;
			max = 15 + ((g.infoArr[0] - 1) * 25 + 5 * (g.infoArr[0] - 1));
			now = max - (int) (g.infoArr[2] - (g.infoArr[5]));
			exp = percent;
			percent = (int) Math.floor(((double) now / max) * 100);

			thema = g.infoArr[4];
			while (now >= max) {
				levelup();
				levelUped = 120;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		p = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

}
