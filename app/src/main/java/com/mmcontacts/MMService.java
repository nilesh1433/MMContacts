package com.mmcontacts;

/**
 * Created by Nilesh Kumar Tiwari on 6/27/2015.
 */
import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

public class MMService extends Service {

    private int mContactCount;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContactCount = getContactCount();
        this.getContentResolver().registerContentObserver(
                ContactsContract.Contacts.CONTENT_LOOKUP_URI, true, mObserver);
    }

    private int getContactCount() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_LOOKUP_URI, null, null, null,
                    null);
            if (cursor != null) {
                return cursor.getCount();
            } else {
                return 0;
            }
        } catch (Exception ignore) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange,Uri uri) {
            super.onChange(selfChange);
            Log.d("TAG", uri.getPath());

            final int currentCount = getContactCount();
            if (currentCount < mContactCount) {
                // DELETE HAPPEN.
            } else if (currentCount == mContactCount) {
                // UPDATE HAPPEN.
            } else {
                // INSERT HAPPEN.
            }
            mContactCount = currentCount;
        }

    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAg", "destroyed");
        getContentResolver().unregisterContentObserver(mObserver);
    }

}
