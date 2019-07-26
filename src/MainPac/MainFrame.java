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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.TreeSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {                       //It's Main window which contains all containers!
	Game game; 
	SettingsContainer setCon;
	RightContainer righCon;
	MiddleContainer midCon;
	AboutContainer aboutCon;
	
	MainFrame(Dimension mainSize){
		Container con = new Container();                                       //main container
		game = new Game(this, con);
		setCon = new SettingsContainer();
		righCon = new RightContainer();
		midCon = new MiddleContainer();
		aboutCon = new AboutContainer();
		
		setTitle("Tetris the game");                                    //set frame settings
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(mainSize);
		setResizable(false);
		setLocationRelativeTo(null);
		setBackground(Tetris.pref.mainBackColor);
		setContentPane(con);
		
		con.add(setCon);                                              //add into container
		con.add(righCon);
		con.add(midCon);
		con.add(aboutCon);
		con.add(game);
		
		
		game.setSize(Tetris.pref.gameDimen);
		game.setLocation(((this.getWidth() - game.getWidth())/2), 0);
		setVisible(true);
	}	


	class MiddleContainer extends Container {              // Middle container!!!
		MyButton start, settings, about;
		//MyLable theme, difficult;
		Container thisCon = this;
		
		MiddleContainer(){
			start = new MyButton("/start.png", new Dimension(300,100));  
			settings = new MyButton("/settings.png", new Dimension(300, 80));
			about = new MyButton("/about.png", new Dimension(200, 60));
			
			//difficult = new MyLable("difficult");
			
			setSize(736,680);
			add(start);
			add(settings);	
			add(about);
			
			
			start.setLocation(((getWidth() / 2) - (start.getWidth()/2)), 110); //start button preference;
			start.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					midCon.setVisible(false);
					righCon.setVisible(true);
					game.start();
				}
			});
			
			settings.setLocation(((getWidth() / 2) - (start.getWidth()/2)), 250);
			settings.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					midCon.setVisible(false);
					setCon.setVisible(true);
				}
			});
			
			about.setLocation(((getWidth() / 2) - (about.getWidth()/2)), 380);
			about.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					midCon.setVisible(false);
					setCon.setVisible(false);
					aboutCon.setVisible(true);
				}
			});
			setVisible(true);
		}
	}
	
	class RightContainer extends Container {                   //Right container
		MyButton pauseBut = new MyButton("/pause.png", new Dimension(110, 50), 80, 50);
		MyButton exitBut = new MyButton("/exit.png", new Dimension(100, 50), 80, 60);
		Container thisCon = this;
		
		RightContainer(){
			add(pauseBut);
			add(exitBut);
			setSize((Tetris.pref.mainDimen.width - Tetris.pref.gameDimen.width)/2, 640);
			pauseBut.setLocation((getSize().width - 100)/2, 100); //pause button preference
			pauseBut.addActionListener(new ActionListener() {
				boolean flagPress = false;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					game.timerStopStart();
					if(flagPress) {
						pauseBut.setBackground(Tetris.pref.butBackColor);
						flagPress = false;
					}
					else {
						pauseBut.setBackground(pauseBut.getForeground());;
						flagPress = true;
					}
				}
			});
			
			exitBut.setLocation((getSize().width - 100)/2, 200);
			exitBut.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					game.gameExit();
					midCon.setVisible(true);
					righCon.setVisible(false);
				}
					
				
			});
			setVisible(false);
		}
		
	}
	
	class AboutContainer extends Container{
		MyButton gitHub;
		MyButton twitter;
		MyButton repository;
		MyButton back;
		Label copy;
		Image mascot;
		
		public AboutContainer() {
			gitHub = new MyButton("/GitHub.png", new Dimension(200, 60));
			twitter = new MyButton("/twitter.png", new Dimension(200, 60),85,60);
			repository = new MyButton("/repository.png", new Dimension(220, 60),80, 60);
			back = new MyButton("/back.png", new Dimension(100, 50));
			 
			setSize(480,680);
			setVisible(false);
			setLocation((Tetris.pref.mainDimen.width - 480)/2, 0);
			add(gitHub);
			add(twitter);
			add(repository);
			add(back);
			gitHub.setLocation(260, 200);
			gitHub.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						Tetris.openWebpage(new URL("https://github.com/CreatorHRS"));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			 
			twitter.setLocation(260, 280);
			twitter.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							Tetris.openWebpage(new URL("https://twitter.com/CreatorHRS"));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
			});
			
			repository.setLocation((Tetris.pref.gameDimen.width - 220) / 2, 380);
			repository.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						Tetris.openWebpage(new URL("https://github.com/CreatorHRS/TetrisTheGame"));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			back.setLocation(200, 550);
			back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					aboutCon.setVisible(false);
					midCon.setVisible(true);
					
				}
				 
			 });
			 
			 ImageIcon iia;
			 iia = new ImageIcon(Tetris.pref.dir + "/Image1.png");
			 mascot = iia.getImage();
		}
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(mascot,0,100,256,256,this);
			g.setFont(new Font("", 1, 20));
			g.drawString("Copyright © 2019", 260, 150);
			g.drawString("Mihail Harisov", 280, 180);
		}
	}
	
	class SettingsContainer extends Container {            //settings container!!!
		Difficults dif = new Difficults();
		Themes theme = new Themes();
		MyButton save;
		MyLable themeLbl , difLbl;
		boolean wasChange = false;
		
		
		SettingsContainer() {
			save = new MyButton("/save.png", new Dimension(100, 50));
			themeLbl = new MyLable("Theme", 100);
			difLbl = new MyLable("Difficult", 120);		
			
			setSize(480,680);
			setVisible(false);
			setLocation((Tetris.pref.mainDimen.width - 480)/2, 0);
			add(themeLbl);
			add(theme);
			add(difLbl);
			add(dif);
			add(save);
			
			themeLbl.setLocation(((getWidth() / 2) - (themeLbl.getWidth()/2)),190);
			difLbl.setLocation(((getWidth() / 2) - (difLbl.getWidth()/2)),275);
			save.setLocation((getSize().width - 100)/2, 500);  //save button preference
			save.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					midCon.setVisible(true);
					setCon.setVisible(false);
					if(wasChange) {
						try {
							Tetris.pref.propPref.store(new FileOutputStream(Tetris.pref.dir + "/Pref.cfg"), "Owner");
							System.out.println("Settings was saved sucsessflly!");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						wasChange = false;
					}
				}
			});
			
		}
		
	void update() {
			dif.setSelectedIndex(Tetris.pref.level.index);
	}
	
	
	
	class Themes extends JComboBox<String>{
		Themes(){
			setSize(300, 50);
			setLocation((480 - 300)/2, 220);
			setBackground(Tetris.pref.butBackColor);
			setFont(Tetris.pref.font);
			setForeground(Color.WHITE);
			setFocusable(false);
			addItem(Tetris.pref.propPref.get("theme").toString());
			File f = new File(Tetris.pref.dir + "/Themes");
			String[] list = f.list();
			TreeSet<String> treeSet = new TreeSet<>();
			for (int i = 0; i < list.length; i++) {
				treeSet.add(list[i]);
			}
			for (String string : treeSet) {
				System.out.println(string);
				if(!Tetris.pref.propPref.get("theme").toString().equals(string)) {
					addItem(string);
				}
			}
			addItemListener(new ItemListener() {
		
				@Override
				public void itemStateChanged(ItemEvent e) {
					Tetris.changePref("theme", e.getItem().toString());
					wasChange = true;
				}
			});
		}
	}
		
	class Difficults extends JComboBox<String>{
			Difficults(){
				setSize(300, 50);
				setLocation((480 - 300)/2, 300);
				setBackground(Tetris.pref.butBackColor);
				setFont(new Font("", 1, 25));
				setForeground(Color.WHITE);
				setFocusable(false);
				addItem("level1");
				addItem("level2");
				addItem("level3");
				addItem("level4");
				addItem("level5");
				setSelectedIndex(Tetris.pref.level.index);
				addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						Tetris.changePref("Level", e.getItem().toString());
						wasChange = true;
					}
				});
			}
		}
	}
}

