package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import Screen.Game_Frame;
import Screen.IntroFrame;
import Screen.Root_Frame;
import Screen.StageSelect_Frame;

public class Game extends JFrame implements KeyListener, Runnable {

	Toolkit tk = Toolkit.getDefaultToolkit();

	Thread th;
	public Thread bgmth;
	Registry reg;
	Game_Frame gf;
	IntroFrame tf;
	StageSelect_Frame ssf;
	Root_Frame scene;
	Dimension screen;
	String bgmName = "none";
	Sound bgmSound;
	int stm = 0;
	public boolean bgmisPlaying = false;
	final int ver = 0;
	public double sss = 1;// ScreenSizeScale
	public int infoArr[] = new int[18];

	// * infoArr[0] = 레벨
	// * infoArr[1] = 골드
	// * infoArr[2] = 다음 레벨업 경험치
	// * infoArr[3] = 수련포인트
	// * infoArr[4] = 테마
	// * infoArr[5] = 현재 경험치
	// * infoArr[6] = 전체화면 / 창모드
	// * infoArr[7] = 스킬 1 [ 기본 ]
	// * infoArr[8] = 스킬 2 [ 부스트 ]
	// * infoArr[9] = 스킬 3 [ 에너지볼 ]
	// * infoArr[10] = 스킬 4 [ 섬광 ]
	// * infoArr[11] = 특성 1 [ 이동속도 ]
	// * infoArr[12] = 특성 2 [ 충전량 ]
	// * infoArr[13] = 특성 3 [ 체력 ]
	// * infoArr[14] = 특성 4 [ 충전속도 ]
	// * infoArr[15] = 사용중-창두개 방지
	// * infoArr[16] = bgm on/off
	// * infoArr[17] = sound on/off
	public void soundPlay(String file, int loop) {
		if (infoArr[17] == 0) {
			try {
				Sound effs = new Sound(file, loop);
				Thread th = new Thread(effs);
				th.start();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public void bgmPlay(String file, int loop) {
		if (!bgmName.equals(file)) {
			if (infoArr[16] == 0) {
				try {
					if (!bgmName.equals("none")) {
						bgmSound.stop();
						bgmth.stop();
					}
					if (file.equals("none")) {
						bgmName = "none";
						if (bgmSound != null)
							bgmSound.stop();
					}
					bgmName = file;
					bgmSound = new Sound(file, loop);
					bgmth = new Thread(bgmSound);
					bgmth.start();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	public void setData(int arr[]) {
		infoArr = arr;
	}

	public void SaveData() {
		reg.save();
	}

	public void deleteData() {
		reg.deleteData();
	}

	public void LoadData() {
		String temp = reg.load();
		String tmp[] = null;
		tmp = temp.split(",");
		try {
			for (int i = 0; i < tmp.length; i++) {
				infoArr[i] = Integer.parseInt(tmp[i]);
				System.out.println("Loaded Data : " + infoArr[i]);
			}
		} catch (Exception e) {
			for (int i = 0; i < 15; i++) {
				infoArr[i] = 0;
			}
			infoArr[0] = 1;
			infoArr[1] = 1000;
			infoArr[2] = 15;
		}
		infoArr[15]++;
		SaveData();
	}

	public void ClosingLoadData() {
		String temp = reg.load();
		String tmp[] = null;
		tmp = temp.split(",");
		try {
			for (int i = 0; i < tmp.length; i++) {
				infoArr[i] = Integer.parseInt(tmp[i]);
				System.out.println("Loaded Data : " + infoArr[i]);
			}
		} catch (Exception e) {
			for (int i = 0; i < 15; i++) {
				infoArr[i] = 0;
			}
			infoArr[0] = 1;
			infoArr[1] = 1000;
			infoArr[2] = 15;
		}
	}

	public int[] getData() {
		return infoArr;
	}

	public void setdeco() {
			setVisible(false);
			dispose();
			setUndecorated(false);
			// gd.setFullScreenWindow(this);
			setVisible(true);
			// validate();
	}

	public void setUndeco() {
		if (infoArr[6] == 0) {
			setVisible(false);
			dispose();
			setUndecorated(true);
			// gd.setFullScreenWindow(this);
			setVisible(true);
			// validate();
		}
	}

	public void resetSize() {
		setSize(1024, 768);
	}

	public void gameFrame(int thema) {
		removePrevFrame();
		gf = new Game_Frame(this, thema);
		scene = gf;// 현재 장면 저장
		// gf.init();
		// gf.start();
		if(infoArr[6]==0)
		setSize((int) screen.getWidth(), (int) screen.getHeight());
		else
		setSize((int)(1280*sss), (int)(880*sss));
		midLocation();
		add(gf);
		repaint();
		// this.gf = gf;
	}

	public void midLocation() {
//		screen = tk.getScreenSize();
//		int f_xpos = (int) (screen.getWidth() / 2 - getWidth() / 2);
//		int f_ypos = (int) (screen.getHeight() / 2 - getHeight() / 2);
//		setLocation(f_xpos, f_ypos);
	}

	public void shiftLocation(int w, int h){
		Point p = this.getLocation();
		setLocation(p.x-w, p.y-h);
	}
	public void removePrevFrame() {
		if (scene == null)
			return;
		System.out.println(scene.toString());
		remove(scene);
	}

	public void gameReadyFrame() {
		setUndeco();
		removePrevFrame();
		ssf = new StageSelect_Frame(this);
		scene = ssf;
		add(ssf);
		// setSize(1290, 820);
		screen = tk.getScreenSize();
		ssf.sss = sss;
		midLocation();
		repaint();
	}

	public void introFrame() {
		resetSize();
		setdeco();
		removePrevFrame();
		if(infoArr[6]==1){
			shiftLocation(-110, -30);
		}else{
			midLocation();
		}
		tf = new IntroFrame(this);
		scene = tf;
		add(tf);
		repaint();
	}

	Game() {
		// 찾기 : 프레임 및 윈도우 시작
		for (int i = 0; i < infoArr.length; i++) {
			infoArr[i] = 0;
		}
		reg = new Registry(this);
		LoadData();
		setTitle("총알 피하기 게임");
		setBackground(Color.black);
		setSize(1024, 768);
		screen = tk.getScreenSize();
		if (screen.getWidth() < 1280) {
			sss = screen.getWidth() / 1280;
			System.out.println("작은 화면 감지 sss = " + sss);
		}
		int f_xpos = (int) (screen.getWidth() / 2 - 1024 / 2);
		int f_ypos = (int) (screen.getHeight() / 2 - 768 / 2);

		tf = new IntroFrame(this);
		scene = tf;
		add(tf);
		addKeyListener(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				closeWindow();

			}// end windowClosing

		});// 매개변수 WindowAdapter 전달 끝

		// setLayout(new FlowLayout());
		setLocation(f_xpos, f_ypos);
		setResizable(true);
		setVisible(true);

		this.setFocusable(true);

		System.out.println("pc 시작됨");
		th = new Thread(this);
		th.start();
		// nextFrame();
	}

	public void closeWindow() {
		ClosingLoadData();
		infoArr[15]--;
		SaveData();
		th.stop();
		System.exit(0);
	}


	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(16);
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		scene.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		scene.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		scene.keyTyped(e);
	}

	public static void main(String[] ar) {
		Game mainframe = new Game();
		// System.out.println(new File("banner.jpg").getAbsolutePath());
	}
}