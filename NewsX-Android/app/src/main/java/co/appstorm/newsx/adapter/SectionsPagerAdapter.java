package co.appstorm.newsx.adapter;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.fragments.CategoryFragment;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.utils.PreferenceUtils;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter<T extends CategoryVM> extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();
    private Context mContext;
    private String addTabName;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        addTabName = context.getString(R.string.add_tab);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    public void addFragment(Fragment fragment, String title) {
        if (mFragmentTitleList.contains(title)) {
            return;
        }
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void addFragmentMore(Fragment fragment, String title) {
        if (!mFragmentTitleList.contains(title)) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

            swapWithAddTab(title);
        }
    }

    public void removeFragment(String title) {
        if (mFragmentTitleList.contains(title)) {
            mFragmentList.remove(mFragmentTitleList.indexOf(title));
        }
    }

    public void removeFragments(List<String> titleList) {
        for (String title : titleList) {
            mFragmentList.remove(mFragmentTitleList.indexOf(title));
            mFragmentTitleList.remove(title);
        }
    }

    public void refineFragmentList(List<String> titleList) {
        List<String> toRemove = new ArrayList<>();
        for (String title : mFragmentTitleList) {
            if (!titleList.contains(title) && !title.equals(addTabName)) {
                toRemove.add(title);
            } else {
                swapWithAddTab(title);
            }
        }
        removeFragments(toRemove);
    }

    public void clearFragments() {
        if (mFragmentTitleList.size() < 2){
            return;
        }

        int size = mFragmentTitleList.size();
        for (int i = 0; i < size; i++) {
            if (!mFragmentTitleList.get(i).equals(addTabName)){
                mFragmentTitleList.remove(i);
                mFragmentList.remove(i);
            }
        }
    }

    private void swapWithAddTab(String title){
        int indexAdd = mFragmentTitleList.indexOf(addTabName);
        int indexFrag = mFragmentTitleList.indexOf(title);

        Collections.swap(mFragmentList, indexFrag, indexAdd);
        Collections.swap(mFragmentTitleList, indexFrag, indexAdd);
    }

    public void addCategoryList(List<T> list) {
        for (T category : list) {
            String cateId = PreferenceUtils.getInstace(mContext).getCategorySelected(category.getCategoryId());
            if (!cateId.equals("")) {
                this.addFragmentMore(CategoryFragment.newInstance(category.getCategoryId()), category.getCategoryName());
            } else {
                this.removeFragment(category.getCategoryName());
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
