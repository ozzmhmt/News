package co.appstorm.newsx.model.event;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface OnClickNextAndBeforeListener {
    void onBefore(int pos);

    void onNext(int pos);
}
