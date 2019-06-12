

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainController {

	@FXML
	private Button fileChoseBtn;

	@FXML
	private Button startBtn;

	@FXML
	private Button clearBtn;

	@FXML
	private CheckBox coatingChBox;

	@FXML
	private TextField filePathField;

	@FXML
	private TextField projectNameField;

	@FXML
	public void pressFileChoseBtn(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("c:\\"));
		fc.getExtensionFilters().addAll(new ExtensionFilter("XLSX Files", "*.xlsx"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			filePathField.setText(selectedFile.getAbsolutePath());
		}
	}

	@FXML
	public void clear(ActionEvent event) {
		filePathField.setText("");
		projectNameField.setText("");
	}

	@FXML
	public void start(ActionEvent event) {
		String inputFilePath = "";
		if (filePathField.getText().trim() == null || filePathField.getText().trim().isEmpty()) {
			do {
			inputFilePath = ErrContoller.errWindowFilePath();
			} while (inputFilePath==null || inputFilePath.isEmpty());
		} else {
			inputFilePath = filePathField.getText().trim().toLowerCase();
		}
		String projectName = "";
		if (projectNameField.getText().trim() == null || projectNameField.getText().trim().isEmpty()) {
			do {
			projectName = ErrContoller.errWindowProjectName();
			} while (projectName==null || projectName.isEmpty());
		} else {
			projectName = projectNameField.getText().trim();
		}
		long tstart = System.currentTimeMillis();
		String outputFilePath = filePathMaker(inputFilePath, projectName);
		classes.ReadExcelFile ref = new classes.ReadExcelFile();
		List<classes.Part> list = new ArrayList<classes.Part>();
		String strRead = "";
		String strSort = "";
		String strWrite = "";
		try {
			list = ref.readXLSX(inputFilePath);
		} catch (NumberFormatException | IOException e) {
			ErrContoller.errWindow(strRead = "Read: " + e.toString());
		}
		try {
			classes.SortPartList.removeStdParts(list);
			classes.SortPartList.removeVnsItem(list);
			classes.SortPartList.removeStupidItem(list);
			classes.SortPartList.fillMaterial(list);
			classes.SortPartList.delThickness(list);
			classes.SortPartList.makeDesignationBetter(list);
		} catch (IOException e1) {
			ErrContoller.errWindow(strSort = "Sort: " + e1.toString());
		}

		if (coatingChBox.isSelected()) {
			classes.WriteExcelFileRal wef = new classes.WriteExcelFileRal(projectName);
			try {
				wef.saveXLSX(filePathMakerRal(outputFilePath), list);
			} catch (IOException e) {
				ErrContoller.errWindow(strWrite = "Write: " + e.toString());
			}
		} else {
			classes.WriteExcelFile wef = new classes.WriteExcelFile(projectName);
			try {
				wef.saveXLSX(outputFilePath, list);
			} catch (IOException e) {
				ErrContoller.errWindow(strWrite = "Write: " + e.toString());
			}
		}
		long tend = System.currentTimeMillis();
		try {
			logFileWriter(projectName, strRead, strSort, strWrite, (tend - tstart), Main.getVegsion());
		} catch (IOException e) {
			ErrContoller.errWindow(strWrite = "Write log: " + e.toString());
			
		}
		DoneController.doneWindow();
	}

	public static void logFileWriter(String projectName, String strRead, String strSort, String strWrite, long time,
			String version) throws IOException, UnknownHostException {
		File logFile = new File("LogFile.txt");
		List<String> log = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				log.add(line);
			}
		} catch (IOException e) {
			throw e;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss / dd.MM.yyyy");
		try {
			log.add(sdf.format(new Date()) + " V" + version + " " + InetAddress.getLocalHost().getHostName() + " "
					+ projectName + " " + strRead + " " + strSort + " " + strWrite + " " + time + "ms");
		} catch (UnknownHostException e) {
			throw e;
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile))) {
			for (int i = 0; i < log.size(); i++) {
				bw.write(log.get(i) + System.lineSeparator());
			}
		} catch (Exception e) {
			throw e;
		}
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

}
