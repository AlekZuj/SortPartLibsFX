
//v 0.1-0.4 - try to read and write file? sorting AllayList.
//v 0.5 - introduced design and reminder elements. Testing started.
//v 0.6 - changed methods of making cell styles. NULL check added in delete enter method.
//v 1.0 - transition to a graphical interface. Testing started.
//v 1.1 - added NullPointerExsception in catch. New DoneController.
//v 1.2 - no-break space (char 160) problem in hight/width/length debugged. WriteExselFile(ral) if designation contains SLDPRT write swFile name. Text size in constant. Add ico.
//v 1.3 - get Lib Files through the LAN. Del 0 from dates in designation. write logFile without reading.
//v 1.4 - Debugged NullPointerExceptions in Sort and Write classes. Added check of double positions in designations.
//v 1.5 - Added compare function. refactored some code. Made better ErrControllet
//v 1.5.1 - date-to-string change year to 2020;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import compareClasses.CompareList;
import compareClasses.PartShort;
import compareClasses.XlsxMarker;
import compareClasses.XlsxReader;
import controllers.DoneController;
import controllers.ErrContoller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Main extends Application {
	private static final String VERSION = "1.5.1";
	private static final int FONT_SIZE = 14;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("SPLFX v" + VERSION + " by AlekZ");
		primaryStage.setResizable(false);
		File icoFile = new File("vents_ico.png");
		String icoLocalUrl = icoFile.toURI().toURL().toString();
		Image ico = new Image(icoLocalUrl);
		primaryStage.getIcons().add(ico);
		VBox mainBox = new VBox(10);
		mainBox.setPadding(new Insets(10));
		mainBox.setAlignment(Pos.CENTER);
		File imageFile = new File("logo_vents_min.png");
		String imageLocalUrl = imageFile.toURI().toURL().toString();
		ImageView logo = new ImageView(imageLocalUrl);
		HBox rowOne = new HBox(20);
		rowOne.setAlignment(Pos.CENTER_LEFT);
		Label labelFirst = new Label("Путь к файлу перечня: ");
		labelFirst.setFont(new Font(FONT_SIZE));
		labelFirst.setPadding(new Insets(10));
		TextField filePathField = new TextField();
		filePathField.setMinSize(200, 25);
		Button btnChoose = new Button("...");
		btnChoose.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
		btnChoose.setPadding(new Insets(10));
		btnChoose.setOnAction(event -> {
			filePathField.setText(pressBtnChoose());
		});
		rowOne.getChildren().addAll(labelFirst, filePathField, btnChoose);
		HBox rowTwo = new HBox(20);
		rowTwo.setAlignment(Pos.CENTER_LEFT);
		Label labelTwo = new Label("Имя проекта: ");
		labelTwo.setFont(new Font(FONT_SIZE));
		labelTwo.setPadding(new Insets(10));
		TextField projectName = new TextField();
		projectName.setMinSize(100, 25);
		CheckBox coatingCheck = new CheckBox("Добавить покраску");
		coatingCheck.setFont(new Font(FONT_SIZE));
		rowTwo.getChildren().addAll(labelTwo, projectName, coatingCheck);
		HBox btnRow = new HBox(30);
		btnRow.setAlignment(Pos.CENTER);
		Button btnCompare = new Button("Сравнить");
		btnCompare.setFont(new Font(FONT_SIZE));
		btnCompare.setMinSize(50, 20);
		btnCompare.setAlignment(Pos.CENTER);
		btnCompare.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				VBox mBox = new VBox(10);
				mBox.setPadding(new Insets(10));
				mBox.setAlignment(Pos.CENTER);
				HBox rowOneMod = new HBox(20);
				rowOneMod.setAlignment(Pos.CENTER_LEFT);
				Label lFirst = new Label("Путь к первому файлу перечня: ");
				lFirst.setFont(new Font(FONT_SIZE));
				lFirst.setPadding(new Insets(10));
				TextField filePathOne = new TextField();
				filePathOne.setMinSize(200, 25);
				Button btnChooseOne = new Button("...");
				btnChooseOne.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
				btnChooseOne.setPadding(new Insets(10));
				btnChooseOne.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						filePathOne.setText(pressBtnChoose());
					}

				});
				rowOneMod.getChildren().addAll(lFirst, filePathOne, btnChooseOne);
				HBox rowTwoMod = new HBox(20);
				rowTwoMod.setAlignment(Pos.CENTER_LEFT);
				Label lTwo = new Label("Путь ко второму файлу перечня: ");
				lTwo.setFont(new Font(FONT_SIZE));
				lTwo.setPadding(new Insets(10));
				TextField filePathTwo = new TextField();
				filePathTwo.setMinSize(200, 25);
				Button btnChooseTwo = new Button("...");
				btnChooseTwo.setPrefSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
				btnChooseTwo.setPadding(new Insets(10));
				btnChooseTwo.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						filePathTwo.setText(pressBtnChoose());
					}

				});
				rowTwoMod.getChildren().addAll(lTwo, filePathTwo, btnChooseTwo);
				HBox btnRowMod = new HBox(30);
				btnRowMod.setAlignment(Pos.CENTER);
				Button btnCompareM = new Button("Сравнить");
				btnCompareM.setFont(new Font(FONT_SIZE));
				btnCompareM.setMinSize(50, 20);
				btnCompareM.setAlignment(Pos.CENTER);
				btnCompareM.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						checkAndCompare(filePathOne, filePathTwo);
					}

				});

				Button btnClearM = new Button("Очистить");
				btnClearM.setFont(new Font(FONT_SIZE));
				btnClearM.setMinSize(50, 25);
				btnClearM.setAlignment(Pos.CENTER);
				btnClearM.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						filePathOne.setText("");
						filePathTwo.setText("");
					}

				});

				btnRowMod.getChildren().addAll(btnCompareM, btnClearM);
				mBox.getChildren().addAll(rowOneMod, rowTwoMod, btnRowMod);
				Scene sceneTwo = new Scene(mBox);
				sceneTwo.setOnKeyPressed(e -> {
					if (e.getCode() == KeyCode.ENTER) {
						checkAndCompare(filePathOne, filePathTwo);
					}
				});
				Stage newWindow = new Stage();
				newWindow.setTitle("Сравнить");
				newWindow.setScene(sceneTwo);
				newWindow.initModality(Modality.WINDOW_MODAL);
				newWindow.initOwner(primaryStage);	
				newWindow.show();

			}
		});

		Button btnStart = new Button("START");
		btnStart.setFont(new Font(20));
		btnStart.setMinSize(150, 50);
		btnStart.setAlignment(Pos.CENTER);
		Button btnClear = new Button("Очистить");
		btnClear.setFont(new Font(FONT_SIZE));
		btnClear.setMinSize(50, 25);
		btnClear.setAlignment(Pos.CENTER);
		btnClear.setOnAction(event -> {
			filePathField.setText("");
			projectName.setText("");
		});
		btnStart.setOnAction(event -> {
			checkAndStart(filePathField, projectName, coatingCheck);
		});
		btnRow.getChildren().addAll(btnCompare, btnStart, btnClear);
		mainBox.getChildren().addAll(logo, rowOne, rowTwo, btnRow);
		Scene scene = new Scene(mainBox);
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				checkAndStart(filePathField, projectName, coatingCheck);
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static String getVegsion() {
		return VERSION;
	}

	public String pressBtnChoose() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("c:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("XLSX Files", "*.xlsx"));
		File selectedFile = fc.showOpenDialog(null);
		String filePath = "";
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
		}
		return filePath;
	}

	public static String filePathMaker(String filePath, String projectName) {
		int index = filePath.lastIndexOf("\\");
		StringBuilder sb = new StringBuilder();
		sb.append(filePath.substring(0, index));
		sb.append("\\Перечень деталей " + projectName + ".xlsx");
		return sb.toString();
	}

	public static String filePathMakerRal(String filePath) {
		int n = filePath.indexOf(".xlsx");
		StringBuilder sb = new StringBuilder();
		sb.append(filePath.substring(0, n));
		sb.append(" с покраской.xlsx");
		return sb.toString();
	}

	public static void logFileWriter(String projectName, String strRead, String strSort, String strWrite, long time,
			String version) throws Exception, UnknownHostException {
		File logFile = new File("LogFile.txt");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss / dd.MM.yyyy");
		String logLine = sdf.format(new Date());
		try {
			logLine += " V" + version + " " + InetAddress.getLocalHost().getHostName() + " " + projectName + " "
					+ strRead + " " + strSort + " " + strWrite + " " + time + "ms";
		} catch (Exception e) {
			throw e;
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
			bw.write(logLine + System.lineSeparator());
		} catch (Exception e) {
			throw e;
		}
	}

	public void startBtn(String inputFilePath, String projectName, boolean checkBox) {
		long tstart = System.currentTimeMillis();
		String outputFilePath = filePathMaker(inputFilePath, projectName);
		classes.ReadExcelFile ref = new classes.ReadExcelFile();
		List<classes.Part> list = new ArrayList<classes.Part>();
		String strRead = "";
		String strSort = "";
		String strWrite = "";
		try {
			list = ref.readXLSX(inputFilePath);
		} catch (Exception e) {
			ErrContoller.errWindow(strRead = "Read: " + e.toString());
		}
		try {
			classes.SortPartList.removeStdParts(list);
			classes.SortPartList.removeVnsItem(list);
			classes.SortPartList.removeStupidItem(list);
			classes.SortPartList.fillMaterial(list);
			classes.SortPartList.delThickness(list);
			classes.SortPartList.makeDesignationBetter(list);
		} catch (Exception e1) {
			ErrContoller.errWindow(strSort = "Sort: " + e1.toString());
		}

		if (checkBox) {
			File fileRal = new File(filePathMakerRal(outputFilePath));
			if (fileRal.exists()) {
				Alert saveAlert = new Alert(AlertType.CONFIRMATION);
				saveAlert.setTitle("Сохранение...");
				saveAlert.setHeaderText("Файл с таким именем существует.");
				saveAlert.setContentText("Перезаписать?");
				Optional<ButtonType> result = saveAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					classes.WriteExcelFileRal wef = new classes.WriteExcelFileRal(projectName);
					try {
						wef.saveXLSX(filePathMakerRal(outputFilePath), list);
						DoneController.doneWindow();
					} catch (Exception e) {
						e.printStackTrace();
						ErrContoller.errWindow(strWrite = "Write: " + e.toString());
					}
				}
			} else {
				classes.WriteExcelFileRal wef = new classes.WriteExcelFileRal(projectName);
				try {
					wef.saveXLSX(filePathMakerRal(outputFilePath), list);
					DoneController.doneWindow();
				} catch (Exception e) {
					e.printStackTrace();
					ErrContoller.errWindow(strWrite = "Write: " + e.toString());
				}
			}

		} else {
			File file = new File(outputFilePath);
			if (file.exists()) {
				Alert saveAlert = new Alert(AlertType.CONFIRMATION);
				saveAlert.setTitle("Сохранение...");
				saveAlert.setHeaderText("Файл с таким именем существует.");
				saveAlert.setContentText("Перезаписать?");
				Optional<ButtonType> result = saveAlert.showAndWait();
				if (result.get() == ButtonType.OK) {
					classes.WriteExcelFile wef = new classes.WriteExcelFile(projectName);
					try {
						wef.saveXLSX(outputFilePath, list);
						DoneController.doneWindow();
					} catch (Exception e) {
						e.printStackTrace();
						ErrContoller.errWindow(strWrite = "Write: " + e.toString());
					}
				}
			} else {
				classes.WriteExcelFile wef = new classes.WriteExcelFile(projectName);
				try {
					wef.saveXLSX(outputFilePath, list);
					DoneController.doneWindow();
				} catch (Exception e) {
					e.printStackTrace();
					ErrContoller.errWindow(strWrite = "Write: " + e.toString());
				}
			}

		}
		long tend = System.currentTimeMillis();
		try {
			logFileWriter(projectName, strRead, strSort, strWrite, (tend - tstart), Main.getVegsion());
		} catch (Exception e) {
			ErrContoller.errWindow(strWrite = "Write log: " + e.toString());

		}
	}

	public void compareBtn(String filePathOne, String filePathTwo) {
		XlsxReader reader = new XlsxReader();
		List<PartShort> listOne = new ArrayList<>();
		List<PartShort> listTwo = new ArrayList<>();
		try {
			listOne = reader.readXlsx(filePathOne);
			listTwo = reader.readXlsx(filePathTwo);
		} catch (IOException e) {
			ErrContoller.errWindow("Read: " + e.toString());
		}
		List<List<PartShort>> res = new ArrayList<>();
		if (listOne.size() != 0 && listTwo.size() != 0) {
			CompareList com = new CompareList();
			res = com.compareLists(listOne, listTwo);
		}
		listOne = res.get(0);
		listTwo = res.get(1);
		XlsxMarker marker = new XlsxMarker();
		if (listOne.size() != 0) {
			try {
				marker.mark(filePathOne, listOne);
				DoneController.doneWindowCompareMsg(filePathOne);
			} catch (IOException e) {
				e.printStackTrace();
				ErrContoller.errWindow("Write: " + e.toString());
			}
		}
		if (listTwo.size() != 0) {
			try {
				marker.mark(filePathTwo, listTwo);
				DoneController.doneWindowCompareMsg(filePathTwo);
			} catch (IOException e) {
				e.printStackTrace();
				ErrContoller.errWindow("Write: " + e.toString());
			}
		}
		if (listOne.size() == 0 && listTwo.size() == 0) {
			DoneController.doneWindow();
		}
	}
	
	public void checkAndStart(TextField filePathField, TextField projectName, CheckBox coatingCheck) {
		String inputFilePath = "";
		if (filePathField.getText().trim() == null || filePathField.getText().isEmpty()) {
			do {
				inputFilePath = ErrContoller.errWindowFilePath();
				filePathField.setText(inputFilePath);
			} while (inputFilePath == null || inputFilePath.isEmpty());
		} else {
			inputFilePath = filePathField.getText().trim().toLowerCase();
		}
		if (projectName.getText().trim() == null || projectName.getText().trim().isEmpty()) {
			do {
				projectName.setText(ErrContoller.errWindowProjectName());
			} while (projectName.getText().trim() == null || projectName.getText().trim().isEmpty());
		}
		startBtn(filePathField.getText(), projectName.getText(), coatingCheck.isSelected());
	}
	
	public void checkAndCompare (TextField filePathOne, TextField filePathTwo) {
		String inputFilePathOne = "";
		String inputFilePathTwo = "";
		if (filePathOne.getText().trim() == null || filePathOne.getText().isEmpty()) {
			do {
				inputFilePathOne = ErrContoller.errWindowFilePath();
				filePathOne.setText(inputFilePathOne);
			} while (inputFilePathOne == null || inputFilePathOne.isEmpty());

		} else {
			inputFilePathOne = filePathOne.getText().trim().toLowerCase();
		}
		if (filePathTwo.getText().trim() == null || filePathTwo.getText().isEmpty()) {
			do {
				inputFilePathTwo = ErrContoller.errWindowFilePath();
				filePathTwo.setText(inputFilePathTwo);
			} while (inputFilePathTwo == null || inputFilePathTwo.isEmpty());

		} else {
			inputFilePathTwo = filePathTwo.getText().trim().toLowerCase();
		}
		compareBtn(filePathOne.getText(), filePathTwo.getText());
	}

}
