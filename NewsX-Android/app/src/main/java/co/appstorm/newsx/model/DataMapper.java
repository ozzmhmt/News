package co.appstorm.newsx.model;

import co.appstorm.data.entity.ArticleEntity;
import co.appstorm.data.entity.CategoryEntity;
import co.appstorm.newsx.model.realm.RealmArticle;
import co.appstorm.newsx.model.realm.RealmCategory;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class DataMapper {
    public final static String TAG = DataMapper.class.getSimpleName();
    public static Category transformCategory(CategoryEntity entity) {
        return new Category.Builder()
                .categoryId(String.valueOf(entity.getCategoryId()))
                .image(entity.getImage())
                .categoryName(entity.getCategoryName())
                .build();
    }

    public static Article transformArticle(ArticleEntity entity) {
        return new Article.Builder()
                .catalogType(entity.getCatalogType())
                .articleId(String.valueOf(entity.getArticleId()))
                .description(entity.getDescription())
                .name(entity.getName())
                .image(entity.getImage())
                .publishedDate(entity.getPublishedDate())
                .headline(entity.getHeadline())
                .html(entity.getHtmlContent())
                .build();
    }

    public static SpotlightVM transformSpotlight(ArticleEntity entity) {
        return new SpotlightVM.Builder()
                .articleId(String.valueOf(entity.getArticleId()))
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .categoryName(entity.getCategoryName())
                .description(entity.getDescription())
                .image(entity.getImage())
                .build();
    }

    public static DetailViewModel generateDetailViewModel(ArticleEntity entity) {
        return new DetailViewModel.Builder()
                .setContent(entity.getHtmlContent())
                .setTitle(entity.getName())
                .setExternalContent(entity.getExternalUrl())
                .createDetailViewModel();
    }

    public static RealmArticle transformRealmArticle(ArticleEntity entity, String articleId) {
        return new RealmArticle.Builder()
                .articleId(String.valueOf(articleId))
                .categoryId(entity.getCategoryId())
                .title(entity.getName())
                .categoryName(entity.getCategoryName())
                .content(entity.getHtmlContent())
                .publishedDate(entity.getPublishedDate())
                .coverImage(entity.getImage())
                .catalogType(entity.getCatalogType())
                .build();
    }

    public static RealmCategory transformRealmCategory(CategoryEntity entity){
        return new RealmCategory.Builder()
                .categoryId(entity.getCategoryId())
                .categoryName(entity.getCategoryName())
                .image(entity.getImage())
                .build();
    }
}
