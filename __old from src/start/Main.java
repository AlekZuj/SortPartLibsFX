//v 0.1-0.4 - try to read and write file? sorting AllayList.
//v 0.5 - introduced design and reminder elements. Testing started.
//v 0.6 - changed methods of making cell styles. NULL check added in delete enter method.
//v 1.0 - transition to a graphical interface. Testing started.

package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private static final String VERSION = "1.0";

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
		loader.setController(new MainController());
		Parent root = loader.load();
		primaryStage.setTitle("SPLFX v" + VERSION + " by AlekZ");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, 590, 260));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static String getVegsion() {
		return VERSION;
	}

}
