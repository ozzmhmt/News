package co.appstorm.newsx.model;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class DetailViewModel {

    private String title;
    private String content;
    private String externalContent;

    private DetailViewModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private DetailViewModel(String title, String content, String externalContent) {
        this.title = title;
        this.content = content;
        this.externalContent = externalContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExternalContent() {
        return externalContent;
    }

    public void setExternalContent(String externalContent) {
        this.externalContent = externalContent;
    }

    public static final class Builder {
        private String title;
        private String content;
        private String externalContent;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setExternalContent(String url){
            this.externalContent = url;
            return this;
        }

        public DetailViewModel createDetailViewModel() {
            return new DetailViewModel(title, content, externalContent);
        }
    }


}
