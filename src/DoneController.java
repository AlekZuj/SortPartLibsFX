
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DoneController {

	public static void doneWindow() {
		Alert doneWindow = new Alert(AlertType.INFORMATION);
		doneWindow.setTitle("Готово");
		doneWindow.setHeaderText(null);
		doneWindow.setContentText("Готово!");
		doneWindow.showAndWait();
	}
}
