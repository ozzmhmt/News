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
import co.appstorm.newsx.databinding.ItemCategorySelectingBinding;
import co.appstorm.newsx.model.CategoryVM;
import co.appstorm.newsx.model.realm.RealmCategory;
import co.appstorm.newsx.model.realm.RealmHelper;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class ItemCheckListArticle<T> extends AdapterDelegate<List<T>> {
    private Activity activity;
    private LayoutInflater inflater;

    public ItemCheckListArticle(Activity activity) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<T> items, int position) {
        return items.get(position) instanceof CategoryVM;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ItemCategorySelectingBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_category_selecting, parent, false);
        return new CheckListViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<T> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final CheckListViewHolder vh = (CheckListViewHolder) holder;
        if (items.get(position) instanceof CategoryVM){
            final CategoryVM category = (CategoryVM) items.get(position);
            vh.binding.setItem(category);
            vh.binding.checkCategory.setChecked(RealmHelper.isSavedToRealm(RealmCategory.class,"categoryId",category.getCategoryId()));

            vh.binding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vh.binding.checkCategory.isChecked()) {
                        vh.binding.checkCategory.setChecked(false);
                        //PreferenceUtils.getInstace(activity).removeCategorySelected(category.getCategoryId());
                        RealmHelper.removeCategory(category);
                    } else {
                        vh.binding.checkCategory.setChecked(true);
                        //PreferenceUtils.getInstace(activity).putCategorySelected(category.getCategoryId());
                        RealmHelper.saveCategory(category);
                    }
                }
            });
        }

    }

    private class CheckListViewHolder extends RecyclerView.ViewHolder {
        ItemCategorySelectingBinding binding;

        CheckListViewHolder(ItemCategorySelectingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
