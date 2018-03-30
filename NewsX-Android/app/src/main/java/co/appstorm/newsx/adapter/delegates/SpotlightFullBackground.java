package co.appstorm.newsx.adapter.delegates;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.PagerDelegate;
import co.appstorm.newsx.databinding.PagerSpotlight01Binding;
import co.appstorm.newsx.fragments.DetailsFragment;
import co.appstorm.newsx.model.SpotlightVM;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class SpotlightFullBackground<T> implements PagerDelegate<T> {

    private FragmentManager fragmentManager;

    public SpotlightFullBackground(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final T base, int position) {
        Context context = container.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PagerSpotlight01Binding binding = DataBindingUtil.inflate(inflater, R.layout.pager_spotlight_01, container, false);
        if (base instanceof SpotlightVM){
            final SpotlightVM spotlight = (SpotlightVM) base;
            binding.setItem(spotlight);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailsFragment detailsFragment = DetailsFragment.newInstance(spotlight.getCategoryId(),spotlight.getArticleId(), spotlight.getName());
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, detailsFragment, detailsFragment.getClass().getName())
                            .addToBackStack(detailsFragment.getClass().getName())
                            .commit();
                }
            });
        }


        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public boolean isChosenForDisplay(T item) {
        return item instanceof SpotlightVM;
    }
}
