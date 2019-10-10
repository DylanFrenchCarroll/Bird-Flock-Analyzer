import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import application.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

class jUnit {

	Image image;
	int[] thisArray;
	PixelReader pixelReader;
	PixelWriter pixelWriter;
	WritableImage writableImage;
	
SortedSet distinct = new TreeSet<>();

	
	
	@Before
	public void setUp() {

		File file = new File("files/25.png");
		 image = new Image(file.toURI().toString());
		 thisArray = new int[(int)image.getHeight() * (int)image.getWidth()];
	
	}
	
	
	@Test
	public void test1() {
		
		
		
	}
	

}
