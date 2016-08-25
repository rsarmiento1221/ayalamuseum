package com.stratpoint.ayalamuseum.database.object;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by raymondsarmiento on 8/16/16.
 */
public class Filter extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private byte[] image;
    private String type;
    private int resourceId;

    public static final String TYPE_DEFAULT = "default_filter";
    public static final String TYPE_CUSTOM = "custom_filter";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}