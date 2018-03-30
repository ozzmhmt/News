package co.appstorm.newsx.helpers;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.transition.Fade;
import android.view.View;

import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.activities.BaseActivity;
import co.appstorm.newsx.custom.DetailsTransition;
import co.appstorm.newsx.utils.MyUtils;

/**
 * Helper class for navigating between fragments
 */
public class NavigationManager {
    /**
     * Listener interface for navigation events.
     */
    public interface NavigationListener {

        /**
         * Callback on backstack changed.
         */
        void onBackStackChanged();
    }

    private int containerId;
    //private int subContainerId;
    private FragmentManager mFragmentManager;
    private NavigationListener mNavigationListener;

    /**
     * Initialize the NavigationManager with a FragmentManager, which will be used at the
     * fragment transactions.
     *
     * @param fragmentManager
     */
    public void init(FragmentManager fragmentManager, int containerId) {
        mFragmentManager = fragmentManager;
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (mNavigationListener != null) {
                    mNavigationListener.onBackStackChanged();
                }
            }
        });
        this.containerId = containerId;
    }

    /**
     * Displays the next fragment
     *
     * @param fragment
     */
    public void open(Fragment fragment) {
        if (mFragmentManager != null) {
            //@formatter:off
            mFragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .setCustomAnimations(R.anim.fade_in_left,
                            R.anim.fade_out_right,
                            R.anim.fade_in_right,
                            R.anim.fade_out_left)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
            //@formatter:on
        }
    }

    /**
     * Add new fragment to top of base fragment
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (mFragmentManager != null) {
            String tag = fragment.getClass().getName();
            mFragmentManager.beginTransaction()
                    .add(containerId, fragment, tag)
                    .setCustomAnimations(R.anim.fade_in_left,
                            R.anim.fade_out_right,
                            R.anim.fade_in_right,
                            R.anim.fade_out_left)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    /**
     * Displays the next fragment with transition
     */
    public void addFragment(Fragment fragment, List<Pair<View, String>> lstPair) {
        if (MyUtils.isLollipop()) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (Pair<View, String> pair : lstPair) {
                transaction.addSharedElement(pair.first, pair.second);
            }
            transaction.add(containerId, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        } else {
            open(fragment);
        }
    }

    /**
     * Displays the next fragment with transition
     */
    public void open(Fragment fragment, List<Pair<View, String>> lstPair) {
        if (MyUtils.isLollipop()) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
            for (Pair<View, String> pair : lstPair) {
                mFragmentManager.beginTransaction()
                        .addSharedElement(pair.first, pair.second);
            }
            mFragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        } else {
            open(fragment);
        }
    }


    public void open(Fragment fragment, List<Pair<View, String>> lstPair, int enterTransition, int exitTransition) {
        if (MyUtils.isLollipop()) {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
            for (Pair<View, String> pair : lstPair) {
                mFragmentManager.beginTransaction()
                        .addSharedElement(pair.first, pair.second);
            }
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(enterTransition, 0, 0, exitTransition)
                    .setTransition(enterTransition)
                    .add(containerId, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
        } else {
            open(fragment);
        }
    }

    public void addFragment(Fragment fragment, int enterTransition, int exitTransition) {
        mFragmentManager.beginTransaction()
                .setCustomAnimations(enterTransition, 0, 0, exitTransition)
                .setTransition(enterTransition)
                .add(containerId, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    /**
     * pops every fragment and starts the given fragment as a new one.
     *
     * @param fragment
     */
    public void openAsRoot(Fragment fragment) {
        popEveryFragment();
        open(fragment);
    }


    /**
     * Pops all the queued fragments
     */
    private void popEveryFragment() {
        // Clear all back stack.
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();
            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public void popToRoot() {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        for (int i = 1; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = mFragmentManager.getBackStackEntryAt(i).getId();
            mFragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public Fragment getFragmentByTag(String tag) {
        return mFragmentManager.findFragmentByTag(tag);
    }

    /**
     * Navigates back by popping teh back stack. If there is no more items left we finish the current activity.
     *
     * @param baseActivity
     */
    public void navigateBack(BaseActivity baseActivity) {
        if (!isRootFragmentVisible()) {
            mFragmentManager.popBackStackImmediate();
        } else if (baseActivity != null) {
            baseActivity.finish();
        }
    }

    /**
     * @return true if the current fragment displayed is a root fragment
     */
    public boolean isRootFragmentVisible() {
        return mFragmentManager.getBackStackEntryCount() <= 1;
    }

    public Fragment getCurrentFragment() {
        //Fragment fragment =
        return mFragmentManager.findFragmentById(containerId);
        //return fragment != null ? fragment : mFragmentManager.findFragmentById(containerId);
    }

    public NavigationListener getNavigationListener() {
        return mNavigationListener;
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        mNavigationListener = navigationListener;
    }
}
