package application;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("unused")
public class Controller {

	@FXML
	private MenuItem openFile;
	@FXML
	private ImageView originalImage;
	@FXML
	private ImageView blackWhiteImage;
	Image image;
	int[] thisArray;
	PixelReader pixelReader;
	PixelWriter pixelWriter;
	WritableImage writableImage;

	SortedSet distinct = new TreeSet<>();

	public int numOfBirds() {

		for (int i = 0; i < thisArray.length; i++) {
			distinct.add(find(thisArray, i)); // adding unions to distinct set
		}
		// removeNoise();

		int numOfBirds = distinct.size() - 1; // -1 = Removing the zeroes
		System.out.println("\n Number:" + numOfBirds);
		return numOfBirds;

	}

	/*
	 * 
	 * public void removeNoise() {
	 * 
	 * for(int l =0; l<distinct.size(); l++) { Object[] h = distinct.toArray();
	 * 
	 * if(count(thisArray, find(thisArray, (int) h[l])) < 4 ) { distinct.remove(l);
	 * } }
	 * 
	 * //Was trying to remove noise by removing union } //with a count less than a
	 * specific number
	 * 
	 * 
	 * 
	 * 
	 * public int count(int[] anything, int k) { int s = 0; int count = 0; for (int
	 * i = 0; i < anything.length; i++) {
	 * 
	 * anything[i] = s;
	 * 
	 * if (s == k) { count++; }
	 * 
	 * }
	 * 
	 * return count; }
	 * 
	 */

	@FXML
	public void fileChoose() {
		Image fileImage;
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")); // Choosing
																													// file

		Object selectedFile = fileChooser.showOpenDialog(null);
		image = new Image(((File) selectedFile).toURI().toString());
		if (selectedFile != null) {

			originalImage.setImage(image); // setting the original image in image view
		}

		PixelReader pixelReader = image.getPixelReader();
		WritableImage writableImage = new WritableImage((int) image.getWidth(), // Pixelwriter/reader and WritableImage
				(int) image.getHeight());
		PixelWriter pixelWriter = writableImage.getPixelWriter();

		thisArray = new int[(int) image.getHeight() * (int) image.getWidth()]; // setting size of array the same as
																				// image
		for (int i = 0; i < thisArray.length; i++)
			thisArray[i] = i;

		int j = 0;
		double average = 0;
		double averageRight = 0;
		double averageBelow = 0;

		for (int y = 0; y < image.getHeight(); y++) { // nested for loop to iterate through image
			for (int x = 0; x < image.getWidth(); x++) {

				Color mainColor = pixelReader.getColor(x, y);
				average = mainColor.getRed() + mainColor.getGreen() + mainColor.getBlue() / 3; // getting average to
																								// determine black or
																								// white
				if (average < 0.5) {

					pixelWriter.setColor(x, y, Color.BLACK); // if average of pixel is less than 0.5 change to black
					/*
					 * Rectangle rect = new Rectangle(x,y,1,1); rect.setFill(Color.BLUE);
					 * rect.setOpacity(60);
					 */

					if (x < image.getWidth() - 1) {
						Color colorRight = pixelReader.getColor(x + 1, y); // getting average of color to right
						averageRight = colorRight.getRed() + colorRight.getGreen() + colorRight.getBlue() / 3;
					}

					if (y < image.getHeight() - 1) {
						Color colorBelow = pixelReader.getColor(x, y + 1); /// average of color below
						averageBelow = colorBelow.getRed() + colorBelow.getGreen() + colorBelow.getBlue() / 3;
					}

					// Unioning if black

					if (averageRight < 0.5) {
						union(thisArray, j, j + 1);
						// if the average of the pixel to the right is less than 0.5 union
					}
					if (averageBelow < 0.5) {
						union(thisArray, j, j + (int) image.getWidth()); // if the average of the pixel below is less
																			// than 0.5, union
					}
				} else {
					pixelWriter.setColor(x, y, Color.WHITE); // else set it white
					thisArray[j] = 0; // else set to 0 in Array
				}

				j++;
			}
		}

		blackWhiteImage.setImage(writableImage); // setting black and white image

		for (int i = 0; i < thisArray.length; i++) { // Printing out in console to test unions
			System.out
					.print((thisArray[i] == 0 ? 0 : find(thisArray, i)) + ((i % image.getWidth() == 0) ? "\n " : " "));
		}
		numOfBirds();
	}

	// union-find
	public static int find(int[] a, int id) {
		while (a[id] != id) {
			a[id] = a[a[id]];
			id = a[id];
		}
		return id;
	}

	public static void union(int[] a, int p, int q) {
		a[find(a, q)] = find(a, p);
	}

}// end of class
