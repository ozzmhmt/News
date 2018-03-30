package co.appstorm.newsx.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.CategoryDrawerAdapter;
import co.appstorm.newsx.databinding.ActivityMainBinding;
import co.appstorm.newsx.fragments.BaseFragment;
import co.appstorm.newsx.fragments.FavouritesFragment;
import co.appstorm.newsx.fragments.IntroFragment;
import co.appstorm.newsx.fragments.MainFragment;
import co.appstorm.newsx.fragments.RecentlyViewFragment;
import co.appstorm.newsx.fragments.SelectCategoryFragment;
import co.appstorm.newsx.fragments.SpotlightFragment;
import co.appstorm.newsx.helpers.DataModule;
import co.appstorm.newsx.helpers.NavigationManager;
import co.appstorm.newsx.model.Category;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.model.event.OnClickCategoryListener;
import co.appstorm.newsx.model.event.OnNavigationDrawerListener;
import co.appstorm.newsx.utils.PreferenceUtils;
import io.fabric.sdk.android.Fabric;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class MainActivity extends BaseActivity
        implements OnNavigationDrawerListener, NavigationManager.NavigationListener, OnClickCategoryListener {

    private ActivityMainBinding binding;
    private NavigationManager navigationManager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private InterstitialAd mInterstitialAds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initAds();
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initNavigationManager();
        setupListenerSkipButton();
    }

    public Button getSkipButton() {
        return binding != null ? binding.btnSkip : null;
    }

    public Button getUpdateButton() {
        return binding != null ? binding.btnUpdate : null;
    }

    public AdView getAds() {
        return binding != null ? binding.adView : null;
    }

    public InterstitialAd getInterstitialAds() {
        return mInterstitialAds;
    }

    public ProgressBar getProgressBar() {
        return binding != null ? binding.progressBar : null;
    }

    private void initAds() {
        MobileAds.initialize(this, getString(R.string.key_admob));

        mInterstitialAds = new InterstitialAd(this);
        mInterstitialAds.setAdUnitId(getString(R.string.key_interstitialAds));
        mInterstitialAds.loadAd(new AdRequest.Builder().build());

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    private void initNavigationManager() {
        navigationManager = new NavigationManager();
        navigationManager.init(getSupportFragmentManager(), R.id.fragment_container);
        navigationManager.setNavigationListener(this);
        if (!PreferenceUtils.getInstace(this).getShowIntroduce()) {
            navigationManager.openAsRoot(IntroFragment.newInstance());
            PreferenceUtils.getInstace(this).putShowIntroduce(true);
        } else {
            if (!PreferenceUtils.getInstace(this).getShowSelectCategory() && Const.SHOULD_SHOW_CATEGORY_SELECT) {
                navigationManager.openAsRoot(SelectCategoryFragment.newInstance("", new ArrayList<CategoryVM>()));
            }
        }

        if (!Const.SHOULD_SHOW_SPOTLIGHT){
            navigationManager.openAsRoot(MainFragment.newInstance());
        } else {
            navigationManager.openAsRoot(SpotlightFragment.newInstance());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        CategoryDrawerAdapter adapter = new CategoryDrawerAdapter(DataModule.getDrawers(), Category.CategoryType.HORIZONTAL, this);
        binding.navigationLayout.recyclerView.setLayoutManager(linearLayoutManager);
        binding.navigationLayout.recyclerView.setAdapter(adapter);
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

    @Override
    public void onCategoryClicked(String name) {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        switch (name) {
            case "Home":
                if (!(navigationManager.getCurrentFragment() instanceof MainFragment))
                    navigationManager.addFragment(MainFragment.newInstance());
                break;
            case "Spotlight":
                if (!(navigationManager.getCurrentFragment() instanceof SpotlightFragment))
                    navigationManager.openAsRoot(SpotlightFragment.newInstance());
                break;
            case "Recently Viewed":
                navigationManager.addFragment(RecentlyViewFragment.newInstance());
                break;
            case "Favourites":
                navigationManager.addFragment(FavouritesFragment.newInstance());
                break;
            case "Settings":
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    @Override
    public ActionBarDrawerToggle onAddDrawerListener(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_36px);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        return toggle;
    }

    @Override
    public void onRemoveDrawerListener(DrawerLayout.DrawerListener toggle) {
        binding.drawerLayout.removeDrawerListener(toggle);
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = navigationManager.getCurrentFragment();
        // FIXME: Unwrap this code to prevent slide menu open in child fragment
        if (fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onFragmentVisible();
        }
    }

    private void setupListenerSkipButton() {
        binding.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationManager.addFragment(MainFragment.newInstance());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getNavigationManager().getCurrentFragment();
            if (fragment instanceof SpotlightFragment || fragment instanceof IntroFragment || fragment instanceof MainFragment)
                finish();
            else
                super.onBackPressed();
        }
    }
}
