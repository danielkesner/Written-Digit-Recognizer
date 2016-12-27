package backend;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessor {
	
	static final int WHITE = 0;
	static final int BLACK = 1;

	// Takes a path to a color photo, creates a new file in same directory
	// with the original photo's name + "_bw" appended at the end
	public static void convertToBlackAndWhite(String pathToImage) throws IOException {
		BufferedImage orig = ImageIO.read(new File(pathToImage));	
		BufferedImage blackWhite = new BufferedImage(orig.getWidth(), orig.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

		Graphics2D g = blackWhite.createGraphics();

		g.drawImage(orig, 0, 0, null);

		String newPath = pathToImage.substring(0, pathToImage.length()-4)
				+ "_bw." + getExtension(pathToImage);

		// Keep same file extension as original, append "_bw" to path before extension
		saveImageToFile(blackWhite, newPath);
	}

	public static void saveImageToFile(BufferedImage bi, String path) throws IOException {
		ImageIO.write(bi, getExtension(path), new File(path));
	}

	private static String getExtension(String path) {
		return path.substring(path.length()-3, path.length());
	}

	public static BufferedImage createObjectFromFile(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	// Returns vector of binary black/white elements representing the pixels of the BufferedImage argument
	// NOTE: Assumes images have already been converted to black and white
	// Do not use this method for colored images, results will be garbage
	public static double[] getPixelVector(BufferedImage image) {
		
		// Store the results of getRGB() in a temporary array
		int[] vec = new int[image.getWidth() * image.getHeight()];
		vec = image.getRGB(0, 0, image.getWidth(), image.getHeight(), vec, 0, image.getWidth());
		
		int red, green, blue;
		
		// Return the average of red, green, blue values of each pixel
		// Initially produces pixels such that white is 255, black is 0; translate to black is 1, 0 is white
		for (int i = 0; i < vec.length; i++) {
			
			red = (vec[i] >> 16) & 0x000000FF;;
			green = (vec[i] >> 8) & 0x000000FF;
			blue = vec[i] & 0x000000FF;
			
			// Code white pixels to white/black
			if (( (red+green+blue) / 3) == 255)
				vec[i] = WHITE;
			else 
				vec[i] = BLACK;
		}
		
		// Convert to double[], return (bleh)
		double[] ret = new double[vec.length];
		
		for (int i = 0; i < ret.length; i++) 
			ret[i] = vec[i];
		
		return ret;
		
	}
	
	private static String getIdentifierString(int number) {
		switch (number) {
		case 0: return "1 0 0 0 0 0 0 0 0 0";
		case 1: return "0 1 0 0 0 0 0 0 0 0";
		case 2: return "0 0 1 0 0 0 0 0 0 0";
		case 3: return "0 0 0 1 0 0 0 0 0 0";
		case 4: return "0 0 0 0 1 0 0 0 0 0";
		case 5: return "0 0 0 0 0 1 0 0 0 0";
		case 6: return "0 0 0 0 0 0 1 0 0 0";
		case 7: return "0 0 0 0 0 0 0 1 0 0";
		case 8: return "0 0 0 0 0 0 0 0 1 0";
		case 9: return "0 0 0 0 0 0 0 0 0 1";
		default: throw new RuntimeException("Invalid input to getNumberString(): " + number);
		}
	}
	
	public static String createANNInput(BufferedImage image, int numberToIdentify) {
		
		double[] pixels = getPixelVector(image);
		String ret = "";
		
		for (int i = 0; i < pixels.length; i++) {
			ret += Double.toString(pixels[i]) + " ";
		}
		
		ret += getIdentifierString(numberToIdentify);
		
		return ret;
	}
	
	public static void main(String[] a) throws IOException {

//		convertToBlackAndWhite("src/data/cropped_0.png"); // creates cropped_0_bw.png in dir
//		convertToBlackAndWhite("src/data/cropped_0_2.png");
//		convertToBlackAndWhite("src/data/cropped_7.png");
//		convertToBlackAndWhite("src/data/cropped_3.png");
//		convertToBlackAndWhite("src/data/cropped_3_2.png");
//		
//		ImageResizer.resize("src/data/cropped_0_bw.png", "src/data/processed_0.png", 16, 16);
//		ImageResizer.resize("src/data/cropped_0_2_bw.png", "src/data/processed_0_2.png", 16, 16);
//		ImageResizer.resize("src/data/cropped_7_bw.png", "src/data/processed_7.png", 16, 16);
//		ImageResizer.resize("src/data/cropped_3.png", "src/data/processed_3.png", 16, 16);
//		ImageResizer.resize("src/data/cropped_3_2.png", "src/data/processed_3_2.png", 16, 16);
//		
//		BufferedImage img_0 = createObjectFromFile("src/data/processed_0.png");
//		BufferedImage img_0_2 = createObjectFromFile("src/data/processed_0_2.png");
//		BufferedImage img_7 = createObjectFromFile("src/data/processed_7.png");
//		BufferedImage img_3 = createObjectFromFile("src/data/processed_3.png");
//		BufferedImage img_3_2 = createObjectFromFile("src/data/processed_3_2.png");
//		
//		System.out.println("0: " + createANNInput(img_0, 0));
//		System.out.println("0: " + createANNInput(img_0_2, 0));
//		System.out.println("7: " + createANNInput(img_7, 7));
//		System.out.println("3: " + createANNInput(img_3, 3));
//		System.out.println("3: " + createANNInput(img_3_2, 3));
		
		
		
		
	}

}
