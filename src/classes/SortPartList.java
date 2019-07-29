package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortPartList {
	protected static final String LIB_FILE_PATH = "\\\\filesrv\\KB\\10 Личные папки\\Зуев А.С\\SplfxDB";

	public static void removeStdParts(List<Part> partList) throws IOException, NullPointerException {
		setSectionPart(partList);
		List<String> stdList = addLib(LIB_FILE_PATH+"\\StdLib");
		for (int i = 0; i < partList.size(); i++) {
			for (int j = 0; j < stdList.size(); j++) {
				if (partList.get(i).getSection().contains(stdList.get(j)) || partList.get(i).getSection().isEmpty()) {
					partList.remove(i);
					i--;
					break;
				}
			}
		}

	}

	public static void removeVnsItem(List<Part> partList) throws IOException {
		List<String> itemList = addLib(LIB_FILE_PATH+"\\VnsItemLib");
		for (int i = 0; i < partList.size(); i++) {
			for (int j = 0; j < itemList.size(); j++) {
				if (partList.get(i).getDesignation() != null && (partList.get(i).getDesignation().toLowerCase()
						.contains(itemList.get(j).toLowerCase())
						|| partList.get(i).getSwFileName().toLowerCase().contains(itemList.get(j).toLowerCase()))) {
					partList.remove(i);
					i--;
					break;
				}
			}
		}
	}

	public static void removeStupidItem(List<Part> partList) throws IOException {
		List<String> itemList = addLib(LIB_FILE_PATH+"\\StupidItemLib");
		for (int i = 0; i < partList.size(); i++) {
			for (int j = 0; j < itemList.size(); j++) {
				if (partList.get(i).getName().toLowerCase().contains(itemList.get(j).toLowerCase())) {
					partList.remove(i);
					i--;
					break;
				}
			}
		}
	}

	public static List<String> addLib(String filePath) throws IOException {
		List<String> stdList = new ArrayList<String>();
		File file = new File(filePath);
		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String str;
				while ((str = br.readLine()) != null) {
					stdList.add(str);
				}
			} catch (IOException e) {
				throw e;
			}
		}
		for (int i = 0; i < stdList.size(); i++) {
			if (stdList.get(i).contains("***")) {
				stdList.remove(i);
			}
		}
		return stdList;
	}

	private static void setSectionPart(List<Part> partList) {
		for (int i = 0; i < partList.size(); i++) {
			if (partList.get(i).getDesignation() != null
					&& partList.get(i).getDesignation().toLowerCase().contains("внс")
					&& partList.get(i).getSection().isEmpty()) {
				partList.get(i).setSection("Детали");
			} else {
			}
		}
	}

	public static void delThickness(List<Part> partList) throws IOException {
		List<String> itemList = addLib("DelThicknessList");
		for (int i = 0; i < partList.size(); i++) {
			for (int j = 0; j < itemList.size(); j++) {
				if (partList.get(i).getMaterial().toLowerCase().contains(itemList.get(j).toLowerCase())) {
					partList.get(i).setThickness(-1);
				}
			}
		}
	}

	private static void delEnterAndIncorrectMaterial(List<Part> partList) {
		for (int i = 0; i < partList.size(); i++) {
			if (partList.get(i).getMaterial().contains("\n")) {
				partList.get(i).setMaterial(partList.get(i).getMaterial().replaceAll("\n", " ").trim());
			}
			if (partList.get(i).getMaterialFB().contains("\n")) {
				partList.get(i).setMaterialFB(partList.get(i).getMaterialFB().replaceAll("\n", " ").trim());
			}
			if (partList.get(i).getMaterialTable().contains("\n")) {
				partList.get(i).setMaterialTable(partList.get(i).getMaterialTable().replaceAll("\n", " ").trim());
			}
			if (partList.get(i).getMaterial().toLowerCase().contains("таблиц")) {
				partList.get(i).setMaterial("");
			}
			if (partList.get(i).getMaterialFB().toLowerCase().contains("таблиц")) {
				partList.get(i).setMaterialFB("");
			}
			if (partList.get(i).getMaterialTable().toLowerCase().contains("таблиц")) {
				partList.get(i).setMaterialTable("");
			}
			if (partList.get(i).getMaterial().toLowerCase().contains("не указан")) {
				partList.get(i).setMaterial("");
			}
			if (partList.get(i).getMaterialFB().toLowerCase().contains("не указан")) {
				partList.get(i).setMaterialFB("");
			}
			if (partList.get(i).getMaterialTable().toLowerCase().contains("не указан")) {
				partList.get(i).setMaterialTable("");
			}
			if (partList.get(i).getName().toLowerCase().contains("pp1381")) {
				partList.get(i).setMaterial("");
				partList.get(i).setMaterialFB("");
				partList.get(i).setMaterialTable("");
			}
		}
	}

	public static void fillMaterial(List<Part> partList) throws IOException {
		delEnterAndIncorrectMaterial(partList);
		for (int i = 0; i < partList.size(); i++) {
			if (partList.get(i).getMaterial().isEmpty() && !partList.get(i).getMaterialFB().isEmpty()) {
				if (partList.get(i).getMaterialFB().toLowerCase().contains("sw-file")) {
					String[] str = partList.get(i).getMaterialFB().trim().split("SW-File");
					partList.get(i).setMaterial(str[0]);
				} else {
					partList.get(i).setMaterial(partList.get(i).getMaterialFB());
				}
			}
			if (partList.get(i).getMaterial().isEmpty() && partList.get(i).getMaterialFB().isEmpty()
					&& !partList.get(i).getMaterialTable().isEmpty()) {
				if (partList.get(i).getMaterialTable().toLowerCase().contains("sw-file")) {
					String[] str = partList.get(i).getMaterialTable().trim().split("SW-File");
					partList.get(i).setMaterial(str[0]);
				} else {
					partList.get(i).setMaterial(partList.get(i).getMaterialTable());
				}
			}
			if (partList.get(i).getMaterial().isEmpty() && partList.get(i).getMaterialFB().isEmpty()
					&& partList.get(i).getMaterialTable().isEmpty()) {

			}
		}
		delWrongMaterial(partList);
		fillAllMaterials(partList);
	}

	private static void fillAllMaterials(List<Part> partList) {
		for (int i = 0; i < partList.size(); i++) {
			if (!partList.get(i).getMaterial().isEmpty() && partList.get(i).getMaterialFB().isEmpty()) {
				partList.get(i).setMaterialFB(partList.get(i).getMaterial());
			}
			if (!partList.get(i).getMaterial().isEmpty() && partList.get(i).getMaterialTable().isEmpty()) {
				partList.get(i).setMaterialTable(partList.get(i).getMaterial());
			}
		}
	}

	private static void delWrongMaterial(List<Part> partList) throws IOException {
		List<String> itemList = addLib(LIB_FILE_PATH+"\\WrongMaterialList");
		for (int i = 0; i < partList.size(); i++) {
			for (int j = 0; j < itemList.size(); j++) {
				if (partList.get(i).getMaterial().toLowerCase().contains(itemList.get(j).toLowerCase())) {
					partList.remove(i);
					i--;
					break;
				}
			}
		}
		for (int i = 0; i < partList.size(); i++) {
			if (!partList.get(i).getMaterial().isEmpty()) {
				if (partList.get(i).getMaterial().toLowerCase().contains("толщина")) {
					int n = partList.get(i).getMaterial().toLowerCase().indexOf("толщина");
					String str = partList.get(i).getMaterial().substring(0, n - 2);
					partList.get(i).setMaterial(str);
				}
			}
			if (!partList.get(i).getMaterial().isEmpty()) {
				if (partList.get(i).getMaterial().toLowerCase().contains("бт-пн")) {
					int n = partList.get(i).getMaterial().toLowerCase().indexOf("бт-пн");
					String str = partList.get(i).getMaterial().substring(0, n - 1);
					partList.get(i).setMaterial(str);
				}
			}
		}
	}

	public static void makeDesignationBetter(List<Part> partList) {
		for (int i = 0; i < partList.size(); i++) {
			if (partList.get(i).getDesignation() != null && partList.get(i).getDesignation().contains("/")) {
				String[] strArr = partList.get(i).getDesignation().split("/");
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < strArr.length; j++) {
					if (j != strArr.length - 1) {
						sb.append(strArr[j] + ".");
					} else {
						sb.append(strArr[j]);
					}
				}
				partList.get(i).setDesignation(sb.toString());
			}
		}
	}

}
