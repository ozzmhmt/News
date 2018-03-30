package co.appstorm.newsx.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class ViewPagerAdapter<T> extends PagerAdapter {

    private PagerDelegateManager<T> pagerManager;

    public ViewPagerAdapter(PagerDelegateManager<T> manager) {
        this.pagerManager = manager;
    }

    @Override
    public int getCount() {
        return pagerManager.getDataList().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return pagerManager.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addDataList(List<T> objects){
        addDataList(0,objects);
    }

    public void addDataList(int start, List<T> objects){
        pagerManager.addDataList(start,objects);
        notifyDataSetChanged();
    }
}
