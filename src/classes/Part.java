package classes;

public class Part {
	private String designation;
	private String name;
	private String material;
	private String materialFB;
	private String materialTable;
	private double thickness;
	private String ral;
	private String coatingType;
	private double coatingSquare;
	private double width;
	private double height;
	private double length;
	private String swFileName;
	private String section;
	private double quantity;

	public Part(String designation, String name, String material, String materialFB, String materialTable,
			double thickness, String ral, String coatingType, double coatingSquare, double width, double height,
			double length, String swFileName, String section, double quantity) {
		super();
		this.designation = designation;
		this.name = name;
		this.material = material;
		this.materialFB = materialFB;
		this.materialTable = materialTable;
		this.thickness = thickness;
		this.ral = ral;
		this.coatingType = coatingType;
		this.coatingSquare = coatingSquare;
		this.width = width;
		this.height = height;
		this.length = length;
		this.swFileName = swFileName;
		this.section = section;
		this.quantity = quantity;
	}

	public Part() {
		super();
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterialFB() {
		return materialFB;
	}

	public void setMaterialFB(String materialFB) {
		this.materialFB = materialFB;
	}

	public String getMaterialTable() {
		return materialTable;
	}

	public void setMaterialTable(String materialTable) {
		this.materialTable = materialTable;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public String getRal() {
		return ral;
	}

	public void setRal(String ral) {
		this.ral = ral;
	}

	public String getCoatingType() {
		return coatingType;
	}

	public void setCoatingType(String coatingType) {
		this.coatingType = coatingType;
	}

	public double getCoatingSquare() {
		return coatingSquare;
	}

	public void setCoatingSquare(double coatingSquare) {
		this.coatingSquare = coatingSquare;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getSwFileName() {
		return swFileName;
	}

	public void setSwFileName(String swFileName) {
		this.swFileName = swFileName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "PartsLine [designation=" + designation + ", name=" + name + ", material=" + material + ", materialFB="
				+ materialFB + ", materialTable=" + materialTable + ", thickness=" + thickness + ", ral=" + ral
				+ ", coatingType=" + coatingType + ", coatingSquare=" + coatingSquare + ", width=" + width + ", height="
				+ height + ", length=" + length + ", swFileName=" + swFileName + ", section=" + section + ", quantity="
				+ quantity + "]";
	}

}
