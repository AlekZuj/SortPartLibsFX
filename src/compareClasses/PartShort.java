package compareClasses;
public class PartShort {
    private String name;
    private int line;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }


    public PartShort(String name, int line) {
        this.name = name;
        this.line = line;

    }

    public PartShort() {
    }

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", line=" + line +
                '}';
    }
}
