package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class DigitVisualizer extends JComponent {
	
	static final int numRows = 16;
	static final int numCols = 16;
	static final int numPixels = 256;
	static int imageIndex;
	static String imagePath = "";
	
	public void paint(Graphics g) {
		g.drawRect (0, 0, 800, 800);    

		int xCount = 0;
		int yCount = 0;

		for (int cols = 0; cols < numCols; cols++) {
			for (int rows = 0; rows < numRows; rows++) {
				g.drawRect(xCount, yCount, 50, 50);
				xCount+=50;
			}
			xCount = 0;
			yCount+=50;
		}	
		xCount = 0; yCount = 0;
		g.setColor(Color.BLACK);

		double[] buff = populateArr(imagePath, imageIndex);
		int idx = 0;
		
		for (int cols = 0; cols < numCols; cols++) {
			for (int rows = 0; rows < numRows; rows++) {
				if (buff[idx++] == 1.00) {
					g.fillRect(xCount, yCount, 50, 50);
				}
				xCount+=50;
			}
			xCount = 0;
			yCount+=50;
		}

	}

	// Grab the correct 256-vector from a given line, specified by index
	// Remember: index=0 is the first image
	public static double[] populateArr(String path, int index) {

		double[] buff = new double[numPixels];
		int stop = 0;

		try {

			Scanner in = new Scanner(new File(path));
		
			// Skip to the right line first
			for (int i = 0; i < index; i++) {
				in.nextLine();
			}
			
			while (stop < numPixels) 
				buff[stop++] = in.nextDouble();
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return buff;
	}

	public static void main(String[] a) {
		
		// Choose which image to draw (note: zero-based,
		// imageIndex = 0 will draw the first line of semeion.data)
		// I don't check for invalid arguments so be sure that -1 < imageIndex < 1593 (if using semeion.data)
		imageIndex = 4;
		imagePath = "src/data/training/semeion.data.txt";
		
		JFrame window = new JFrame();
		window.setSize(900,900);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new DigitVisualizer());
		window.setVisible(true);
		window.setTitle("Pixel Visualizer");
	}
}