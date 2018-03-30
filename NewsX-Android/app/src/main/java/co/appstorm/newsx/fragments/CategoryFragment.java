package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.appstorm.data.datasource.DataStore;
import co.appstorm.data.datasource.DataStoreFactory;
import co.appstorm.data.entity.ArticleEntity;
import co.appstorm.newsx.BuildConfig;
import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.EndlessRecyclerViewScrollListener;
import co.appstorm.newsx.adapter.ItemAdapter;
import co.appstorm.newsx.databinding.FragmentCategoryBinding;
import co.appstorm.newsx.model.DataMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class CategoryFragment extends BaseFragment {
    public final static String TAG = CategoryFragment.class.getSimpleName();
    private FragmentCategoryBinding binding;
    private ItemAdapter<Object> adapter;
    private String categoryId;
    private LinearLayoutManager layoutManager;
    private EndlessRecyclerViewScrollListener endlessScrollListener;

    public static CategoryFragment newInstance(String categoryId) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        categoryId = getArguments().getString("categoryId");
        setupRecyclerView();
        setupInfiniteScroll();
        setupSwipeLayout();
        getArticles(Const.ARTICLES_PER_LOAD, 1);
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ItemAdapter<>(getActivity(), getNavigationManager());
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupSwipeLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clearItems();
                        endlessScrollListener.resetState();
                        getArticles(Const.ARTICLES_PER_LOAD, 1);
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);

            }
        });
    }

    private void setupInfiniteScroll() {
        endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if ((totalItemsCount-adapter.getAdCount()) % Const.ARTICLES_PER_LOAD == 0) {
                    getArticles(Const.ARTICLES_PER_LOAD, page+1);
                }
            }
        };
        binding.recyclerView.addOnScrollListener(endlessScrollListener);
    }

    private void getArticles(int limit, int page) {
        if (categoryId == null || categoryId.equals(""))
            return;
        DataStore dataStore = DataStoreFactory.create();
        dataStore.articleList(categoryId, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleEntity>>() {
                    @Override
                    public void accept(@NonNull List<ArticleEntity> articleEntities) throws Exception {
                        ArrayList<Object> articles = new ArrayList<Object>();
                        for (ArticleEntity entity : articleEntities) {
                            articles.add(DataMapper.transformArticle(entity));
                        }
                        adapter.addItems(articles);
                        //getNativeAds();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (BuildConfig.DEBUG) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }

    /*private void getNativeAds() {
        if (categoryId == null || categoryId.equals(""))
            return;
        final AdsLoadManager adsLoadManager = new AdsLoadManager();

        adsLoadManager.loadNativeAdSet()
                .subscribe(new Consumer<NativeAdsManager>() {
                    @Override
                    public void accept(@NonNull NativeAdsManager nativeAdsManager) throws Exception {
                        int articleCount = adapter.getItemCount();
                        int adCount = nativeAdsManager.getUniqueNativeAdCount();
                        int maxAds = articleCount / Const.ARTICLES_PER_AD;

                        if (articleCount < Const.ARTICLES_PER_AD) {
                            adapter.addItem(nativeAdsManager.nextNativeAd());
                            adapter.increaseAdCount(1);
                            return;
                        }

                        if ((articleCount / Const.ARTICLES_PER_AD) <= adCount) {
                            for (int i = 0; i < adCount; i++) {
                                int pos = i + 1;
                                if (maxAds < pos)
                                    return;
                                adapter.addItem(nativeAdsManager.nextNativeAd(), pos * Const.ARTICLES_PER_AD);
                                adapter.increaseAdCount(1);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (BuildConfig.DEBUG) {
                            throwable.printStackTrace();
                        }
                    }
                });
    }*/

    @Override
    public void onFragmentVisible() {

    }
}
