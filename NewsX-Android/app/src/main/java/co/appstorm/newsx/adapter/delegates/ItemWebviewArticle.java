package co.appstorm.newsx.adapter.delegates;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

import co.appstorm.newsx.R;
import co.appstorm.newsx.databinding.ItemWebviewBinding;
import co.appstorm.newsx.model.DetailViewModel;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class ItemWebviewArticle<T> extends AdapterDelegate<List<T>> {
    public final static String TAG = ItemWebviewArticle.class.getSimpleName();
    private LayoutInflater inflater;
    private ProgressBar progressBar;

    public ItemWebviewArticle(Activity activity, ProgressBar progressBar) {
        this.inflater = activity.getLayoutInflater();
        this.progressBar = progressBar;
    }

    @Override
    protected boolean isForViewType(@NonNull List<T> items, int position) {
        return (items.get(position) instanceof DetailViewModel)
                && ((DetailViewModel) items.get(position)).getContent() != null;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        ItemWebviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_webview, parent, false);
        return new WebviewViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<T> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        WebviewViewHolder vh = (WebviewViewHolder) holder;
        if (items.get(position) != null) {
            final DetailViewModel news = (DetailViewModel) items.get(position);
            initWebview(vh, news.getContent(),news.getExternalContent());
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebview(WebviewViewHolder vh, String html, String url) {
        if ((html == null || html.isEmpty()) && (url == null || url.isEmpty())) {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            return;
        }

        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
        vh.binding.webView.getSettings().setJavaScriptEnabled(true);
        vh.binding.webView.setVerticalScrollBarEnabled(true);
        if (url != null || (url != null  && !url.isEmpty())){
            vh.binding.webView.loadUrl(url);
        } else {
            vh.binding.webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        }

        vh.binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
            }
        });
    }

    private class WebviewViewHolder extends RecyclerView.ViewHolder {
        ItemWebviewBinding binding;

        WebviewViewHolder(ItemWebviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
