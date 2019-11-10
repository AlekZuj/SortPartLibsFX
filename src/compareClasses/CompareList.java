package compareClasses;
import java.util.ArrayList;
import java.util.List;

import classes.Part;

public class CompareList {
	private List<PartShort> listOne;
	private List<PartShort> listTwo;

	public CompareList(List<PartShort> listOne, List<PartShort> listTwo) {
		this.listOne = listOne;
		this.listTwo = listTwo;
	}

	public CompareList() {
	}

	public List<PartShort> getListOne() {
		return listOne;
	}

	public void setListOne(List<PartShort> listOne) {
		this.listOne = listOne;
	}

	public List<PartShort> getListTwo() {
		return listTwo;
	}

	public void setListTwo(List<PartShort> listTwo) {
		this.listTwo = listTwo;
	}

	public List<List<PartShort>> compareLists(List<PartShort> listOne, List<PartShort> listTwo) {
		List<List<PartShort>> resultList = new ArrayList<>();
		cleanList(listOne);
		cleanList(listTwo);
		for (int i = 0; i < listOne.size(); i++) {
			for (int j = 0; j < listTwo.size(); j++) {
				if (listOne.get(i).getName().equals(listTwo.get(j).getName())) {
					listOne.remove(i);
					listTwo.remove(j);
					i--;
					j--;
					break;
				}
			}
		}
		resultList.add(listOne);
		resultList.add(listTwo);
		return resultList;
	}

	public List<PartShort> cleanList(List<PartShort> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName() == null) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}
}
