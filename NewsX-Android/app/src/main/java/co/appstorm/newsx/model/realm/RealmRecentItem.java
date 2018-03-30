package co.appstorm.newsx.model.realm;

import java.util.Date;

import co.appstorm.newsx.model.ICatalogType;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ozzmhmt on 4/2/2018.
 */
public class RealmRecentItem extends RealmObject implements ICatalogType, RealmArticleVM {
    @PrimaryKey
    private String articleId;
    private RealmArticle article;
    private Date viewDate;

    public RealmRecentItem(){

    }

    private RealmRecentItem(Builder builder) {
        setArticleId(builder.articleId);
        setArticle(builder.article);
        setViewDate(builder.viewDate);
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public RealmArticle getArticle() {
        return article;
    }

    public void setArticle(RealmArticle article) {
        this.article = article;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    @Override
    public String getCatalogType() {
        if (article == null) {
            return null;
        }
        return article.getCatalogType();
    }


    public static final class Builder {
        private RealmArticle article;
        private Date viewDate;
        private String articleId;

        public Builder() {
        }

        public Builder article(RealmArticle val) {
            article = val;
            return this;
        }

        public Builder viewDate(Date val) {
            viewDate = val;
            return this;
        }

        public RealmRecentItem build() {
            return new RealmRecentItem(this);
        }

        public Builder articleId(String val) {
            articleId = val;
            return this;
        }
    }
}