class MyLable extends JLabel{
	MyLable(String str, int w){
		super(str);
		setSize(w, 25);
		setForeground(Color.BLACK);
		setFont(Tetris.pref.font);
	}
}

class MyButton extends JButton{                           //Custom button class
	ImageIcon iia;
	Image im;
	
	MyButton(String imageLocation, Dimension d){
		
		setBorderPainted(true);
		setFocusable(false);
		setForeground(Tetris.pref.butForColor);
		setBackground(Tetris.pref.butBackColor);
		setSize(d);
		
		iia = new ImageIcon(Tetris.pref.dir +"/Themes"+ Tetris.pref.theme + "/Images" + imageLocation);
		im = iia.getImage();
		im = im.getScaledInstance(Tetris.persent(d.width, 80), Tetris.persent(d.height, 70), 0);
		iia.setImage(im);
		setIcon(iia);
	}
	
	MyButton(String imageLocation, Dimension d, int widthPer, int heightPer){
		
		setBorderPainted(true);
		setFocusable(false);
		setForeground(Tetris.pref.butForColor);
		setBackground(Tetris.pref.butBackColor);
		setSize(d);
		
		iia = new ImageIcon(Tetris.pref.dir +"/Themes"+ Tetris.pref.theme + "/Images" + imageLocation);
		im = iia.getImage();
		im = im.getScaledInstance(Tetris.persent(d.width, widthPer), Tetris.persent(d.height, heightPer), 0);
		iia.setImage(im);
		setIcon(iia);
	}
	
} 
