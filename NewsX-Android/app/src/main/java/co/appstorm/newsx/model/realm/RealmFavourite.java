package co.appstorm.newsx.model.realm;

import java.util.Date;

import co.appstorm.newsx.model.ICatalogType;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class RealmFavourite extends RealmObject implements ICatalogType, RealmArticleVM {
    @PrimaryKey
    private String articleId;
    private RealmArticle article;
    private Date favouriteDate;

    public RealmFavourite(){

    }

    private RealmFavourite(Builder builder) {
        setArticleId(builder.articleId);
        setArticle(builder.article);
        setFavouriteDate(builder.favouriteDate);
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Date getFavouriteDate() {
        return favouriteDate;
    }

    public void setFavouriteDate(Date favouriteDate) {
        this.favouriteDate = favouriteDate;
    }

    public RealmArticle getArticle() {

        return article;
    }

    public void setArticle(RealmArticle article) {
        this.article = article;
    }

    @Override
    public String getCatalogType() {
        return article.getCatalogType();
    }


    public static final class Builder {
        private RealmArticle article;
        private Date favouriteDate;
        private String articleId;

        public Builder() {
        }

        public Builder article(RealmArticle val) {
            article = val;
            return this;
        }

        public Builder favouriteDate(Date val) {
            favouriteDate = val;
            return this;
        }

        public RealmFavourite build() {
            return new RealmFavourite(this);
        }

        public Builder articleId(String val) {
            articleId = val;
            return this;
        }
    }
}
