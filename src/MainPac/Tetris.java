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
 * Copyright (C) 2019-2020 Mihail Harisov
 */


package MainPac;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import javax.swing.*;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Tetris{
	static Preference pref;	

	public static void main(final String[] args){ // Start program!!!
		LoadingWindow loadWindow = new LoadingWindow();
		pref = Preference.initPref();
		MainFrame main = new MainFrame();
	}
	
	//static void changePref(String key, String value) {           
		//pref.propPref.setProperty(key, value);
	//}
	
	public static int persent(int num, int pro) {
		float res = (float)num / 100;
		res = res * pro;
		return (int)res;
	}
	
	public static boolean openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	            return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	public static boolean openWebpage(URL url) {
	    try {
	        return openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}

class LoadingWindow extends JFrame{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension winDem = new Dimension(procent((int)screenSize.getWidth(), 40),
												 procent((int)screenSize.getHeight(), 30));
	/*loading line demension*/
	Dimension llDem = new Dimension(procent(winDem.width, 60),  
											procent(winDem.height, 10));
	Container con = new Container();
	Label lab = new Label("Loading");
	int load = 50;

	public LoadingWindow(){ 
		setSize(winDem);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(new Color(88,63,61,255));
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("DejaVu Sans Mono", 1, procent(winDem.height, 15)));
		g.drawString("LOADING", procent(winDem.width, 40), procent(winDem.height, 30));
		g.drawRect(procent(winDem.width, 50) - (llDem.width/2), 
					procent(winDem.height, 70), llDem.width, llDem.height);
		g.fillRect(procent(winDem.width, 50) - (llDem.width/2), procent(winDem.height, 70), procent(llDem.width, load), llDem.height);
	}

	public void load(int val){
		load += val;
		repaint();
	}

	private int procent(int num, int proc){
		int temp = (num / 100) * proc;
		System.out.printf("%d\n", temp);
		return temp;
	}
}


class Preference{
	Dimension mainDimen = new Dimension(736, 667);                //Main window dimension
	Dimension gameDimen = new Dimension(480, 640);                //Game window dimension
	int dot_size;                                            
	String dir;
	int maxFps;
	String theme;
	Levl level;
	String backDir;
	Properties propPref;
	Color mainBackColor = new Color(88, 63, 61);
	Color butBackColor = new Color(22, 12, 11, 200);
	Color butForColor;
	Font font = new Font("", 1, 25);
	Path path;
	private static int def = 0;

	private Preference(){}

	public static Preference initPref(){
		if(def == 1){
			return null;
		}

		def = 1;
		Preference pref = new Preference();  
		/*Determine screen resolution*/
		try {
			pref.path = new File(Tetris.class.getProtectionDomain().getCodeSource()     //Get dir with game
											.getLocation().toURI()).getParentFile().toPath();
			pref.propPref = new Properties();
			pref.propPref.load(new FileInputStream(pref.path  + "/Pref.cfg"));  //Load pref from Pref.cfg file
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}            //to preference class
		pref.dir = pref.path.toString();
		pref.dot_size = Integer.valueOf(pref.propPref.getProperty("dot_size"));
		pref.maxFps = Integer.valueOf(pref.propPref.getProperty("FPS"));
		pref.level = Levl.valueOf(pref.propPref.getProperty("Level"));
		pref.backDir =  "/" + pref.propPref.getProperty("AnimBeak");
		pref.theme = "/" + pref.propPref.getProperty("theme");
		
		Properties theme = new Properties();                      //loading theme's colors
		StringTokenizer st;
		int r, g, b, pe;
		try {
			System.out.println(pref.dir+ "/Themes" + pref.theme + "/Theme.cfg");
			theme.load(new FileInputStream(pref.dir + "/Themes" + pref.theme + "/Theme.cfg"));
		} catch (FileNotFoundException e) {
			System.err.println("File \"theme.cfg\" is not found!");
			System.err.println("This theme may be created no correctly");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                             
		st = new StringTokenizer(theme.getProperty("mainBackColor"), ",");
		r = Integer.valueOf(st.nextToken());
		g = Integer.valueOf(st.nextToken());
		b = Integer.valueOf(st.nextToken());
		pe = Integer.valueOf(st.nextToken());
		pref.mainBackColor = new Color(r,g,b,pe);
		st = new StringTokenizer(theme.getProperty("butBackColor"), ",");
		r = Integer.valueOf(st.nextToken());
		g = Integer.valueOf(st.nextToken());
		b = Integer.valueOf(st.nextToken());
		pe = Integer.valueOf(st.nextToken());
		pref.butBackColor = new Color(r,g,b,pe);
		st = new StringTokenizer(theme.getProperty("butForColor"), ",");
		r = Integer.valueOf(st.nextToken());
		g = Integer.valueOf(st.nextToken());
		b = Integer.valueOf(st.nextToken());
		pe = Integer.valueOf(st.nextToken());
		pref.butForColor = new Color(r,g,b,pe);
		
		return pref;                             //Return preference class 
	}

}


enum Levl{                        
	level1(500, 0), level2(450, 1), level3(400,2), level4(350, 3), level5(300, 4);
	int speed;
	int index;
	Levl(int s, int i){
		speed = s;
		index = i;
	}
	
	Levl getLevel(int index) {
		Levl[] l = Levl.values();
		return l[index];
	}
}