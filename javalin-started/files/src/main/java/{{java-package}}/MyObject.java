package {{java-package}};

public class MyObject {
    public String name;
    public int value;

    public MyObject() {
        // Potrzebne dla deserializacji Jackson
    }

    public MyObject(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
