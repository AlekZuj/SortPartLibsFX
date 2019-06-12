package fxml;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DoneController {

	public static void doneWindow() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox();
		Label text = new Label("Готово!");
		text.setAlignment(Pos.CENTER);
		text.setPadding(new Insets(20));
		Button btn = new Button("Ok");
		btn.setPrefSize(65, 30);
		btn.setAlignment(Pos.CENTER);
		btn.setPadding(new Insets(10));
		btn.setOnAction(event -> window.close());
		box.getChildren().addAll(text, btn);
		box.setAlignment(Pos.CENTER);
		Scene scene = new Scene(box);
		window.setScene(scene);
		window.setTitle("Сохранение...");
		window.setResizable(false);
		window.showAndWait();
	}

}
