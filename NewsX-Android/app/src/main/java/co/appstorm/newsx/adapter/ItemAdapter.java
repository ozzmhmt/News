package co.appstorm.newsx.adapter;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;-
import java.util.ArrayList;
import java.util.List;

import co.appstorm.newsx.adapter.delegates.ItemArticle01;
import co.appstorm.newsx.adapter.delegates.ItemArticle02;
import co.appstorm.newsx.adapter.delegates.ItemArticle03;
import co.appstorm.newsx.adapter.delegates.ItemCheckListArticle;
import co.appstorm.newsx.adapter.delegates.ItemWebviewArticle;
import co.appstorm.newsx.helpers.NavigationManager;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class ItemAdapter<T> extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<T>> delegatesManager;
    private List<T> items;
    private int adCount;

    // When you want to add an adapter
    public ItemAdapter(Activity activity, NavigationManager navigationManager) {
        this(activity, new ArrayList<T>(), navigationManager, null);
    }

    // When you want to show the list item
    public ItemAdapter(Activity activity, List<T> items, NavigationManager navigationManager) {
        this(activity, items, navigationManager, null);
    }

    // When you want to show the progress bar, if you use it. Always remember that visible or gone
    public ItemAdapter(Activity activity, NavigationManager navigationManager, ProgressBar progressBar) {
        this(activity, new ArrayList<T>(), navigationManager, progressBar);
    }

    private ItemAdapter(Activity activity, List<T> items, NavigationManager navigationManager, ProgressBar progressBar) {
        this.items = items;
        delegatesManager = new AdapterDelegatesManager<>();
        // This delegate will display default if you don't set catalog_type.
        delegatesManager.setFallbackDelegate(new ItemArticle01<T>(activity, navigationManager));
        delegatesManager.addDelegate(new ItemArticle01<T>(activity, navigationManager));
        delegatesManager.addDelegate(new ItemArticle02<T>(activity, navigationManager));
        delegatesManager.addDelegate(new ItemArticle03<T>(activity, navigationManager));
        delegatesManager.addDelegate(new ItemCheckListArticle<T>(activity));
        delegatesManager.addDelegate(new ItemWebviewArticle<T>(activity, progressBar));
        //delegatesManager.addDelegate(new ItemFacebookNativeAd<T>(activity));
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(T item, int index){
        this.items.add(index,item);
        notifyItemChanged(index);
    }

    public void addItem(T item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void increaseAdCount(int number){
        adCount += number;
    }

    public int getAdCount(){
        return adCount;
    }

    public void replaceItems(List<T> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @BindingAdapter(value = {"imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
