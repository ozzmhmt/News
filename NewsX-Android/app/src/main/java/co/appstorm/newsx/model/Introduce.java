package co.appstorm.newsx.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class Introduce implements IIntroduce {
    private String name;
    private String image;
    @SerializedName("about")
    private String description;
    private boolean isActive;
    private boolean isVisible;
    private transient int imageRes;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    private Introduce(Builder builder) {
        setImage(builder.image);
        setName(builder.name);
        setDescription(builder.description);
        setActive(builder.isActive);
        setVisible(builder.isVisible);
        setImageRes(builder.imageRes);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public static final class Builder {
        private String name;
        private String image;
        private String description;
        private boolean isActive;
        private boolean isVisible;
        private int imageRes;

        public Builder() {
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder imageRes(int val){
            imageRes = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder isActive(boolean val) {
            isActive = val;
            return this;
        }

        public Builder isVisible(boolean val) {
            isVisible = val;
            return this;
        }

        public Introduce build() {
            return new Introduce(this);
        }
    }
}
