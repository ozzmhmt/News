package co.appstorm.newsx.model.realm;

import co.appstorm.newsx.model.IArticle;
import co.appstorm.newsx.model.ICatalogType;
import io.realm.RealmObject;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class RealmArticle extends RealmObject implements ICatalogType, IArticle {

    private String articleId;
    private String categoryId;
    private String title;
    private String categoryName;
    private String content;
    private String coverImage;
    private String publishedDate;
    private String catalogType;

    public RealmArticle(){

    }

    private RealmArticle(Builder builder) {
        setArticleId(builder.articleId);
        setCategoryId(builder.categoryId);
        setTitle(builder.title);
        setCategoryName(builder.categoryName);
        setContent(builder.content);
        setCoverImage(builder.coverImage);
        setPublishedDate(builder.publishedDate);
        setCatalogType(builder.catalogType);
    }

    public String getArticleId() {
        return articleId;
    }

    @Override
    public String getName() {
        return title;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public String getAuthorId() {
        return null;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String getAuthorName() {
        return null;
    }

    @Override
    public String getHeadline() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    public static final class Builder {
        private String articleId;
        private String categoryId;
        private String title;
        private String categoryName;
        private String content;
        private String coverImage;
        private String publishedDate;
        private String catalogType;

        public Builder() {
        }

        public Builder articleId(String val) {
            articleId = val;
            return this;
        }

        public Builder categoryId(String val) {
            categoryId = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder categoryName(String val) {
            categoryName = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder coverImage(String val) {
            coverImage = val;
            return this;
        }

        public Builder publishedDate(String val) {
            publishedDate = val;
            return this;
        }

        public Builder catalogType(String val) {
            catalogType = val;
            return this;
        }

        public RealmArticle build() {
            return new RealmArticle(this);
        }
    }
}
