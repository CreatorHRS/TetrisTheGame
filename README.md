# TetrisTheGame 

Hello friend!

It is a simple java tetris. 
If you want to practice to write java code or to unerstand someone else's code, 
so I hope it may help you. 

_It's written with java jdk1.8._ 

I know it contain not enough comments, but it use very clear variable's name. 

![](https://media.giphy.com/media/LjzV5bNP1VWzL7m3v1/giphy.gif)

***
## Structure
File Tetris.java contains main method, so the program start in here. 
It load Preferences from Pref.cfg then start main window. 

File MainFrame.java contains start button and settins bitton. 

Start button launch the game. 

Each (dependent of pref) sec it check to see:   
	1. Is it colusion with flor?   
	2. Is it colusion with another block?   
	3. Is it colusion with ceiling?   
When one of them occur(except last) it check if a line is full. 
If it's true line is deleted and add 100 scors. 

***
## License
  This program is free software; you can redistribute it and/or modify 
  it under the terms of the GNU General Public License as published by 
  the Free Software Foundation; either version 2, or (at your option) 
  any later version.
 
  This program is distributed in the hope that it will be useful, 
  but WITHOUT ANY WARRANTY; without even the implied warranty of 
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
  GNU General Public License for more details.
  
  You should have received a copy of the GNU General Public License along with 
  this program; if not, write to the Free Software Foundation, Inc., 51 Franklin 
  Street, Fifth Floor, Boston, MA 02110-1301, USA. 
  
  Copyright (C) 2019 Mihail Harisov 
 
