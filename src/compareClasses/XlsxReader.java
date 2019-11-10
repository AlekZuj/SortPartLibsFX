package compareClasses;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader {
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public XlsxReader(String filePath) {
		this.filePath = filePath;
	}

	public XlsxReader() {
	}

	public int[] firstRowFinder(File file) throws IOException {
		int[] arr = { -1, -1 };
		try (Workbook wb = new XSSFWorkbook(new FileInputStream(file))) {
			int lines = wb.getSheetAt(0).getLastRowNum();
			for (int i = 0; i <= lines; i++) {
				if (wb.getSheetAt(0).getRow(i) != null) {
					int columns = wb.getSheetAt(0).getRow(i).getLastCellNum();
					for (int j = 0; j < columns; j++) {
						if (wb.getSheetAt(0).getRow(i).getCell(j) != null) {
							switch (wb.getSheetAt(0).getRow(i).getCell(j).getCellType()) {
							case STRING:
								if (wb.getSheetAt(0).getRow(i).getCell(j).getStringCellValue() != null
										|| !wb.getSheetAt(0).getRow(i).getCell(j).getStringCellValue().isEmpty()) {
									if (wb.getSheetAt(0).getRow(i).getCell(j).getStringCellValue()
											.contains("Обознач")) {
										arr[0] = i;
										arr[1] = j;
									}
								}
								break;
							default:
								break;
							}
						}
					}
				} else {
					continue;
				}
			}
		} catch (IOException e) {
			throw e;
		}
		return arr;
	}

	public List<PartShort> readXlsx(String filePath) throws IOException {
		int firstRow = -1;
		int designationColumn = -1;
		File file = new File(filePath);
		List<PartShort> list = new ArrayList<>();
		if (file.exists()) {
			int[] arrResult = firstRowFinder(file);
			firstRow = arrResult[0];
			designationColumn = arrResult[1];
		}
		if (firstRow >= 0 || designationColumn >= 0) {
			try (Workbook wb = new XSSFWorkbook(new FileInputStream(file))) {
				for (int i = firstRow + 1; i <= wb.getSheetAt(0).getLastRowNum(); i++) {
					PartShort part = new PartShort();
					if (wb.getSheetAt(0).getRow(i) != null) {
						switch (wb.getSheetAt(0).getRow(i).getCell(designationColumn).getCellType()) {
						case STRING:
							part.setName(wb.getSheetAt(0).getRow(i).getCell(designationColumn).getStringCellValue());
							break;
						case NUMERIC:
							part.setName(
									wb.getSheetAt(0).getRow(i).getCell(designationColumn).getNumericCellValue() + "");
							break;
						default:
							break;
						}
						part.setLine(i);
						list.add(part);
					} else {
						continue;
					}
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return list;
	}
}
