package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			TabPane root = FXMLLoader.load(getClass().getResource("image.fxml"));

			Scene scene = new Scene(root, 600, 400);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}// class end
