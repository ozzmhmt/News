package co.appstorm.newsx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class Category implements CategoryVM, Serializable {
    @SerializedName("_id")
    private String categoryId;
    @SerializedName("name")
    private String categoryName;
    private String image;
    private int position;

    private Category(Builder builder) {
        setCategoryId(builder.categoryId);
        setCategoryName(builder.categoryName);
        setImage(builder.image);
        setPosition(builder.position);
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static final class Builder {
        private String categoryId;
        private String categoryName;
        private String image;
        private int position;

        public Builder() {
        }

        public Builder categoryId(String val) {
            categoryId = val;
            return this;
        }

        public Builder categoryName(String val) {
            categoryName = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder position(int val) {
            position = val;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }

    public enum CategoryType {
        VERTICAL,   // The icon and title is vertical
        HORIZONTAL  // The icon and title is horizontal
    }
}
