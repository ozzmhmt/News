package co.appstorm.newsx.model.realm;

import java.util.Date;

import co.appstorm.newsx.model.CategoryVM;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class RealmHelper {
    public final static String TAG = RealmHelper.class.getSimpleName();
    public static void saveRecentlyViewedArticle(final RealmArticle article){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmRecentItem realmRecentItem = new RealmRecentItem.Builder()
                        .articleId(String.valueOf(article.getArticleId()))
                        .article(article)
                        .viewDate(new Date(System.currentTimeMillis()))
                        .build();
                realm.copyToRealmOrUpdate(realmRecentItem);
            }
        });
    }

    public static boolean isFavourited(RealmArticle article){
        Realm realm = Realm.getDefaultInstance();
        RealmFavourite favourite = realm.where(RealmFavourite.class).equalTo("articleId",article.getArticleId()).findFirst();
        return favourite != null;
    }

    public static void saveFavouriteArticle(final RealmArticle article){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmFavourite realmFavourite = new RealmFavourite.Builder()
                        .articleId(String.valueOf(article.getArticleId()))
                        .article(article)
                        .favouriteDate(new Date(System.currentTimeMillis()))
                        .build();
                realm.copyToRealmOrUpdate(realmFavourite);
            }
        });
    }

    public static void removeFavouriteArticle(final RealmArticle article){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmFavourite realmFavourite = realm.where(RealmFavourite.class).equalTo("articleId",article.getArticleId()).findFirst();
                if (realmFavourite != null) {
                    realmFavourite.deleteFromRealm();
                }
            }
        });
    }

    public static void saveCategory(final CategoryVM category){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmCategory realmCategory = realm.createObject(RealmCategory.class, category.getCategoryId());
                realmCategory.setCategoryName(category.getCategoryName());
                realmCategory.setImage(category.getImage());
                realmCategory.setPosition(category.getPosition());
                realm.copyToRealmOrUpdate(realmCategory);
            }
        });
    }

    public static void removeCategory(final CategoryVM category){
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmCategory realmCategory = realm.where(RealmCategory.class).equalTo("categoryId",category.getCategoryId()).findFirst();
                if (realmCategory != null) {
                    realmCategory.deleteFromRealm();
                }
            }
        });
    }

    public static <T extends RealmObject> boolean isSavedToRealm(Class<T> clazz, String primaryFieldKey, String primaryFieldValue){
        Realm mRealm = Realm.getDefaultInstance();
        T result =  mRealm.where(clazz).equalTo(primaryFieldKey,primaryFieldValue).findFirst();
        mRealm.close();
        return result != null;
    }
}
