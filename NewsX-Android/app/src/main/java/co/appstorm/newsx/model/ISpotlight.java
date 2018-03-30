package co.appstorm.newsx.model;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface ISpotlight extends IBase {
    String getArticleId();

    String getImage();

    String getName();

    String getDescription();

    String getStartDate();

    String getEndDate();

    boolean isActive();

    boolean isVisible();

    void setArticleId(String articleId);

    void setName(String name);

    void setDescription(String description);

    void setImage(String url);

    void setStartDate(String startDate);

    void setEndDate(String endDate);

    void setActive(boolean isActive);

    void setVisible(boolean isVisible);
}
