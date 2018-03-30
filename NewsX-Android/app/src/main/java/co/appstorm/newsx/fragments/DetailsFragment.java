package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.appstorm.data.datasource.DataStore;
import co.appstorm.data.datasource.DataStoreFactory;
import co.appstorm.data.entity.ArticleEntity;
import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.ItemAdapter;
import co.appstorm.newsx.databinding.FragmentRecyclerBinding;
import co.appstorm.newsx.model.DataMapper;
import co.appstorm.newsx.model.DetailViewModel;
import co.appstorm.newsx.model.realm.RealmArticle;
import co.appstorm.newsx.model.realm.RealmHelper;
import co.appstorm.newsx.utils.PreferenceUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class DetailsFragment extends BaseFragment {
    public final static String TAG = DetailsFragment.class.getSimpleName();
    private FragmentRecyclerBinding binding;
    private String cateId = "";
    private String articleId = "";
    private ItemAdapter<DetailViewModel> adapter;
    private RealmArticle realmArticle;

    public static DetailsFragment newInstance(String cateId, String articleId, String title) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cateId", cateId);
        bundle.putString("articleId", articleId);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);

        showInterstitialAds();

        setUpToolbar();
        setupRecyclerView();
        getArticleDetails();
        return binding.getRoot();
    }

    private void showInterstitialAds() {
        int countShowInAds = PreferenceUtils.getInstace(getContext()).getShowInterstitialAds();
        if (countShowInAds < Const.COUNTER_SHOW_INTERSTITIAL_ADS) {
            countShowInAds++;
            PreferenceUtils.getInstace(getContext()).putShowInterstitialAds(countShowInAds);
        } else {
            PreferenceUtils.getInstace(getContext()).putShowInterstitialAds(1);
            if (getInterstitialAds().isLoaded()) {
                getInterstitialAds().show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }
        System.out.println("countShowInAds: " + countShowInAds);
    }

    private void setUpToolbar() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            cateId = bundle.getString("cateId");
            articleId = bundle.getString("articleId");
            binding.toolbar.setTitle(bundle.getString("title"));
            ((TextView) binding.toolbar.findViewById(R.id.toolbar_title)).setText(bundle.getString("title"));
        }
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMain().backToMainList();
            }
        });
        binding.toolbar.inflateMenu(R.menu.menu_details);

        onMenuFavoritesClick();
    }

    private void setupFavouriteButton(){
        MenuItem menuItem = binding.toolbar.getMenu().findItem(R.id.action_favorites);
        if (!RealmHelper.isFavourited(realmArticle)) {
            menuItem.setIcon(R.drawable.ic_favorite_border_white_24px);
        } else {
            menuItem.setIcon(R.drawable.ic_favorite_white_24px);
        }
    }

    private void onMenuFavoritesClick() {
        binding.toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_favorites) {
                    if (realmArticle == null) {
                        return true;
                    }
                    if (!RealmHelper.isFavourited(realmArticle)) {
                        RealmHelper.saveFavouriteArticle(realmArticle);
                        item.setIcon(R.drawable.ic_favorite_white_24px);
                    } else {
                        RealmHelper.removeFavouriteArticle(realmArticle);
                        item.setIcon(R.drawable.ic_favorite_border_white_24px);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItemAdapter<>(getActivity(), getNavigationManager(), getProgressBar());
        binding.recyclerView.setAdapter(adapter);
    }

    private void getArticleDetails() {
        DataStore dataStore = DataStoreFactory.create();
        dataStore.articleDetails(articleId,cateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArticleEntity>() {
                    @Override
                    public void accept(@NonNull ArticleEntity entity) throws Exception{
                        adapter.addItem(DataMapper.generateDetailViewModel(entity));
                        realmArticle = DataMapper.transformRealmArticle(entity,articleId);
                        RealmHelper.saveRecentlyViewedArticle(realmArticle);
                        setupFavouriteButton();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onFragmentVisible() {
        showAds(false);
        showSkip(false);
        PreferenceUtils.getInstace(getContext()).putRecentlyView(cateId, articleId);
    }
}
