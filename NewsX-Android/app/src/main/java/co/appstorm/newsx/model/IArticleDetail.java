package co.appstorm.newsx.model;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface IArticleDetail extends IBase {
    String getHtmlContent();

    String getExternalUrl();

    void setHtmlContent(String content);
}
