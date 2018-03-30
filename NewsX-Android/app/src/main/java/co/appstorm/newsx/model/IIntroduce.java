package co.appstorm.newsx.model;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface IIntroduce extends IBase {
    String getImage();

    String getName();

    String getDescription();

    int getImageRes();

    boolean isActive();

    boolean isVisible();

    void setName(String name);

    void setDescription(String description);

    void setImage(String url);

    void setImageRes(int value);

    void setActive(boolean isActive);

    void setVisible(boolean isVisible);
}
