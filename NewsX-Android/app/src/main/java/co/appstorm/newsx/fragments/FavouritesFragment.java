package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.ItemAdapter;
import co.appstorm.newsx.databinding.FragmentRecyclerBinding;
import co.appstorm.newsx.model.realm.RealmFavourite;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class FavouritesFragment extends BaseFragment {

    private FragmentRecyclerBinding binding;

    public static FavouritesFragment newInstance() {
        return new FavouritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);
        setUpToolbar();
        setupRecyclerView();
        return binding.getRoot();
    }

    private void setUpToolbar() {
        binding.toolbar.setTitle(R.string.favourites);
        ((TextView) binding.toolbar.findViewById(R.id.toolbar_title)).setText(R.string.favourites);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMain().backToMainList();
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        ItemAdapter<RealmFavourite> adapter = new ItemAdapter<>(getActivity(), getNavigationManager());
        binding.recyclerView.setAdapter(adapter);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmFavourite> results = realm.where(RealmFavourite.class).findAll();
        adapter.addItems(results);
    }

    @Override
    public void onFragmentVisible() {
        showUpdate(false);
    }
}
