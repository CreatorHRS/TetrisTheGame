/* This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 * Copyright (C) 2019 Mihail Harisov
 */

package MainPac;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{

	private int DOT_SIZE;
	private final int SIZEX;
	private final int SIZEY;
	private Graphics2D g2;
	private BufferedImage bi;
	private Image dot,beak,gameOver;     //image fields
	private Timer timer, timer2;      
	private Timer fps;   //fps
	private Timer animBackTimer;
	private Color color;
	private MainFrame main;
	private AnimationBack animBeak;
	private Dot[] element;
	private Dot[] nextFigur;
	private Dot[] routeFigure;
	private boolean islevl = false, inGame = false, timerFlag = false, gameOverFlag = false;  //flags
	private int rout, elementNum, nextElem;
	private Dot[][] wall;     //array with all dots in field
	private int wallLevl;
	private int deadLine;
	private long score = 0;
	private int maxDotOfElement, minDotOfElement;
	private String fpsShow;
	private long fpsCount;
	private Font font;
	
	
	public Game(MainFrame f, Container c){    //Initialization Game;
		SIZEX = 480;
		SIZEY = 640;
		DOT_SIZE = Tetris.pref.dot_size;
		System.out.println("Initializationing values");
		font = new Font("SansSerif", Font.BOLD, 18);
		minDotOfElement = SIZEY;
		maxDotOfElement = 0;
		setBackground(new Color(68,55,20));
		main = f;
		animBeak = new AnimationBack(this);
		bi = new BufferedImage(SIZEX, SIZEY, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) bi.getGraphics();
		wallLevl = SIZEY - (DOT_SIZE*2);
		element = new Dot[4];
		wall = new Dot[20][15];
		timer2 = new Timer(50, this);
		animBackTimer = new Timer(80, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				beak = animBeak.getIm();
				if(inGame) repaint();
				else c.repaint();
				
			}
		});
		animBackTimer.start();
		fps = new Timer(16,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint(0,maxDotOfElement, SIZEX, (minDotOfElement-maxDotOfElement) + DOT_SIZE);
				
			}
		});
		System.out.println("Loading images");
		loadImages();
		System.out.println("Starting");
		main.addKeyListener(new MainKeyAdapt());	
		nextFigur = new Dot[4]; 
		initDot(5);
		setVisible(true);       //set visible for JPanel
		repaint();
	}
	
	
	
	
	public void start() {
		for(int i = 0; i < SIZEX/DOT_SIZE; i++) { //init floor
			wall[19][i] = new Dot((i) * DOT_SIZE, SIZEY - DOT_SIZE, new Color(9, 70 ,76));
		}
		timer = new Timer(Tetris.pref.level.speed, this);
		inGame = true;
		timer.start();
		fps.start();
		initDot(5);
	}
	
	public void gameExit() {
		timer.stop();
		fps.stop();
		this.flushDots();
		inGame = false;
		gameOverFlag = false;
		score = 0;
	}
	
	private void flushDots() {
		int wallX = 20;
		int wallY = 15;
		
		for(int i = wallX - 1 ; i >= 0; i--) {
			for(int j = wallY - 1; j >= 0; j--) {
				wall[i][j] = null;
			}
		}
	}
	
	private  void initDot(int c){
		switch(c) {
			case 0 :{
				elementNum = 0;
				color = new Color(130,15,15);
				Dot d = new Dot((SIZEX/2) - (DOT_SIZE/2), 32, color);
				element[0] = d;
				for (int j = 1; j < 4; j++) {
					Dot dc = new Dot(d, 2);
					element[j] =  dc;
					d = dc;
				}
				break; 
			}
			
			case 1 :{
				elementNum = 1;
				color = new Color(165,197,58);
				Dot d = new Dot((SIZEX/2) - (DOT_SIZE/2), 32, color);
				element[0] = d;
				element[1] = new Dot(element[0], 2);
				element[2] = new Dot(element[1], 2);
				element[3] = new Dot(element[2], 0); 
				break;
			}
			
			case 2 :{
				elementNum = 2;
				color = new Color(11, 117,24);
				Dot d = new Dot((SIZEX/2)- (DOT_SIZE/2), 32, color);
				element[0] = d;
				element[1] = new Dot(element[0], 2);
				element[2] = new Dot(element[1], 2);
				element[3] = new Dot(element[0], 0);
				break;
			}
			
			case 3 :{               
				elementNum = 3;
				color = new Color(24, 55, 147);
				Dot d = new Dot((SIZEX/2)- (DOT_SIZE/2), 32, color);
				element[0] = d;
				element[1] = new Dot(element[0], 2);
				element[2] = new Dot(element[0], 0);
				element[3] = new Dot(element[1], 0);	
				break;
			}
			case 4 :{
				elementNum = 4;
				color = new Color(215, 125, 43);
				Dot d = new Dot((SIZEX/2)- (DOT_SIZE/2), 32, color);
				element[0] = d;
				element[1] = new Dot(element[0], 0);
				element[2] = new Dot(element[1], 2);
				element[3] = new Dot(element[1], 0);
				break;
			}
			case 5 :{
				elementNum = 5;
				color = new Color(81, 146, 146);
				Dot d = new Dot((SIZEX/2)- (DOT_SIZE/2), 32, color);
				element[0] = d;
				element[1] = new Dot(element[0], 0);
				element[2] = new Dot(element[1], 2);
				element[3] = new Dot(element[2], 0);
				break;
			}
			
		}
		rout = 0;
		nextElem = new Random().nextInt(6);
		islevl = false;
	}
	
	public void paint(Graphics g) {
		//super.paint(g);
		buffImage();
		g.drawImage(bi,0,0,SIZEX,SIZEY,this);
		fpsCount++;
	}
	
	private  void loadImages() {
		ImageIcon iia;
		if(DOT_SIZE == 16) {
		 iia = new ImageIcon(Tetris.pref.dir + "/Themes" + Tetris.pref.theme + "/Images/dot.png");
		}
		else {
			iia = new ImageIcon(Tetris.pref.dir + "/Themes" + Tetris.pref.theme +"/Images/dot32.png");
		}
		dot = iia.getImage();
		iia = new ImageIcon(Tetris.pref.dir+ "/Themes" + Tetris.pref.theme + "/Images/beak.png");
		beak = iia.getImage();
		iia = new ImageIcon(Tetris.pref.dir + "/Themes" + Tetris.pref.theme + "/Images/GameOver.png");
		gameOver = iia.getImage();
	}
	
	private void buffImage() {
		//long test = System.currentTimeMillis();       //speed test
		maxMin();
		g2.drawImage(beak, 0, 0,SIZEX, SIZEY, this);
		Dot d;
		if(inGame) {
		g2.setFont(Tetris.pref.font);
		g2.drawString("SCORE: " + score + " FPS: " + fpsShow, 100, 50);
			for(int i = 0; i < 4;i++) {
				d = element[i];
				g2.drawImage(dot, d.Xpos, d.Ypos, d.color,this);
			}
		}
		for (int i = 0; i < 20; i++) {
			for(int z = 0; z < 15; z++) {
				d = wall[i][z];
				if(d != null) {
					g2.drawImage(dot, d.Xpos, d.Ypos, d.color,this);
				}
			}		
		}
		if(!inGame & gameOverFlag) {
			g2.drawImage(gameOver, 110, 128, this);
		}
		//System.out.println("Paint: " + (System.currentTimeMillis() - test));
	}
	
	private boolean isBoundsRight() {              //
		for(int i = 0; i < 4; i++) {
			if(element[i].Xpos > SIZEX - 2*DOT_SIZE) {
				return false;
			}
		}
		
			return true;
	}
	
	private boolean isBoundsLeft() {
		for(int i = 0; i < 4; i++) {
			if(element[i].Xpos < DOT_SIZE) {
				return false;
			}
		}
		
			return true;
	}
	

	
	private void maxMin() {    //set max and mix dot position x for element
		minDotOfElement = 0;
		maxDotOfElement = SIZEY;
		for(int i = 0; i < 4; i++) {
			int temp = element[i].Ypos;
			if(temp > minDotOfElement) {
				minDotOfElement = temp + DOT_SIZE;
			}
			if(temp < maxDotOfElement) {
				maxDotOfElement = temp;
			}
		}
	}
	
	private boolean isFullLine() {               //checks line, if full return true
		int lineCounter;
		for(int i = 0; i < 4; i++) {
			lineCounter = 0;
			for(int z = 0; z < (SIZEX/DOT_SIZE); z++) {
				Dot d = wall[element[i].Ypos/DOT_SIZE][z];
				if(d != null) {
					lineCounter++;
				}
			}
			if(lineCounter == (SIZEX/DOT_SIZE)) {
				deadLine = element[i].Ypos/DOT_SIZE;
				score += 100;
				return true;
			}
		}
		return false;
	}
	
	private void deliteLine(){
		for(int i = 0; i < (SIZEX/DOT_SIZE); i++) {    //delete full line
			wall[deadLine][i] = null;
		}
		int end;
		int lineForOffser = deadLine - 1;
		do {                                          //shifts another line down
			end = 0;
			for(int i = 0; i < (SIZEX/DOT_SIZE); i++) {
				Dot d = wall[lineForOffser][i]; 
				 if(d != null) {
					 wall[lineForOffser+1][i] = new Dot(d,1);
					 wall[lineForOffser][i] = null;
					 end++;
				 }
			}
			lineForOffser--;
		}while(end > 0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//long test = System.currentTimeMillis();       //speed test
		if(inGame) {
			for(int i = 0; i < 4; i++) {    
				if(wallLevl <= minDotOfElement) {
					islevl = true;
				}
			}
			if(islevl) {				
				for(int z = 0; z < 4; z++) {        
					for(int i = 0; i < (SIZEX/DOT_SIZE); i++) {
						Dot d = wall[element[z].Ypos/DOT_SIZE + 1][i];
						if(d != null) {
							if((element[z]).isToch(d)) {	         //if collision with other blocks
								int temp = element[z].Ypos - DOT_SIZE;
								for(int k= 0; k < 4; k++) {
									wall[(element[k].Ypos/32)][(element[k].Xpos/DOT_SIZE)] = element[k];
								}
								while(isFullLine()) {
									deliteLine();
								}
								if(temp < 64) {
									inGame = false;
									gameOverFlag = true;
								}
								if(maxDotOfElement <= wallLevl) {
									wallLevl = maxDotOfElement - DOT_SIZE;
									}
								initDot(nextElem);
								repaint();
							}
						}
					}
				}
				
			}	
			
				for(int i = 0; i < 4; i++) {
					element[i].Ypos += DOT_SIZE;
					}
			
		}else{
			timer.stop();
			timer2.stop();
			fps.stop();
			System.err.println("GAME OVER");
		}
	}
	
	public void timerStopStart() {
		if(timerFlag) {
			timer.start();
			fps.start();
			timerFlag = false;
		}
		else {
			timer.stop();
			timer2.stop();
			fps.stop();
			timerFlag = true;
		}
	}
	
	
	private void routetion(){   
		
		routeFigure = element;
		switch (elementNum){
			case 0:{
				switch (rout) {
					case 0:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 0);
						routeFigure[3].transfer(routeFigure[2], 0);
						rout = 1;
						break;
					}
					case 1:{
						routeFigure[1].transfer(routeFigure[0], 2);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[2], 2);
						rout = 0;
						break;
					}
				}
				break;
			}
			case 1:{
				switch (rout) {
					case 0:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 0);
						routeFigure[3].transfer(routeFigure[2], 3);
						rout = 1;
						break;
					}
					case 1:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[2], 2);
						rout = 2;
						break;
					}
					case 2:{
						routeFigure[1].transfer(routeFigure[0], 2);
						routeFigure[2].transfer(routeFigure[0], 0);
						routeFigure[3].transfer(routeFigure[2], 0);
						rout = 3;
						break;
					}
					case 3:{
						routeFigure[1].transfer(routeFigure[0], 2);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[2], 0);
						rout = 0;
						break;
					}
					
				}
				break;
			}
			case 2:{
				switch (rout) {
					case 0:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 0);
						routeFigure[3].transfer(routeFigure[2], 2);
						rout = 1;
						break;
					}
					case 1:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 3);
						routeFigure[3].transfer(routeFigure[2], 3);
						rout = 2;
						break;
					}
					case 2:{
						routeFigure[1].transfer(routeFigure[0], 3);
						routeFigure[2].transfer(routeFigure[0], 0);
						routeFigure[3].transfer(routeFigure[2], 0);
						rout = 3;
						break;
					}
					case 3:{
						routeFigure[1].transfer(routeFigure[0], 2);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[0], 0);
						rout = 0;
						break;
					}
				}
				break;
			}
			case 4:{
				switch (rout) {
					case 0:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[1], 3);
						rout = 1;
						break;
					}
					case 1:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 3);
						routeFigure[3].transfer(routeFigure[1], 0);
						rout = 2;
						break;
					}
					case 2:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[0], 2);
						routeFigure[3].transfer(routeFigure[0], 3);
						rout = 3;
						break;
					}
					case 3:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[1], 0);
						rout = 0;
						break;
					}
				}
				break;
			}
			case 5:{
				switch (rout) {
					case 0:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 3);
						routeFigure[3].transfer(routeFigure[0], 2);
						rout = 1;
						break;
					}
					case 1:{
						routeFigure[1].transfer(routeFigure[0], 0);
						routeFigure[2].transfer(routeFigure[1], 2);
						routeFigure[3].transfer(routeFigure[2], 0);
						rout = 0;
						break;
					}
				}
				break;
			}	
		}
	}
	
	class Dot{            //class for dots
		int Xpos;
		int Ypos;
		Color color;
		
		Dot(int x, int y, Color color){
			Xpos = x;
			Ypos = y;
			this.color = color;
		}
		
		Dot(Dot d, int p){  //if 0 UP, 1 DOWN, 2 RIGHT, 3 LEFT  
			switch(p){
				case 0: {
					Xpos = d.Xpos;
					Ypos = d.Ypos - DOT_SIZE;
					break;
				}
				case 1:{
					Xpos = d.Xpos;
					Ypos = d.Ypos + DOT_SIZE;
					break;
				}
				case 2: {
					Xpos = d.Xpos + DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
				case 3: {
					Xpos = d.Xpos - DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
			}
			this.color = d.color;
		}
		
		Dot(Dot d, int p, Color color){  //if 0 UP, 1 DOWN, 2 RIGH, 3 LEFT  
			switch(p){
				case 0: {
					Xpos = d.Xpos;
					Ypos = d.Ypos - DOT_SIZE;
					break;
				}
				case 1:{
					Xpos = d.Xpos;
					Ypos = d.Ypos + DOT_SIZE;
					break;
				}
				case 2: {
					Xpos = d.Xpos + DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
				case 3: {
					Xpos = d.Xpos - DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
			}
			this.color = color;
		}
		
		Dot(Dot d){
			Xpos = d.Xpos;
			Ypos = d.Ypos;
			this.color = d.color;
		}
		
		Dot(Dot d, Color color){
			Xpos = d.Xpos;
			Ypos = d.Ypos;
			this.color = color;
		}
		
		void transfer(int x, int y) {
			Xpos = x;
			Ypos = y;
		}
		
		void transfer(Dot d, int p) {
			switch(p){
				case 0: {
					Xpos = d.Xpos;
					Ypos = d.Ypos - DOT_SIZE;
					break;
				}
				case 1:{
					Xpos = d.Xpos;
					Ypos = d.Ypos + DOT_SIZE;
					break;
				}
				case 2: {
					Xpos = d.Xpos + DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
				case 3: {
					Xpos = d.Xpos - DOT_SIZE;
					Ypos = d.Ypos;
					break;
				}
			}
		}
		
		boolean isToch(Dot d) {
			if(Xpos == d.Xpos & Ypos == d.Ypos - DOT_SIZE) {
				return true;
			}
			return false;
		}
	}
	
	class MainKeyAdapt extends KeyAdapter{     //key adapter class
		
		@Override
		public void keyTyped(KeyEvent e) {
			if (inGame & !timerFlag) { 
				if(e.getKeyChar() == 'd') {
					if( isBoundsRight()) {
						for(int i = 0; i < 4; i++) {
							element[i].Xpos += (1 * DOT_SIZE);
						}
					}
				}
				else if(e.getKeyChar() == 'a') {
					if( isBoundsLeft()) {
						for(int i = 0; i < 4; i++) {
							element[i].Xpos += (-1 * DOT_SIZE);
						}
					}
				}
				else if(e.getKeyChar() == 's') {
					timer.stop();
					timer2.start();
				}
				else if(e.getKeyChar() == 'r') {
					routetion();
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (inGame & !timerFlag) { 
				if(e.getKeyChar() == 's') {
					timer2.stop();
					timer.start();
				}
			}
		}
	}
	
}

