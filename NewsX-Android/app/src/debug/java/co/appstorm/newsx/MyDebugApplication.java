package co.appstorm.newsx;

import com.facebook.stetho.Stetho;

/**
 * Created by Ralphilius on 4/18/2017.
 */

public class MyDebugApplication extends MyApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
