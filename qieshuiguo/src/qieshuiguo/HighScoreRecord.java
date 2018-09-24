package qieshuiguo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;

public class HighScoreRecord {
	private int score;
		public void saveScore(int i) {
			try {
				FileInputStream is = new FileInputStream("test.txt");
				InputStreamReader isr = new InputStreamReader(is);
				int ch = 0;  
				while ((ch = isr.read()) != -1) {  
				    System.out.print("历史最高分："+ ch);
			}
				
				if(i>ch) {
				FileOutputStream out = new FileOutputStream("test.txt");
				 PrintStream p=new PrintStream(out);
				 p.print(i);}
			} catch (Exception e1) {
				e1.printStackTrace();
			}  

		}
		
			
		}
		

