package co.appstorm.newsx.model;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface IArticle extends IBase {
    String getCategoryId();

    String getAuthorId();

    String getArticleId();

    String getName();

    String getCategoryName();

    String getAuthorName();

    String getCoverImage();

    String getHeadline();

    String getDescription();

    String getPublishedDate();

    String getCatalogType();

}
