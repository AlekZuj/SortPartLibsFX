
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DoneController {

	public static void doneWindow() {
		Alert doneWindow = new Alert(AlertType.INFORMATION);
		doneWindow.setTitle("������");
		doneWindow.setHeaderText(null);
		doneWindow.setContentText("������!");
		doneWindow.showAndWait();
	}
	
	 public static void doneWindowCompare() {
	        Alert doneWindow = new Alert(AlertType.INFORMATION);
	        doneWindow.setTitle("������");
	        doneWindow.setHeaderText(null);
	        doneWindow.setContentText("��� ������� ���������.");
	        doneWindow.showAndWait();
	    }

	    public static void doneWindowCompareMsg(String msg) {
	        Alert doneWindow = new Alert(AlertType.INFORMATION);
	        doneWindow.setTitle("������");
	        doneWindow.setHeaderText(null);
	        doneWindow.setContentText("������! " + msg + " �����������!");
	        doneWindow.showAndWait();
	    }
}
