package co.appstorm.newsx.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.appstorm.data.datasource.DataStore;
import co.appstorm.data.datasource.DataStoreFactory;
import co.appstorm.data.entity.CategoryEntity;
import co.appstorm.newsx.BuildConfig;
import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.SectionsPagerAdapter;
import co.appstorm.newsx.databinding.FragmentMainBinding;
import co.appstorm.newsx.model.Category;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.model.DataMapper;
import co.appstorm.newsx.model.event.OnNavigationDrawerListener;
import co.appstorm.newsx.model.realm.RealmCategory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class MainFragment extends BaseFragment {
    private static MainFragment instance;
    private FragmentMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private OnNavigationDrawerListener listener;
    private SectionsPagerAdapter<CategoryVM> adapter;

    public static MainFragment getInstance() {
        if (instance == null)
            instance = new MainFragment();
        return instance;
    }

    public static MainFragment newInstance() {
        instance = new MainFragment();
        return instance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof OnNavigationDrawerListener)
            listener = (OnNavigationDrawerListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        getCategory();

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (binding.toolbar != null) {
            toggle = listener.onAddDrawerListener(binding.toolbar);
            binding.toolbar.setTitle(R.string.app_name);
        }
    }

    @Override
    public void onFragmentVisible() {
        showSkip(false);
        showAds(true);
    }

    private void getCategory() {
        adapter = new SectionsPagerAdapter<CategoryVM>(getContext(), getFragmentManager());
        showProgressBar(true);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCategory> results = realm.where(RealmCategory.class).findAllSorted("position", Sort.ASCENDING);
        for (RealmCategory category: results){
            adapter.addFragment(CategoryFragment.newInstance(category.getCategoryId()), category.getCategoryName());
        }
        setupViewPager();
        showProgressBar(false);

        DataStore dataStore = DataStoreFactory.create();
        dataStore.categoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryEntity>>() {
            @Override
            public void accept(@NonNull List<CategoryEntity> categoryEntities) throws Exception{
                List<Category> categories = new ArrayList<>();
                for (CategoryEntity entity : categoryEntities) {
                    categories.add(DataMapper.transformCategory(entity));
                }
                //TODO Fix https://bitbucket.org/appstormco/newsx/issues/8/
                if (Const.SHOULD_SHOW_CATEGORY_SELECT) {
                    adapter.addFragment(SelectCategoryFragment.newInstance(getString(R.string.tab), categories), getString(R.string.add_tab));
                    adapter.notifyDataSetChanged();
                    //Display the update button when tab = 0
                    if (adapter.getCount() == 1 && adapter.getItem(0) instanceof SelectCategoryFragment) {
                        showUpdate(true);
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception{
                if (BuildConfig.DEBUG){
                    throwable.printStackTrace();
                }
            }
        });
    }

    private void setupViewPager() {
        // Set up the ViewPager with the sections adapter.
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (adapter.getItem(position) instanceof SelectCategoryFragment) {
                    showUpdate(true);
                } else {
                    showUpdate(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    public SectionsPagerAdapter<CategoryVM> getAdapter() {
        return adapter;
    }

    public void switchTab(int pos) {
        binding.viewPager.setCurrentItem(pos);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null && toggle != null)
            listener.onRemoveDrawerListener(toggle);
    }
}
