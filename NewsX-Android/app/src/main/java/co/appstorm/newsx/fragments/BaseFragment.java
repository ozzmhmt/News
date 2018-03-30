package co.appstorm.newsx.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import co.appstorm.newsx.activities.MainActivity;
import co.appstorm.newsx.helpers.NavigationManager;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public abstract class BaseFragment extends Fragment {
    public abstract void onFragmentVisible();

    public MainActivity getMain() {
        return (getActivity() != null && getActivity() instanceof MainActivity) ? (MainActivity) getActivity() : null;
    }

    public NavigationManager getNavigationManager() {
        return getMain() != null ? getMain().getNavigationManager() : null;
    }

    public Button getSkipButton() {
        return getMain() != null ? getMain().getSkipButton() : null;
    }

    public void showSkip(boolean isShow) {
        if (getSkipButton() != null) {
            if (isShow)
                getSkipButton().setVisibility(View.VISIBLE);
            else
                getSkipButton().setVisibility(View.GONE);
        }
    }

    public AdView getAds() {
        return getMain() != null ? getMain().getAds() : null;
    }

    public void showAds(boolean isShow) {
        if (getSkipButton() != null) {
            if (isShow)
                getAds().setVisibility(View.VISIBLE);
            else
                getAds().setVisibility(View.GONE);
        }
    }

    public InterstitialAd getInterstitialAds() {
        return getMain() != null ? getMain().getInterstitialAds() : null;
    }

    public Button getUpdateButton() {
        return getMain() != null ? getMain().getUpdateButton() : null;
    }

    public ProgressBar getProgressBar() {
        return getMain() != null ? getMain().getProgressBar() : null;
    }


    public void showUpdate(boolean isShow) {
        if (getUpdateButton() != null) {
            if (isShow)
                getUpdateButton().setVisibility(View.VISIBLE);
            else
                getUpdateButton().setVisibility(View.GONE);
        }
    }

    public void showProgressBar(boolean isShow) {
        if (getMain() != null) {
            if (isShow)
                getProgressBar().setVisibility(View.VISIBLE);
            else
                getProgressBar().setVisibility(View.GONE);
        }
    }
}
