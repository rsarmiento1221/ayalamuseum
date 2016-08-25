package com.stratpoint.ayalamuseum.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.stratpoint.ayalamuseum.R;
import com.stratpoint.ayalamuseum.database.object.Filter;
import io.realm.Realm;
import io.realm.RealmResults;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymondsarmiento on 8/12/16.
 */
public class DatabaseManager extends Manager {
    private Realm realm;

    DatabaseManager(Context context) {
        super(context);
        realm = Realm.getInstance(context);
    }

    public void insertDefaultFilters() {
        String[] resNames = {"Body Sash", "Cheek Blush", "Ear Earring", "Eye Glass", "Face Elephant",
            "Teapot", "Bowler Hat", "Mouth Pipe"};
        int[] resIds = { R.drawable.img_body_sash, R.drawable.img_cheek_blush, R.drawable.img_ear_earring,
            R.drawable.img_eyes_glasses, R.drawable.img_face_elephant, R.drawable.img_float_teapot,
                R.drawable.img_head_bowlerhat, R.drawable.img_mouth_pipe};

        List<Filter> filters = new ArrayList<>();

        long id = getNextPrimaryKeyValue(Filter.class);

        for (int i=0;i<resIds.length;i++) {
            Drawable drawable = ContextCompat.getDrawable(context, resIds[i]);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            Filter filter = new Filter();

            id++;
            filter.setId(id);
            filter.setName(resNames[i]);
            filter.setImage(byteArrayOutputStream.toByteArray());
            filter.setType(Filter.TYPE_DEFAULT);
            filter.setResourceId(resIds[i]);
            filters.add(filter);
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(filters);
        realm.commitTransaction();
    }

    private synchronized long getNextPrimaryKeyValue(Class realmObjectClass) {
        return realm.where(realmObjectClass).maximumInt("id");
    }

    public Filter[] getDefaultFilters() {
        RealmResults<Filter> results = realm.where(Filter.class)
                                            .equalTo("type", Filter.TYPE_DEFAULT)
                                            .findAll();
        Filter[] filters = new Filter[results.size()];
        results.toArray(filters);
        return filters;
    }
}