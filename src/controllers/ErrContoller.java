package controllers;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

public class ErrContoller {

	public static String errWindowProjectName() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox(10);
		box.setSpacing(10);
		Label text = new Label("Введите имя проекта.");
		text.setAlignment(Pos.TOP_CENTER);
		text.setPadding(new Insets(10, 10, 10, 10));
		TextField tf = new TextField();
		tf.setAlignment(Pos.CENTER);
		tf.setPrefSize(100, 15);
		tf.setPadding(new Insets(10, 10, 10, 10));
		Button btn = new Button("Ok");
		btn.setPrefSize(65, 30);
		btn.setAlignment(Pos.BOTTOM_CENTER);
		btn.setPadding(new Insets(10, 10, 10, 10));
		if (tf.getText().trim() != null || !tf.getText().trim().isEmpty()) {
			btn.setOnAction(event -> window.close());
		}
		box.getChildren().addAll(text, tf, btn);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		Scene scene = new Scene(box);
		window.setScene(scene);
		window.setTitle("Ошибка");
		window.setResizable(false);
		window.showAndWait();
		return tf.getText().trim();
	}

	public static String errWindowFilePath() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox(10);
		box.setSpacing(10);
		Label text = new Label("Введите путь к файлу.");
		text.setAlignment(Pos.TOP_CENTER);
		text.setPadding(new Insets(10, 10, 10, 10));
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		TextField tf = new TextField();
		tf.setAlignment(Pos.CENTER_LEFT);
		tf.setMinSize(200, 15);
		tf.setPadding(new Insets(10));
		Button btnChoose = new Button("...");
		btnChoose.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
		btnChoose.setPadding(new Insets(10));
		btnChoose.setOnAction(event -> {
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File("c:\\"));
			fc.getExtensionFilters().addAll(new ExtensionFilter("XLSX Files", "*.xlsx"));
			File selectedFile = fc.showOpenDialog(null);
			if (selectedFile != null) {
				tf.setText(selectedFile.getAbsolutePath());
			}
		});
		hbox.getChildren().addAll(tf, btnChoose);
		Button btn = new Button("Ok");
		btn.setPrefSize(65, 30);
		btn.setAlignment(Pos.BOTTOM_CENTER);
		btn.setPadding(new Insets(10, 10, 10, 10));
		if (tf.getText().trim() != null || !tf.getText().trim().isEmpty()) {
			btn.setOnAction(event -> window.close());
		}
		box.getChildren().addAll(text, hbox, btn);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.CENTER);
		Scene scene = new Scene(box);
		window.setScene(scene);
		window.setTitle("Ошибка");
		window.setResizable(false);
		window.showAndWait();
		return tf.getText().trim();
	}

	public static void errWindow(String str) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		VBox box = new VBox(10);
		Label text = new Label(str);
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
		window.setTitle("Ошибка");
		window.setResizable(false);
		window.showAndWait();
	}

	 
}
