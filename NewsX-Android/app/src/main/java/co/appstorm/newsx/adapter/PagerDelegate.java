package co.appstorm.newsx.adapter;

import android.view.ViewGroup;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface PagerDelegate<T> {

    public Object instantiateItem(ViewGroup container, T item, int position);

    public boolean isChosenForDisplay(T item);
}
