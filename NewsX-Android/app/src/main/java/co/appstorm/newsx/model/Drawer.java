package co.appstorm.newsx.model;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class Drawer {
    private String name;
    private String image;
    private int src;

    public Drawer(String name, String image, int src) {
        this.name = name;
        this.image = image;
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
