package co.appstorm.newsx.custom;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import co.appstorm.newsx.model.event.ListenerEventClicked;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class CustomViewPager extends ViewPager {
    private static final int SCROLL_VIEWPAGER = 0;
    //true swipe left, false right
    private boolean direction = false;
    //disable direction
    CirclePageIndicator indicator;
    OnPageChangeListener onPageChangeListener;
    ListenerEventClicked listenerEventClicked;
    int sizeAdapter;

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        this.sizeAdapter = adapter.getCount();
        super.setAdapter(adapter);
    }

    public void setIndicator(CirclePageIndicator indicator) {
        this.indicator = indicator;
    }

    public void setListenerEventClicked(ListenerEventClicked listenerEventClicked) {
        this.listenerEventClicked = listenerEventClicked;
    }

    private void init() {
        setCurrentItem(2);
        onPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setupComponent();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    setupPage(getCurrentItem());
                }
            }
        };
        addOnPageChangeListener(onPageChangeListener);
    }

    private boolean setupPage(int position) {
        if (position == 0) {
            direction = true;
            return false;
        } else if (position == sizeAdapter - 1) {
            direction = false;
            return false;
        }
        return true;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    private void setupComponent() {
        if (Math.abs(getCurrentItem() - 1 - indicator.getCurrentDisplayed()) < 2) {
            setDirection(getCurrentItem() - 1 < indicator.getCurrentDisplayed());
        }
        setupIndicator(getCurrentItem());
        setupListeningSwith(getCurrentItem());
    }

    private void setupIndicator(int position) {
        if (indicator != null) {
            indicator.updateIndicator(position, sizeAdapter);
        }
    }

    private void setupListeningSwith(int position) {
        if (listenerEventClicked != null) {
            listenerEventClicked.listenerEventClicked(position);
        }
    }
}
