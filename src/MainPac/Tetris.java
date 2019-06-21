package MainPac;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.StringTokenizer;

public class Tetris { 
	static Preference pref;
	private static Path path;
	
	public static void main(String[] args) {
		pref = getPreference();
		MainFrame main = new MainFrame(pref.mainDimen);
	}
	
	static Preference getPreference() {      //rewrite
		Properties p = new Properties();
		path = null;
		try {
			path = new File(Tetris.class.getProtectionDomain().getCodeSource()     //set dir with game
											.getLocation().toURI()).getParentFile().toPath();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			p.load(new FileInputStream(path  + "/Pref.cfg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Preference.initPref(p, path);
	}
	
	static void changePref(String key, String value) {
		pref = Preference.initPref(pref.changePropPref(key, value), path);
	} 
 }

class Preference{
	Dimension mainDimen = new Dimension(736, 667);
	Dimension gameDimen = new Dimension(480, 640);
	int dot_size = 0;
	String dir = "./";
	int maxFps = 0;
	String theme = " ";
	Levl level;
	String backDir = null;
	Properties propPref = null;
	Color mainBackColor = new Color(88, 63, 61);
	Color butBackColor = new Color(22, 12, 11, 200);
	Color butForColor = null;
	Font font = new Font("", 1, 25);
	
	private Preference(){
	}
	
	static Preference initPref(Properties p, Path path) {
		Preference pref = new Preference();
		pref.propPref = p;
		pref.dir = path.toString();
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
		
		return pref;
	}
	
	Properties changePropPref(String key, String value) {
		propPref.setProperty(key, value);
		return propPref;
		
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

