package co.appstorm.newsx.model;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface IAuthor extends IBase {
    String getAuthorId();

    String getName();

    String getImage();

    String getBioGraphy();

    void setAuthorId(String authorId);

    void setName(String name);

    void setImage(String image);

    void setBioGraphy(String bioGraphy);
}
