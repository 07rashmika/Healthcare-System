package Model;

public class Medication {
    private int id;
    private String name;
    private int stock;
    private int threshold;
    //medication
    public Medication(int id, String name, int stock, int threshold) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.threshold = threshold;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
    public int getThreshold() { return threshold; }
}
