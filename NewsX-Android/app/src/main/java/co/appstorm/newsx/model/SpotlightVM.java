package co.appstorm.newsx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class SpotlightVM implements Serializable {
    @SerializedName("_id")
    private String articleId;
    private String categoryId;
    private String name;
    private String image;
    private String categoryName;
    @SerializedName("about")
    private String description;

    private SpotlightVM(Builder builder) {
        setArticleId(builder.articleId);
        setCategoryId(builder.categoryId);
        setImage(builder.image);
        setName(builder.name);
        setCategoryName(builder.categoryName);
        setDescription(builder.description);
    }

    public String getArticleId() {
        return articleId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class Builder {
        private String articleId;
        private String categoryId;
        private String name;
        private String image;
        private String categoryName;
        private String description;

        public Builder() {
        }

        public Builder articleId(String val) {
            articleId = val;
            return this;
        }

        public Builder categoryId(String val){
            categoryId = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder categoryName(String val){
            categoryName = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public SpotlightVM build() {
            return new SpotlightVM(this);
        }
    }
}
