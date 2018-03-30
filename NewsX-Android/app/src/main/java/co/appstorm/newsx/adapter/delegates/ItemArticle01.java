package co.appstorm.newsx.adapter.delegates;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.databinding.ItemNews01Binding;
import co.appstorm.newsx.fragments.DetailsFragment;
import co.appstorm.newsx.helpers.NavigationManager;
import co.appstorm.newsx.model.IArticle;
import co.appstorm.newsx.model.ICatalogType;
import co.appstorm.newsx.model.realm.RealmArticleVM;

/**
 * Created by Ralphilius on 4/23/2017.
 */

public class ItemArticle01<T> extends AdapterDelegate<List<T>> {
    private LayoutInflater inflater;
    private NavigationManager navigationManager;

    public ItemArticle01(Activity activity, NavigationManager navigationManager) {
        this.inflater = activity.getLayoutInflater();
        this.navigationManager = navigationManager;
    }

    @Override
    protected boolean isForViewType(@NonNull List<T> items, int position) {
        return (items.get(position) instanceof IArticle || items.get(position) instanceof ICatalogType)
                && ((ICatalogType) items.get(position)).getCatalogType() != null
                && ((ICatalogType) items.get(position)).getCatalogType().equals("catalog_type_1");
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ItemNews01Binding binding = DataBindingUtil.inflate(inflater, R.layout.item_news_01, parent, false);
        return new TextOnlyViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<T> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TextOnlyViewHolder vh = (TextOnlyViewHolder) holder;
        if (items.get(position) != null) {
            final IArticle news;
            if (items.get(position) instanceof IArticle) {
                news = (IArticle) items.get(position);
            } else if (items.get(position) instanceof RealmArticleVM) {
                news = ((RealmArticleVM) items.get(position)).getArticle();
            } else {
                return;
            }

            vh.binding.setItem(news);
            vh.binding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (navigationManager != null) {
                        String categoryId = news.getCategoryId();
                        navigationManager.addFragment(DetailsFragment.newInstance(categoryId, news.getArticleId(), news.getName()));
                    }
                }
            });

        }
    }

    private class TextOnlyViewHolder extends RecyclerView.ViewHolder {
        ItemNews01Binding binding;

        TextOnlyViewHolder(ItemNews01Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
