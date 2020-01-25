package ml.meiner.anselm.DataBase;

public class Chargingstation {

    private int id;
    private String name;
    private String position;
    private int height;
    private double longitude;
    private double latitude;

    public Chargingstation() {
    }

    public Chargingstation(int id, String name, String position, int height) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return name + " - " + position + " - " + height + " cm";
    }

}
