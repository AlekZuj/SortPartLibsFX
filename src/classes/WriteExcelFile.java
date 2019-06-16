package classes;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.javafx.collections.SetAdapterChange;

public class WriteExcelFile {
	protected static final int NUMBER_COLUMN = 0;
	protected static final int DESIGNATION_COLUMN = 1;
	protected static final int NAME_COLUMN = 2;
	protected static final int MATERIAL_COLUMN = 3;
	protected static final int THICKNESS_COLUMN = 4;
	protected static final int QUANTITY_COLUMN = 5;

	private String projectName;

	public WriteExcelFile(String projectName) {
		super();
		this.projectName = projectName;
	}

	public WriteExcelFile() {
		super();
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void saveXLSX(String filePath, List<Part> partList) throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(projectName);
		makeTitle(wb);
		for (int i = 0; i < partList.size(); i++) {
			Row row = sheet.createRow(i + 2);
			Cell cellNum = row.createCell(NUMBER_COLUMN);
			Cell cellDes = row.createCell(DESIGNATION_COLUMN);
			Cell cellName = row.createCell(NAME_COLUMN);
			Cell cellMat = row.createCell(MATERIAL_COLUMN);
			Cell cellThick = row.createCell(THICKNESS_COLUMN);
			Cell cellQua = row.createCell(QUANTITY_COLUMN);
			makeRowStyle(wb, row, Color.WHITE, false, 14, true);
			cellNum.setCellValue(i + 1);
			cellDes.setCellValue(partList.get(i).getDesignation());
			if (partList.get(i).getDesignation() == null || partList.get(i).getDesignation().isEmpty()) {
				makeRowStyle(wb, row, Color.RED, false, 14, true);
			}
			cellName.setCellValue(partList.get(i).getName());
			if (partList.get(i).getName().isEmpty()) {
				makeRowStyle(wb, row, Color.RED, false, 14, true);
			}
			cellMat.setCellValue(partList.get(i).getMaterial());
			if (partList.get(i).getMaterial().isEmpty()) {
				makeRowStyle(wb, row, Color.RED, false, 14, true);
			}
			if (partList.get(i).getThickness() < 0) {
				cellThick.setCellValue("");
			} else {
				cellThick.setCellValue(partList.get(i).getThickness());
			}
			if (partList.get(i).getThickness() < 0 && (partList.get(i).getMaterial().toLowerCase().contains("лист")
					|| (partList.get(i).getMaterial().toLowerCase().contains("рулон")
							&& !partList.get(i).getMaterial().toLowerCase().contains("az")))) {
				makeRowStyle(wb, row, Color.RED, false, 14, true);
			}
			cellQua.setCellValue(partList.get(i).getQuantity());
			if (partList.get(i).getQuantity() <= 0) {
				makeRowStyle(wb, row, Color.RED, false, 14, true);
			}
		}
		autoSizeColumns(wb);
		makeAssembleList(wb, QUANTITY_COLUMN);
		makeLegend(wb);
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
	}

	public static void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(1);
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	private Workbook makeTitle(Workbook wb) {
		Sheet sheet = wb.getSheetAt(0);
		Row rowTitle = sheet.createRow(0);
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue("Перечень деталей " + projectName);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Row rowTitleTwo = sheet.createRow(1);
		Cell cellTitleNum = rowTitleTwo.createCell(NUMBER_COLUMN);
		cellTitleNum.setCellValue("Позиция");
		Cell cellTitleDes = rowTitleTwo.createCell(DESIGNATION_COLUMN);
		cellTitleDes.setCellValue("Обозначение");
		Cell cellTitleName = rowTitleTwo.createCell(NAME_COLUMN);
		cellTitleName.setCellValue("Наименование");
		Cell cellTitleMat = rowTitleTwo.createCell(MATERIAL_COLUMN);
		cellTitleMat.setCellValue("Материал");
		Cell cellTitleThick = rowTitleTwo.createCell(THICKNESS_COLUMN);
		cellTitleThick.setCellValue("Толщина листового металла");
		Cell cellTitleQau = rowTitleTwo.createCell(QUANTITY_COLUMN);
		cellTitleQau.setCellValue("Кол-во");
		makeRowStyle(wb, rowTitle, Color.WHITE, true, 20, false);
		makeRowStyle(wb, rowTitleTwo, Color.LIGHT_GRAY, true, 14, false);
		return wb;
	}

	public static void makeRowStyle(Workbook wb, Row row, Color color, boolean fontBold, int fontSize, boolean wrapped) {
		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
		cellStyle.setFillForegroundColor(new XSSFColor(color));
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName("Times New Roman");
		font.setBold(fontBold);
		cellStyle.setFont(font);
		cellStyle.setWrapText(wrapped);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		for (int j = 0; j < row.getLastCellNum(); j++) {
			Cell cell = row.getCell(j);
			cell.setCellStyle(cellStyle);
		}

	}

	public static void makeAssembleList(Workbook wb, int lastCellNum) throws IOException {
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum() + 3;
		List<String> assemList = SortPartList.addLib("AssemblyList");
		for (int i = 0; i < assemList.size(); i++) {
			Row row = sheet.createRow(rowNum + i);
			for (int j = 0; j <= lastCellNum; j++) {
				Cell cell = row.createCell(j);
				if (j == NUMBER_COLUMN) {
					cell.setCellValue(rowNum + i);
				}
				if (j == DESIGNATION_COLUMN) {
					cell.setCellValue("НЕ ЗАБУДЬ!!!");
				}
				if (j == NAME_COLUMN) {
					cell.setCellValue(assemList.get(i));
				}
				makeRowStyle(wb, row, Color.YELLOW, true, 14, true);
			}
		}
	}

	public static void makeLegend(Workbook wb) {
		Sheet sheet = wb.getSheetAt(0);
		Row rowOne = sheet.createRow(sheet.getLastRowNum() + 2);
		XSSFCellStyle cellRed = (XSSFCellStyle) wb.createCellStyle();
		cellRed.setFillForegroundColor(new XSSFColor(Color.RED));
		cellRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 14);
		font.setFontName("Times New Roman");
		cellRed.setFont(font);
		cellRed.setAlignment(HorizontalAlignment.CENTER);
		Cell cellOne = rowOne.createCell(DESIGNATION_COLUMN);
		cellOne.setCellValue("КРАСНЫМ");
		Cell cellTwo = rowOne.createCell(DESIGNATION_COLUMN + 1);
		cellTwo.setCellValue("- отмечены строки в которых есть не заполненные ячейки!");
		Row rowTwo = sheet.createRow(sheet.getLastRowNum() + 1);
		Cell cellThree = rowTwo.createCell(DESIGNATION_COLUMN);
		cellThree.setCellValue(
				"P.S. Применение данного ПО не отменяет ошибок в заполнении свойств в SolidWorks. Поэтому ячейки остались пустыми и стали красными.");
		Row rowThree = sheet.createRow(sheet.getLastRowNum() + 1);
		Cell cellFour = rowThree.createCell(DESIGNATION_COLUMN);
		cellFour.setCellValue("НАСТОЯТЕЛЬНО РЕКОМЕНДУЮ выполнять проверку перечня.");
		cellOne.setCellStyle(cellRed);
	}

}
