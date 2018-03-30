package co.appstorm.newsx;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import co.appstorm.data.DataManager;
import co.appstorm.newsx.helpers.DataModule;
import co.appstorm.newsx.model.realm.MyRealmMigration;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        DataModule.setupData(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(2)
                .migration(new MyRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);
        DataManager.init(new DataManager.Builder()
                .contentful()
                .spaceId(getString(R.string.space_key_contenful))
                .accessToken(getString(R.string.access_key_contenful))
                .build());
    }
}
