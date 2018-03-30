package co.appstorm.newsx.model.realm;

import java.io.Serializable;

import co.appstorm.newsx.model.CategoryVM;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class RealmCategory extends RealmObject implements CategoryVM, Serializable {
    @PrimaryKey
    private String categoryId;
    private String categoryName;
    private String image;
    private int position;


    public RealmCategory(){}

    private RealmCategory(Builder builder) {
        setCategoryId(builder.categoryId);
        setCategoryName(builder.categoryName);
        setImage(builder.image);
    }


    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static final class Builder {
        private String categoryName;
        private String image;
        private String categoryId;

        public Builder() {
        }

        public Builder categoryName(String val) {
            categoryName = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public RealmCategory build() {
            return new RealmCategory(this);
        }

        public Builder categoryId(String val) {
            categoryId = val;
            return this;
        }
    }
}
