package co.appstorm.newsx.model.event;

import java.util.List;

import co.appstorm.newsx.model.IBase;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public interface OnCallbackListResponse {
    void onCallback(List<IBase> iBases);
}
