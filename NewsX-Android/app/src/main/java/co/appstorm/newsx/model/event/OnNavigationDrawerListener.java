package co.appstorm.newsx.model.event;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

/**
 * Created by ozzmhmt on 4/2/2018.
 */
public interface OnNavigationDrawerListener {
    ActionBarDrawerToggle onAddDrawerListener(Toolbar toolbar);

    void onRemoveDrawerListener(DrawerLayout.DrawerListener toggle);
}
