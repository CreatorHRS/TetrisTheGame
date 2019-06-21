package MainPac;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class AnimationBack {
	private Image im;
	private ImageIcon iia;
	private String path;
	private int counter = 0;
	private ArrayList<Image> backArray;
	private String folder;
	
	AnimationBack(Game g){
		System.out.println("Loading backbraund");
		folder = Tetris.pref.backDir;
		backArray = new ArrayList<Image>();
		for(int i = 0;i < 24; i++) {
			path = Tetris.pref.dir + "/AnimationBackgraunds" + folder + "/" +  i + ".png";
			iia = new ImageIcon(path);
			backArray.add(iia.getImage());
		}
	}
	
	public Image getIm() {
		//long test = System.nanoTime();
		if(counter > 23) {
			counter = 0;
		}
		im = backArray.get(counter);
		counter++;
		//System.out.println(System.nanoTime() - test);
		return im;
	}
}
