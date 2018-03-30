package co.appstorm.newsx.model.realm;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class MyRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 1) {
            schema.create("RealmCategory")
                    .addField("categoryId", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("categoryName", String.class)
                    .addField("image", String.class)
                    .addField("position",int.class);
            oldVersion++;
        }
    }
}
