package co.appstorm.newsx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class Article implements ICatalogType, IArticle, IArticleDetail, Serializable {
    @SerializedName("_id")
    private String articleId;
    private String categoryId;
    private String authorId;
    private String author;
    private String category;
    private String name;
    private String image;
    private String headline;
    private String description;
    private String htmlContent;
    private String publishedDate;
    private boolean isSpotlight;
    private String spotlightImage;
    private String catalogType;
    private String externalUrl;

    private Article(Builder builder) {
        setArticleId(builder.articleId);
        setCategoryId(builder.categoryId);
        setAuthorId(builder.authorId);
        setAuthor(builder.author);
        setCategory(builder.category);
        setName(builder.name);
        setImage(builder.image);
        setHeadline(builder.headline);
        setDescription(builder.description);
        setHtmlContent(builder.htmlContent);
        setPublishedDate(builder.publishedDate);
        setSpotlight(builder.isSpotlight);
        setSpotlightImage(builder.spotlightImage);
        setCatalogType(builder.catalogType);
        setExternalUrl(builder.externalUrl);
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public String getCategoryName() {
        return category;
    }

    @Override
    public String getAuthorName() {
        return author;
    }

    @Override
    public String getArticleId() {
        return articleId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCoverImage() {
        return image;
    }

    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getHtmlContent() {
        return htmlContent;
    }

    @Override
    public String getExternalUrl() {
        return externalUrl;
    }

    @Override
    public String getPublishedDate() {
        return publishedDate;
    }

    @Override
    public String getCatalogType() {
        return catalogType;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setHtmlContent(String content) {
        this.htmlContent = content;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean isSpotlight() {
        return isSpotlight;
    }

    public void setSpotlight(boolean spotlight) {
        isSpotlight = spotlight;
    }

    public String getSpotlightImage() {
        return spotlightImage;
    }

    public void setSpotlightImage(String spotlightImage) {
        this.spotlightImage = spotlightImage;
    }

    public void setCatalogType(String type) {
        this.catalogType = type;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public static final class Builder {
        private String articleId;
        private String categoryId;
        private String authorId;
        private String author;
        private String category;
        private String name;
        private String image;
        private String headline;
        private String description;
        private String htmlContent;
        private String publishedDate;
        private boolean isActive;
        private boolean isVisible;
        private boolean isSpotlight;
        private String spotlightImage;
        private String catalogType;
        private String externalUrl;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder articleId(String val) {
            articleId = val;
            return this;
        }

        public Builder categoryId(String val) {
            categoryId = val;
            return this;
        }

        public Builder authorId(String val) {
            authorId = val;
            return this;
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder category(String val) {
            category = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder headline(String val) {
            headline = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder htmlContent(String val) {
            htmlContent = val;
            return this;
        }

        public Builder html(String html) {
            htmlContent = html;
            return this;
        }

        public Builder publishedDate(String val) {
            publishedDate = val;
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

        public Builder isSpotlight(boolean val) {
            isSpotlight = val;
            return this;
        }

        public Builder catalogType(String type) {
            catalogType = type;
            return this;
        }

        public Builder spotlight(boolean value) {
            isSpotlight = value;
            return this;
        }

        public Builder spotlightImage(String url) {
            spotlightImage = url;
            return this;
        }

        public Builder externalUrl(String url){
            externalUrl = url;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
