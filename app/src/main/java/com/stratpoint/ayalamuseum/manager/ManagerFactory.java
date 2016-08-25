package com.stratpoint.ayalamuseum.manager;

import android.content.Context;

import java.io.InvalidClassException;

/**
 * Created by raymondsarmiento on 8/12/16.
 */
public class ManagerFactory {

    public static final int TYPE_SHARED_PREFS = 1;
    public static final int TYPE_DATABASE = 2;

    public static Manager createManager(Context context, int type) {
        switch (type) {
            case TYPE_SHARED_PREFS:
                return new SharedPrefsManager(context);
            case TYPE_DATABASE:
                return new DatabaseManager(context);
        }

        throw new InvalidManagerException("Manager is invalid.");
    }

    public static class InvalidManagerException extends RuntimeException {

        public InvalidManagerException(String detailMessage) {
            super(detailMessage);
        }
    }
}
