package co.appstorm.newsx.adapter.delegates;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.appstorm.newsx.Const;
import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.PagerDelegate;
import co.appstorm.newsx.databinding.PagerIntroduce01Binding;
import co.appstorm.newsx.model.IBase;
import co.appstorm.newsx.model.IIntroduce;
import co.appstorm.newsx.model.event.OnClickNextAndBeforeListener;

/**
 * Created by Ralphilius on 4/20/2017.
 */

public class IntroPager01 implements PagerDelegate<IBase> {

    private PagerIntroduce01Binding binding;

    public IntroPager01() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, final IBase base, final int position) {
        Context context = container.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Show Introduce
        binding = DataBindingUtil.inflate(inflater, R.layout.pager_introduce_01, container, false);
        IIntroduce introduce = (IIntroduce) base;
        binding.setItem(introduce);

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    public PagerIntroduce01Binding getBinding() {
        return binding;
    }

    @Override
    public boolean isChosenForDisplay(IBase item) {
        return Const.USE_INTERNET_INTRO;
    }
}
