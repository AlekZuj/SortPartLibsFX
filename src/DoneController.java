
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
	
	 public static void doneWindowCompare() {
	        Alert doneWindow = new Alert(AlertType.INFORMATION);
	        doneWindow.setTitle("Готово");
	        doneWindow.setHeaderText(null);
	        doneWindow.setContentText("Эти перечни идентичны.");
	        doneWindow.showAndWait();
	    }

	    public static void doneWindowCompareMsg(String msg) {
	        Alert doneWindow = new Alert(AlertType.INFORMATION);
	        doneWindow.setTitle("Готово");
	        doneWindow.setHeaderText(null);
	        doneWindow.setContentText("Готово! " + msg + " перезаписан!");
	        doneWindow.showAndWait();
	    }
}
