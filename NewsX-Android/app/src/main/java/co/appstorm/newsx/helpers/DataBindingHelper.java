package co.appstorm.newsx.helpers;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.appstorm.newsx.utils.DateUtils;

/**
 * Created by ozzmhmt on 4/2/2018.
 */
// Use to define for the data binding
public class DataBindingHelper {
    private DataBindingHelper() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url == null || url.equals(""))
            imageView.setVisibility(View.GONE);

        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

    @BindingAdapter("src")
    public static void src(ImageView imageView, int src) {
        Context context = imageView.getContext();
        Glide.with(context).load("").placeholder(src).into(imageView);
    }

    @BindingAdapter("dateToList")
    public static void setDateToList(TextView view, String date) {
        if (date == null || date.equals(""))
            view.setVisibility(View.GONE);

        if (date != null) {
            view.setVisibility(View.VISIBLE);
            view.setText(DateUtils.displayTodayDateList(date));
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, String text) {
        if (text == null || text.equals(""))
            view.setVisibility(View.GONE);

        if (text != null) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }
}
