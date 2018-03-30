package co.appstorm.newsx.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.appstorm.newsx.R;
import co.appstorm.newsx.adapter.ItemAdapter;
import co.appstorm.newsx.databinding.FragmentRecyclerBinding;
import co.appstorm.newsx.model.realm.RealmRecentItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class RecentlyViewFragment extends BaseFragment {

    private FragmentRecyclerBinding binding;
    private ItemAdapter<RealmRecentItem> adapter;

    public static RecentlyViewFragment newInstance() {
        return new RecentlyViewFragment();
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
        binding.toolbar.setTitle(R.string.recently_viewed);
        ((TextView) binding.toolbar.findViewById(R.id.toolbar_title)).setText(R.string.recently_viewed);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMain().backToMainList();
            }
        });
        binding.toolbar.inflateMenu(R.menu.menu_recentlyview);

        onMenuRemoveClick();
    }

    private void onMenuRemoveClick() {
        binding.toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_remove) {
                    /*List<IBase> newsList = DataModule.getNewsList();
                    for (IBase base : newsList) {
                        IArticle article = (IArticle) base;
                        String articleId = PreferenceUtils.getInstace(getContext()).getRecentlyView(article.getCategoryId(), article.getArticleId());
                        if (!articleId.equals("")) {
                            PreferenceUtils.getInstace(getContext()).removeRecentlyView(article.getCategoryId(), article.getArticleId());

                            adapter.clearItems();
                        }
                    }*/
                    return true;
                }
                return false;
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new ItemAdapter<>(getActivity(), getNavigationManager());
        binding.recyclerView.setAdapter(adapter);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmRecentItem> results = realm.where(RealmRecentItem.class).findAll();
        adapter.addItems(results);
    }

    @Override
    public void onFragmentVisible() {
        showUpdate(false);
    }
}
