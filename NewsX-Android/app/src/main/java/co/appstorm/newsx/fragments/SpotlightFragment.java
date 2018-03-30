package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.appstorm.data.datasource.DataStore;
import co.appstorm.data.datasource.DataStoreFactory;
import co.appstorm.data.entity.ArticleEntity;
import co.appstorm.newsx.BuildConfig;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.PagerDelegateManager;
import co.appstorm.newsx.adapter.ViewPagerAdapter;
import co.appstorm.newsx.adapter.delegates.SpotlightFullBackground;
import co.appstorm.newsx.databinding.FragmentSpotlightBinding;
import co.appstorm.newsx.model.DataMapper;
import co.appstorm.newsx.model.SpotlightVM;
import co.appstorm.newsx.model.event.ListenerEventClicked;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class SpotlightFragment extends BaseFragment implements ListenerEventClicked {

    private FragmentSpotlightBinding binding;
    private ViewPagerAdapter<SpotlightVM> adapter;

    public static SpotlightFragment newInstance() {
        return new SpotlightFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_spotlight, container, false);

        setupViewPagerAndIndicator();
        return binding.getRoot();
    }

    private void setupViewPagerAndIndicator() {
        PagerDelegateManager<SpotlightVM> delegateManager = new PagerDelegateManager<>();
        delegateManager.addDelegate(new SpotlightFullBackground<SpotlightVM>(getFragmentManager()));
        adapter = new ViewPagerAdapter<>(delegateManager);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setListenerEventClicked(this);
        loadSpotlightData();
    }

    private void setupIndicator(List<SpotlightVM> spotlights) {
        binding.indicator.setViewCount(spotlights.size());
        binding.viewPager.setIndicator(binding.indicator);
    }

    // Úe to update data in position
    @Override
    public void listenerEventClicked(int position) {
        if (getSkipButton() != null) {
            if (position == binding.viewPager.getAdapter().getCount() - 1)
                getSkipButton().setText(getString(R.string.done));
            else
                getSkipButton().setText(getString(R.string.skip));
        }
    }

    @Override
    public void onFragmentVisible() {
        showSkip(true);
        showUpdate(false);
        showAds(false);
    }

    private void loadSpotlightData(){
        DataStore dataStore = DataStoreFactory.create();
        dataStore.spotlightList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleEntity>>() {
            @Override
            public void accept(@NonNull List<ArticleEntity> articleEntities) throws Exception {
                ArrayList<SpotlightVM> articles = new ArrayList<>();
                for (ArticleEntity entity : articleEntities) {
                    articles.add(DataMapper.transformSpotlight(entity));
                }
                adapter.addDataList(articles);
                setupIndicator(articles);
            }
        },new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (BuildConfig.DEBUG) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}
