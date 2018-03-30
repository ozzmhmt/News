package co.appstorm.newsx.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.databinding.ItemCategoryHorizontalBinding;
import co.appstorm.newsx.databinding.ItemCategoryVerticalBinding;
import co.appstorm.newsx.model.Category;
import co.appstorm.newsx.model.Drawer;
import co.appstorm.newsx.model.event.OnClickCategoryListener;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class CategoryDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Drawer> drawers;
    private Category.CategoryType type;
    private OnClickCategoryListener categoryListener;

    public CategoryDrawerAdapter(List<Drawer> categories, Category.CategoryType type, OnClickCategoryListener categoryListener) {
        this.drawers = categories;
        this.type = type;
        this.categoryListener = categoryListener;
    }

    public void updateData(List<Drawer> data) {
        this.drawers = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (type == Category.CategoryType.HORIZONTAL) {
            ItemCategoryHorizontalBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_category_horizontal, parent, false);
            return new ViewHolderHorizontal(binding);
        } else {
            ItemCategoryVerticalBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_category_vertical, parent, false);
            return new ViewHolderVertical(binding);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (type == Category.CategoryType.HORIZONTAL) {
            ((ViewHolderHorizontal) holder).bindItem(drawers.get(position));
            ((ViewHolderHorizontal) holder).binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryListener.onCategoryClicked(
                            ((ViewHolderHorizontal) holder).drawer.getName());
                }
            });
        } else {
            ((ViewHolderVertical) holder).bindItem(drawers.get(position));
            ((ViewHolderVertical) holder).binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryListener.onCategoryClicked(
                            ((ViewHolderVertical) holder).drawer.getName());
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }

    private static class ViewHolderHorizontal extends RecyclerView.ViewHolder {
        Drawer drawer;
        ItemCategoryHorizontalBinding binding;

        ViewHolderHorizontal(ItemCategoryHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Bind category item to view. Show title anicon fetch from url
         *
         * @paramtem
         */
        void bindItem(Drawer item) {
            this.drawer = item;
            binding.setItem(item);
        }
    }

    private static class ViewHolderVertical extends RecyclerView.ViewHolder {
        Drawer drawer;
        ItemCategoryVerticalBinding binding;

        ViewHolderVertical(ItemCategoryVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Bind category item to vi. Show title and in fetch from url
         *
         * @param item
         */
        void bindItem(Drawer item) {
            this.drawer = item;
            binding.setItem(item);
        }
    }
}
