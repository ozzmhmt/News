package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import android.databinding.*;

import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.PagerDelegateManager;
import co.appstorm.newsx.adapter.ViewPagerAdapter;
import co.appstorm.newsx.adapter.delegates.IntroPager01;
import co.appstorm.newsx.adapter.delegates.IntroPager02;
import co.appstorm.newsx.databinding.FragmentIntroduceBinding;
import co.appstorm.newsx.databinding.FragmentSpotlightBinding;
import co.appstorm.newsx.helpers.DataModule;
import co.appstorm.newsx.model.IBase;
import co.appstorm.newsx.model.event.ListenerEventClicked;
import co.appstorm.newsx.model.event.OnClickNextAndBeforeListener;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class IntroFragment extends BaseFragment implements ListenerEventClicked, View.OnClickListener {

    FragmentIntroduceBinding binding;
    IntroPager01 introPager01;
    IntroPager02 introPager02;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduce, container, false);

        setupViewPagerAndIndicator();
        setupListener();

        return binding.getRoot();
    }

    private void setupViewPagerAndIndicator() {
        PagerDelegateManager<IBase> delegateManager = new PagerDelegateManager<>(DataModule.getIntroduces());
        introPager01 = new IntroPager01();
        introPager02 = new IntroPager02();
        delegateManager.addDelegate(introPager01);
        delegateManager.addDelegate(introPager02);
        ViewPagerAdapter adapter = new ViewPagerAdapter(delegateManager);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.setListenerEventClicked(this);
        setupIndicator(DataModule.getIntroduces());
    }

    private void setupIndicator(List<IBase> introduces) {
        binding.indicator.setViewCount(introduces.size());
        binding.viewPager.setIndicator(binding.indicator);
    }

    private void setupListener() {
        binding.ibtnBefore.setOnClickListener(this);
        binding.ibtnNext.setOnClickListener(this);
    }

    // Úe to update data in position
    @Override
    public void listenerEventClicked(int position) {
        if (DataModule.getIntroduces().size() > 0) {
            binding.ibtnNext.setVisibility(View.VISIBLE);
            binding.ibtnNext.setText(R.string.next);
        }
        if (position > 0) {
            binding.ibtnBefore.setVisibility(View.VISIBLE);
        }
        if (position == DataModule.getIntroduces().size() - 1) {
            binding.ibtnNext.setVisibility(View.VISIBLE);
            binding.ibtnNext.setText(R.string.done);
        }
        if (position == 0 || DataModule.getIntroduces().size() == 1) {
            binding.ibtnBefore.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int pos = binding.viewPager.getCurrentItem();
        if (view.getId() == R.id.ibtnNext) {
            System.out.println("onNext: " + pos);
            if (pos == DataModule.getIntroduces().size() - 1) {
                // Back when it is last item
                getNavigationManager().getCurrentFragment().getFragmentManager().popBackStack();
            } else {
                binding.viewPager.setCurrentItem(pos + 1);
            }
        } else if (view.getId() == R.id.ibtnBefore) {
            System.out.println("onBefore: " + pos);
            binding.viewPager.setCurrentItem(pos - 1);
        }
    }

    @Override
    public void onFragmentVisible() {
        showSkip(false);
        showUpdate(false);
    }
}
