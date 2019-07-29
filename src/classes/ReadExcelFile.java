package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {
	protected static final int DESIGNATION_COLUMN = 1;
	protected static final int NAME_COLUMN = 2;
	protected static final int MATERIAL_COLUMN = 3;
	protected static final int MATERIALFB_COLUMN = 4;
	protected static final int MATERIALTABLE_COLUMN = 5;
	protected static final int THICKNESS_COLUMN = 6;
	protected static final int RAL_COLUMN = 7;
	protected static final int COATINGTYPE_COLUMN = 8;
	protected static final int COATINGSQUARE_COLUMN = 9;
	protected static final int WIDTH_COLUMN = 10;
	protected static final int HIGHT_COLUMN = 11;
	protected static final int LENGTH_COLUMN = 12;
	protected static final int SWFILENAME_COLUMN = 13;
	protected static final int SECTION_COLUMN = 14;
	protected static final int QUANTITY_COLUMN = 15;

	private String filePath;

	public ReadExcelFile(String filePath) {
		super();
		this.filePath = filePath;
	}

	public ReadExcelFile() {
		super();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<Part> readXLSX(String filePath)
			throws IOException, NumberFormatException, NullPointerException, ParseException {
		File file = new File(filePath);
		List<Part> partList = new ArrayList<Part>();
		int lines = 0;
		if (file.exists()) {
			try (Workbook wb = new XSSFWorkbook(new FileInputStream(file))) {
				lines = wb.getSheetAt(0).getLastRowNum();
				for (int i = 1; i <= lines; i++) {
					Part part = new Part();
					// Designation
					switch (wb.getSheetAt(0).getRow(i).getCell(DESIGNATION_COLUMN).getCellType()) {
					case NUMERIC:
						if (isValidDate(
								"" + wb.getSheetAt(0).getRow(i).getCell(DESIGNATION_COLUMN).getNumericCellValue())) {
							part.setDesignation(dateToString(
									wb.getSheetAt(0).getRow(i).getCell(DESIGNATION_COLUMN).getDateCellValue()));
							break;
						} else {
							part.setDesignation(
									"" + wb.getSheetAt(0).getRow(i).getCell(DESIGNATION_COLUMN).getNumericCellValue());
							break;
						}
					case STRING:
						part.setDesignation(
								wb.getSheetAt(0).getRow(i).getCell(DESIGNATION_COLUMN).getStringCellValue());
						break;
					default:
						break;
					}
					// Name
					part.setName(wb.getSheetAt(0).getRow(i).getCell(NAME_COLUMN).getStringCellValue());
					// Material
					part.setMaterial(wb.getSheetAt(0).getRow(i).getCell(MATERIAL_COLUMN).getStringCellValue());
					// Material_FB
					part.setMaterialFB(wb.getSheetAt(0).getRow(i).getCell(MATERIALFB_COLUMN).getStringCellValue());
					// Material_Table
					part.setMaterialTable(
							wb.getSheetAt(0).getRow(i).getCell(MATERIALTABLE_COLUMN).getStringCellValue());
					// Thickness
					switch (wb.getSheetAt(0).getRow(i).getCell(THICKNESS_COLUMN).getCellType()) {
					case NUMERIC:
						part.setThickness(
								makeDouble(wb.getSheetAt(0).getRow(i).getCell(THICKNESS_COLUMN).getNumericCellValue()));
						break;
					case STRING:
						if (wb.getSheetAt(0).getRow(i).getCell(THICKNESS_COLUMN).getStringCellValue().isEmpty()) {
							if (wb.getSheetAt(0).getRow(i).getCell(THICKNESS_COLUMN).getStringCellValue()
									.contains("sld")) {
								part.setThickness(-1);
								break;
							} else {
								part.setThickness(makeDouble(
										wb.getSheetAt(0).getRow(i).getCell(THICKNESS_COLUMN).getStringCellValue()));
								break;
							}
						} else {
							part.setThickness(-1);
							break;
						}
					default:
						part.setThickness(-1);
						break;
					}
					// Ral
					part.setRal(wb.getSheetAt(0).getRow(i).getCell(RAL_COLUMN).getStringCellValue());
					// Coating Type
					part.setCoatingType(wb.getSheetAt(0).getRow(i).getCell(COATINGTYPE_COLUMN).getStringCellValue());
					// Coating Square
					switch (wb.getSheetAt(0).getRow(i).getCell(COATINGSQUARE_COLUMN).getCellType()) {
					case NUMERIC:
						part.setCoatingSquare(
								wb.getSheetAt(0).getRow(i).getCell(COATINGSQUARE_COLUMN).getNumericCellValue());
						break;
					case STRING:
						part.setCoatingSquare(Double.parseDouble(
								wb.getSheetAt(0).getRow(i).getCell(COATINGSQUARE_COLUMN).getStringCellValue()));
						break;
					default:
						part.setCoatingSquare(-1);
						break;
					}
					// Width
					switch (wb.getSheetAt(0).getRow(i).getCell(WIDTH_COLUMN).getCellType()) {
					case NUMERIC:
						part.setWidth(wb.getSheetAt(0).getRow(i).getCell(WIDTH_COLUMN).getNumericCellValue());
						break;
					case STRING:
						String str = wb.getSheetAt(0).getRow(i).getCell(WIDTH_COLUMN).getStringCellValue();
						part.setWidth(Double.parseDouble(str.trim().replaceAll(String.valueOf((char) 160), "")));
						break;
					default:
						part.setWidth(-1);
						break;
					}
					// Hight
					switch (wb.getSheetAt(0).getRow(i).getCell(HIGHT_COLUMN).getCellType()) {
					case NUMERIC:
						part.setHeight(wb.getSheetAt(0).getRow(i).getCell(HIGHT_COLUMN).getNumericCellValue());
						break;
					case STRING:
						String str = wb.getSheetAt(0).getRow(i).getCell(HIGHT_COLUMN).getStringCellValue();
						part.setHeight(Double.parseDouble(str.trim().replaceAll(String.valueOf((char) 160), "")));
						break;
					default:
						part.setHeight(-1);
						break;
					}
					// Length
					switch (wb.getSheetAt(0).getRow(i).getCell(LENGTH_COLUMN).getCellType()) {
					case NUMERIC:
						part.setLength(wb.getSheetAt(0).getRow(i).getCell(LENGTH_COLUMN).getNumericCellValue());
						break;
					case STRING:
						String str = wb.getSheetAt(0).getRow(i).getCell(LENGTH_COLUMN).getStringCellValue();
						part.setLength(Double.parseDouble(str.trim().replaceAll(String.valueOf((char) 160), "")));
						break;
					default:
						part.setLength(-1);
						break;
					}
					// SwFileName
					switch (wb.getSheetAt(0).getRow(i).getCell(SWFILENAME_COLUMN).getCellType()) {
					case NUMERIC:
						if (isValidDate(
								"" + wb.getSheetAt(0).getRow(i).getCell(SWFILENAME_COLUMN).getNumericCellValue())) {
							part.setSwFileName(dateToString(
									wb.getSheetAt(0).getRow(i).getCell(SWFILENAME_COLUMN).getDateCellValue()));
							break;
						} else {
							part.setSwFileName(
									"" + wb.getSheetAt(0).getRow(i).getCell(SWFILENAME_COLUMN).getNumericCellValue());
							break;
						}
					case STRING:
						part.setSwFileName(wb.getSheetAt(0).getRow(i).getCell(SWFILENAME_COLUMN).getStringCellValue());
						break;
					default:
						break;
					}
					// Section
					part.setSection(wb.getSheetAt(0).getRow(i).getCell(SECTION_COLUMN).getStringCellValue());
					// Quantity
					switch (wb.getSheetAt(0).getRow(i).getCell(QUANTITY_COLUMN).getCellType()) {
					case NUMERIC:
						part.setQuantity(
								(int) wb.getSheetAt(0).getRow(i).getCell(QUANTITY_COLUMN).getNumericCellValue());
						break;
					case STRING:
						part.setQuantity(Double
								.parseDouble(wb.getSheetAt(0).getRow(i).getCell(QUANTITY_COLUMN).getStringCellValue()));
						break;
					default:
						part.setQuantity(-1);
						break;
					}
					partList.add(part);
				}
			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new IOException();
		}

		return partList;
	}

	protected double makeDouble(double number) {
		String str = "" + number;
		number = Double.parseDouble(str.trim().replace(',', '.'));
		return number;
	}

	protected double makeDouble(String str) {
		double number = Double.parseDouble(str.trim().replace(',', '.'));
		return number;
	}

	protected boolean isValidDate(String dateString) {
		SimpleDateFormat dfmy = new SimpleDateFormat("M.y");
		try {
			dfmy.parse(dateString);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	protected String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
		SimpleDateFormat dfMonth = new SimpleDateFormat("MM");
		SimpleDateFormat dfDay = new SimpleDateFormat("d");
		SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");
		String str = "_";
		if (!dfYear.format(date).equals("2019")) {
			str += df.format(date);
		} else {
			str += dfMonth.format(date) + "-" + dfDay.format(date);
		}
		return str;
	}
}