package Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import Game.Game;
import Game.Sound;

public class IntroFrame extends Root_Frame implements ActionListener, Runnable {
	private JRootPane jrp;
	private JPanel con = new JPanel();
	private JPanel jp = new JPanel();
	private boolean bgmsw;
	Game gf;
	JButton b1;
	JButton b2;
	JButton b3; // close
	ButtonGroup bgm = new ButtonGroup();
	JButton r1;
	JButton r2;//
	JButton r3;//
	Thread th;
	Sound es; // 2014/05/23
	Font font = new Font("Dialog", Font.BOLD, 30);// 2014/05/23
	JLabel l1 = new JLabel("배경음");
	JLabel l2 = new JLabel("효과음");//
	JLabel l3 = new JLabel("전체화면");//

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			// gf.nextFrame();
			gf.gameReadyFrame();
		} else if (e.getSource() == b2) {
			jp.setVisible(true);
		} else if (e.getSource() == b3) {
			jp.setVisible(false);
		} else if (e.getSource() == r1) {
			if (gf.infoArr[16] == 1) {
				r1.setLabel("ON");
				gf.infoArr[16] = 0;
			} else {
				th.stop();
				r1.setLabel("OFF");
				gf.infoArr[16] = 1;
				if (gf.bgmisPlaying) {
					gf.bgmth.stop();
				}
			}
		} else if (e.getSource() == r2) {
			if (gf.infoArr[17] == 1) {
				r2.setLabel("ON");
				gf.infoArr[17] = 0;
			} else {
				r2.setLabel("OFF");
				gf.infoArr[17] = 1;
			}
		} else if (e.getSource() == r3) {
			if (gf.infoArr[6] == 1) {
				r3.setLabel("전체화면");
				gf.infoArr[6] = 0;
			} else {
				r3.setLabel("창모드");
				gf.infoArr[6] = 1;
			}
		}
	}

	public IntroFrame(Game gf) {
		// bgmsw = true;
		//
		// try {
		// es = new Sound(
		// "rsc/Ariana Grande-01-Problem (Feat. Iggy Azalea).wav");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		th = new Thread(es);
		th.start();
		this.gf = gf;

		b1 = new JButton(new ImageIcon("rsc/START.png"));
		b2 = new JButton(new ImageIcon("rsc/SETTING.png"));
		b3 = new JButton(new ImageIcon("rsc/close.png"));
		l1.setFont(font);// 2014/05/23
		l2.setFont(font);//
		l3.setFont(font);
		setLayout(null);
		jp.setLayout(null);
		if (gf.infoArr[16] == 0) {
			r1 = new JButton("ON");
		} else {
			r1 = new JButton("OFF");
		}

		if (gf.infoArr[17] == 0) {
			r2 = new JButton("ON");
		} else {
			r2 = new JButton("OFF");
		}
		if (gf.infoArr[6] == 0) {
			r3 = new JButton("전체화면");
		} else {
			r3 = new JButton("창모드");
		}

		r1.addActionListener(this);
		r2.addActionListener(this);
		r3.addActionListener(this);//
		bgm.add(r1);
		r1.setBounds(200, 100, 182, 69);
		r2.setBounds(200, 200, 182, 69);//
		r3.setBounds(200, 300, 182, 69);//
		l1.setBounds(100, 100, 182, 69);
		l2.setBounds(100, 200, 182, 69);//
		l3.setBounds(70, 300, 182, 69);//

		jp.add(r1);
		jp.add(r2);//
		jp.add(r3);//
		jp.add(l1);
		jp.add(l2);//
		jp.add(l3);//

		b1.setBounds(521, 370, 182, 69);
		b2.setBounds(521, 440, 182, 69);
		/*
		 * con.add(b1); con.add(b2); con.setVisible(true);
		 * con.setBackground(Color.white);
		 */
		setBackground(Color.white);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		b3.setBounds(360, 400, 96, 48);
		jp.setBounds(10, 10, 500, 500);
		jp.add(b3);

		// jp.setOpaque(true);
		// jrp.setGlassPane(jp);
		add(b1);
		add(b2);
		// add(b3);
		add(jp);
		jp.setVisible(false);
		this.setSize(800, 600);
		this.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			gf.gameReadyFrame();
		} else if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			jp.setVisible(true);
		}else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gf.closeWindow();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
