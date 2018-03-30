package co.appstorm.newsx.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class Author implements IAuthor, Serializable {
    @SerializedName("_id")
    private String authorId;
    private String name;
    private String image;
    private String bioGraphy;

    private Author(Builder builder) {
        setAuthorId(builder.authorId);
        setName(builder.name);
        setImage(builder.image);
        setBioGraphy(builder.bioGraphy);
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImage() {
        return image;
    }

    public String getBioGraphy() {
        return bioGraphy;
    }

    @Override
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public void setBioGraphy(String bioGraphy) {
        this.bioGraphy = bioGraphy;
    }

    public static final class Builder {
        private String authorId;
        private String name;
        private String image;
        private String bioGraphy;

        public Builder() {
        }

        public Builder authorId(String val) {
            authorId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder image(String val) {
            image = val;
            return this;
        }

        public Builder bioGraphy(String val) {
            bioGraphy = val;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}