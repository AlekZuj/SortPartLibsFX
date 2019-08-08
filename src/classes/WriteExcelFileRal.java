package classes;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelFileRal {
	protected static final int RAL_AND_TYPE_COLUMN = 5;
	protected static final int COATING_SQUARE_COLUMN = 6;
	protected static final int WIDTH_HIGHT_LENGTH_COLUMN = 7;
	protected static final int QUANTITY_COLUMN = 8;

	private String projectName;

	public WriteExcelFileRal(String projectName) {
		super();
		this.projectName = projectName;
	}

	public WriteExcelFileRal() {
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
			Cell cellNum = row.createCell(WriteExcelFile.NUMBER_COLUMN);
			Cell cellDes = row.createCell(WriteExcelFile.DESIGNATION_COLUMN);
			Cell cellName = row.createCell(WriteExcelFile.NAME_COLUMN);
			Cell cellMat = row.createCell(WriteExcelFile.MATERIAL_COLUMN);
			Cell cellThick = row.createCell(WriteExcelFile.THICKNESS_COLUMN);
			Cell cellRal = row.createCell(RAL_AND_TYPE_COLUMN);
			Cell cellCoatingSquare = row.createCell(COATING_SQUARE_COLUMN);
			Cell cellSize = row.createCell(WIDTH_HIGHT_LENGTH_COLUMN);
			Cell cellQua = row.createCell(QUANTITY_COLUMN);
			WriteExcelFile.makeRowStyle(wb, row, Color.WHITE, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
			cellNum.setCellValue(i + 1);
			if (partList.get(i).getDesignation() != null) {
				if (!partList.get(i).getDesignation().toLowerCase().contains("sldprt")) {
					cellDes.setCellValue(partList.get(i).getDesignation());
				} else {
					cellDes.setCellValue(
							partList.get(i).getDesignation() + " FILE NAME = " + partList.get(i).getSwFileName());
					WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
				}
				if (partList.get(i).getDesignation().isEmpty()) {
					WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
				}
			} else {
				cellDes.setCellValue(" FILE NAME = " + partList.get(i).getSwFileName());
				WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
			}
			if (partList.get(i).getName() != null) {
				cellName.setCellValue(partList.get(i).getName());
				if (partList.get(i).getName().isEmpty()) {
					WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
				}
			} else {
				WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
			}
			if (partList.get(i).getMaterial() != null) {
				if (partList.get(i).getThickness() < 0 && (partList.get(i).getMaterial().toLowerCase().contains("лист")
						|| (partList.get(i).getMaterial().toLowerCase().contains("рулон")
								&& !partList.get(i).getMaterial().toLowerCase().contains("az")))) {
					WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
				}
				cellMat.setCellValue(partList.get(i).getMaterial());
			} else {
				WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
			}
			if (partList.get(i).getThickness() < 0) {
				cellThick.setCellValue("");
			} else {
				cellThick.setCellValue(partList.get(i).getThickness());
			}
			if ((partList.get(i).getRal() == null || partList.get(i).getRal().isEmpty()
					|| partList.get(i).getCoatingType() == null || partList.get(i).getCoatingType().isEmpty())) {
				cellRal.setCellValue("");
			} else {
				cellRal.setCellValue(partList.get(i).getRal() + " / " + partList.get(i).getCoatingType());
			}
			if (partList.get(i).getCoatingSquare() < 0) {
				cellCoatingSquare.setCellValue("");
			} else {
				cellCoatingSquare.setCellValue(partList.get(i).getCoatingSquare());
			}
			if (partList.get(i).getLength() < 0 || partList.get(i).getWidth() < 0 || partList.get(i).getHeight() < 0) {
				cellSize.setCellValue("");
			} else {
				cellSize.setCellValue(partList.get(i).getLength() + " x " + partList.get(i).getWidth() + " x "
						+ partList.get(i).getHeight());
			}
			cellQua.setCellValue(partList.get(i).getQuantity());
			if (partList.get(i).getQuantity() <= 0) {
				WriteExcelFile.makeRowStyle(wb, row, Color.RED, false, WriteExcelFile.MAIN_TEXT_SIZE, true);
			}
			if (partList.get(i).getDesignation() != null) {
				if (!partList.get(i).getDesignation().isEmpty()) {
					for (int j = 0; j < i; j++) {
						if (partList.get(i).getDesignation().equalsIgnoreCase(partList.get(j).getDesignation())) {
							WriteExcelFile.makeRowStyle(wb, row, Color.CYAN, false, WriteExcelFile.MAIN_TEXT_SIZE,
									true);
							Row sameRow = sheet.getRow(j + 2);
							WriteExcelFile.makeRowStyle(wb, sameRow, Color.CYAN, false, WriteExcelFile.MAIN_TEXT_SIZE,
									true);
						}
					}
				}
			}
		}
		WriteExcelFile.autoSizeColumns(wb);
		WriteExcelFile.makeAssembleList(wb, QUANTITY_COLUMN);
		WriteExcelFile.makeLegend(wb);

		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
	}

	private Workbook makeTitle(Workbook wb) {
		Sheet sheet = wb.getSheetAt(0);
		Row rowTitle = sheet.createRow(0);
		Cell cellTitle = rowTitle.createCell(0);
		cellTitle.setCellValue("Перечень деталей " + projectName);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
		Row rowTitleTwo = sheet.createRow(1);
		Cell cellTitleNum = rowTitleTwo.createCell(WriteExcelFile.NUMBER_COLUMN);
		cellTitleNum.setCellValue("Позиция");
		Cell cellTitleDes = rowTitleTwo.createCell(WriteExcelFile.DESIGNATION_COLUMN);
		cellTitleDes.setCellValue("Обозначение");
		Cell cellTitleName = rowTitleTwo.createCell(WriteExcelFile.NAME_COLUMN);
		cellTitleName.setCellValue("Наименование");
		Cell cellTitleMat = rowTitleTwo.createCell(WriteExcelFile.MATERIAL_COLUMN);
		cellTitleMat.setCellValue("Материал");
		Cell cellTitleThick = rowTitleTwo.createCell(WriteExcelFile.THICKNESS_COLUMN);
		cellTitleThick.setCellValue("Толщина листового металла");
		Cell cellTitleRal = rowTitleTwo.createCell(RAL_AND_TYPE_COLUMN);
		cellTitleRal.setCellValue("RAL / Тип покрытия");
		Cell cellTitleCoatingSquare = rowTitleTwo.createCell(COATING_SQUARE_COLUMN);
		cellTitleCoatingSquare.setCellValue("Площадь покрытия");
		Cell cellTitleSize = rowTitleTwo.createCell(WIDTH_HIGHT_LENGTH_COLUMN);
		cellTitleSize.setCellValue("Размеры ДхШхВ");
		Cell cellTitleQau = rowTitleTwo.createCell(QUANTITY_COLUMN);
		cellTitleQau.setCellValue("Кол-во");
		WriteExcelFile.makeRowStyle(wb, rowTitle, Color.WHITE, true, WriteExcelFile.HEAD_TEXT_SIZE, false);
		WriteExcelFile.makeRowStyle(wb, rowTitleTwo, Color.LIGHT_GRAY, true, WriteExcelFile.MAIN_TEXT_SIZE, false);
		return wb;
	}
}
