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
