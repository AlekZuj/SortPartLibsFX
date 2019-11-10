package compareClasses;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsxMarker {
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public XlsxMarker(String filePath) {
		this.filePath = filePath;
	}

	public XlsxMarker() {
	}

	public void mark(String filePath, List<PartShort> list) throws IOException {
		File file = new File(filePath);
		Workbook wb = new XSSFWorkbook(new FileInputStream(file));
		Sheet sheet = wb.getSheetAt(0);
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.getRow(list.get(i).getLine());
			makeRowStyle(wb, row);
		}
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
	}

	public static void makeRowStyle(Workbook wb, Row row) {
		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
		cellStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.RED));
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setFontName("Times New Roman");
		font.setBold(false);
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		for (int j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = row.getCell(j);
			if (cell != null) {
				cell.setCellStyle(cellStyle);
			}

		}

	}
}
