package co.appstorm.newsx.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.model.Drawer;
import co.appstorm.newsx.model.IArticle;
import co.appstorm.newsx.model.IBase;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.model.Introduce;
import co.appstorm.newsx.utils.MyUtils;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class DataModule {
    private static Context context;
    private static List<IBase> introduces = new ArrayList<>();
    private static List<IBase> categories = new ArrayList<>();
    private static List<IBase> authors = new ArrayList<>();
    private static List<IBase> newsList = new ArrayList<>();
    private static List<IBase> detailsList = new ArrayList<>();
    private static List<IBase> spotLights = new ArrayList<>();
    private static List<Drawer> drawers = new ArrayList<>();

    public static void setupData(Context context) {
        DataModule.context = context;
        readArticleFile();
        createDrawersList();
    }

    private static void readArticleFile() {
        Gson gson = new Gson();

        if (Const.USE_INTERNET_INTRO) {
            introduces = gson.fromJson(MyUtils.toReaderFile(context, "introduce.json"), new TypeToken<List<Introduce>>() {
            }.getType());
        } else {
            Introduce intro1 = new Introduce.Builder()
                    .name(context.getString(R.string.intro_title_01))
                    .description(context.getString(R.string.intro_subtitle_01))
                    .imageRes(R.drawable.intro_type2_01)
                    .build();
            Introduce intro2 = new Introduce.Builder()
                    .name(context.getString(R.string.intro_title_02))
                    .description(context.getString(R.string.intro_subtitle_02))
                    .imageRes(R.drawable.intro_type2_02)
                    .build();
            Introduce intro3 = new Introduce.Builder()
                    .name(context.getString(R.string.intro_title_03))
                    .description(context.getString(R.string.intro_subtitle_03))
                    .imageRes(R.drawable.intro_type2_03)
                    .build();

            introduces.add(intro1);
            introduces.add(intro2);
            introduces.add(intro3);
        }
//        spotLights = gson.fromJson(MyUtils.toReaderFile(context, "spotlight.json"), new TypeToken<List<SpotlightVM>>() {
//        }.getType());
//        authors = gson.fromJson(MyUtils.toReaderFile(context, "author.json"), new TypeToken<List<Author>>() {}.getType());
//        categories = gson.fromJson(MyUtils.toReaderFile(context, "category.json"), new TypeToken<List<Category>>() {}.getType());
//        newsList = gson.fromJson(MyUtils.toReaderFile(context, "article.json"), new TypeToken<List<Article>>() {}.getType());
//        detailsList = gson.fromJson(MyUtils.toReaderFile(context, "detailhtml.json"), new TypeToken<List<Article>>() {}.getType());
    }

    private static void createDrawersList() {
        Drawer drawer = new Drawer(context.getString(R.string.home), "", R.drawable.ic_home_white_24dp);
        drawers.add(drawer);
        drawer = new Drawer(context.getString(R.string.spotlight), "", R.drawable.ic_whatshot_white_24dp);
        drawers.add(drawer);
        drawer = new Drawer(context.getString(R.string.recently_viewed), "", R.drawable.ic_access_time_white_24dp);
        drawers.add(drawer);
        drawer = new Drawer(context.getString(R.string.favourites), "", R.drawable.ic_favorite_white_24px);
        drawers.add(drawer);
        drawer = new Drawer(context.getString(R.string.settings), "", R.drawable.ic_settings_white_24dp);
        drawers.add(drawer);
    }

    public static List<IBase> getIntroduces() {
        return introduces;
    }

    public static List<Drawer> getDrawers() {
        return drawers;
    }

    public static List<IBase> getCategories() {
        return categories;
    }

    public static IBase getCategory(String categoryId) {
        for (IBase base : categories) {
            CategoryVM category = (CategoryVM) base;
            if (category.getCategoryId().equals(categoryId))
                return category;
        }
        return null;
    }

    public static List<IBase> getNewsList() {
        return newsList;
    }

    public static List<IBase> getNews(String categoryId) {
        List<IBase> newsListInCate = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            IArticle article = (IArticle) newsList.get(i);
            if (article.getCategoryId().equals(categoryId)) {
                newsListInCate.add(newsList.get(i));
            }
        }
        return newsListInCate;
    }

    public static IBase getNew(String articleId) {
        for (int i = 0; i < newsList.size(); i++) {
            IArticle article = (IArticle) newsList.get(i);
            if (article.getArticleId().equals(articleId)) {
                return newsList.get(i);
            }
        }
        return null;
    }

    public static List<IBase> getDetailsList() {
        return detailsList;
    }

    public static List<IBase> getAuthors() {
        return authors;
    }

    public static List<IBase> getSpotLights() {
        return spotLights;
    }
}