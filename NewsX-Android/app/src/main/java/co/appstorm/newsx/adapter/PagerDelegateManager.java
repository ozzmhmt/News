package co.appstorm.newsx.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class PagerDelegateManager<T> {
    static final int FALLBACK_DELEGATE_VIEW_TYPE = Integer.MAX_VALUE - 1;

    protected SparseArrayCompat<PagerDelegate<T>> delegates = new SparseArrayCompat();
    protected ArrayList<T> objectList = new ArrayList<>();

    public Object instantiateItem(ViewGroup container, int position) {

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            PagerDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isChosenForDisplay(objectList.get(position))) {
                return delegate.instantiateItem(container,objectList.get(position),position);
            }
        }


        throw new NullPointerException(
                "No AdapterDelegate added that matches position=" + position + " in data source");
    }

    public PagerDelegateManager(){
        objectList = new ArrayList<>();
    }

    public PagerDelegateManager(List<T> objects){
        this.objectList.addAll(objects);
    }

    public void addDataList(List<T> objects){
        addDataList(0,objects);
    }

    public void addDataList(int start, List<T> objects){
        this.objectList.addAll(start,objects);
    }

    public void replaceDataList(List<T> objects){
        this.objectList.clear();
        this.objectList.addAll(objects);
    }

    public List<T> getDataList(){
        return this.objectList;
    }

    public PagerDelegateManager<T> addDelegate(PagerDelegate<T>  delegate){
        int viewType = delegates.size();
        while (delegates.get(viewType) != null) {
            viewType++;
            /*if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
                throw new IllegalArgumentException(
                        "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.");
            }*/
        }
        return addDelegate(viewType,delegate);
    }

    public PagerDelegateManager<T> addDelegate(int viewType, PagerDelegate<T> delegate){
        return addDelegate(viewType,false,delegate);
    }

    public PagerDelegateManager<T> addDelegate(int viewType, boolean allowReplacingDelegate,
                                                  @NonNull PagerDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        if (viewType == FALLBACK_DELEGATE_VIEW_TYPE) {
            throw new IllegalArgumentException("The view type = "
                    + FALLBACK_DELEGATE_VIEW_TYPE
                    + " is reserved for fallback adapter delegate (see setFallbackDelegate() ). Please use another view type.");
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered AdapterDelegate is "
                            + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    public PagerDelegateManager<T> removeDelegate(@NonNull PagerDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * Removes the adapterDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public PagerDelegateManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }
}
