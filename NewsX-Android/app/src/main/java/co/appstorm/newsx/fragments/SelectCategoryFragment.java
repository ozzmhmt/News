package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.ItemAdapter;
import co.appstorm.newsx.adapter.SectionsPagerAdapter;
import co.appstorm.newsx.databinding.FragmentSelectCategoryBinding;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.model.realm.RealmCategory;
import co.appstorm.newsx.utils.PreferenceUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class SelectCategoryFragment<T extends CategoryVM> extends BaseFragment implements View.OnClickListener {

    private FragmentSelectCategoryBinding binding;
    private List<CategoryVM> categories;
    private String name;
    private SectionsPagerAdapter<CategoryVM> adapter;

    public static <U extends CategoryVM> SelectCategoryFragment newInstance(String name, List<U> categories) {
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        fragment.categories = categories;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_category, container, false);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            if (name != null) {
                if (name.equals(""))
                    PreferenceUtils.getInstace(getContext()).putShowSelectCategory(true);
                else {
                    binding.layoutText.setVisibility(View.GONE);
                    binding.btnContinue.setVisibility(View.GONE);
                    getUpdateButton().setOnClickListener(this);
                }
            }

        }

        setupRecyclerView();
        binding.btnContinue.setOnClickListener(this);
        return binding.getRoot();
    }


    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

        ItemAdapter<CategoryVM> adapter = new ItemAdapter<>(getActivity(), categories, getNavigationManager());
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContinue || view.getId() == R.id.btnUpdate) {
            if (getNavigationManager() != null) {
                if (name.equals("Tab")) {
                    if (MainFragment.getInstance().getAdapter() != null)
                        adapter = MainFragment.getInstance().getAdapter();
                    else
                        return;

                    updateListContent(adapter);
                } else {
                    getNavigationManager().getCurrentFragment().getFragmentManager().popBackStack();
                    getNavigationManager().addFragment(MainFragment.getInstance());
                }
            }
        }
    }

    private void updateListContent(SectionsPagerAdapter<CategoryVM> adapter) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCategory> results = realm.where(RealmCategory.class).findAllSorted("position", Sort.ASCENDING);
        List<String> fragmentTitleList = new ArrayList<>();
        if (results.size() == 0){
            adapter.clearFragments();
        } else {
            for (RealmCategory category: results){
                fragmentTitleList.add(category.getCategoryName());
                adapter.addFragment(CategoryFragment.newInstance(category.getCategoryId()), category.getCategoryName());
            }
        }

        fragmentTitleList.add(getString(R.string.add_tab));
        adapter.refineFragmentList(fragmentTitleList);
        adapter.notifyDataSetChanged();
        MainFragment.getInstance().switchTab(adapter.getCount() - 1);
    }

    @Override
    public void onFragmentVisible() {
        showSkip(false);
    }
}
