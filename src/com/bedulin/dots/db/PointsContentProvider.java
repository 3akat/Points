
package com.bedulin.dots.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.bedulin.dots.db.entity.Game;

public class PointsContentProvider extends ContentProvider {

    private static final String TAG = PointsContentProvider.class.getName();

    public static final String PROVIDER_NAME = "com.beeline.beein.PointsContentProvider";

    private PointsDbHelper dbHelper;

    private static final int AREAS = 1;

    private static final int AREAS_ID = 2;

    private static final int EVENTS = 3;

    private static final int EVENTS_ID = 4;

    private static final int PHOTOS = 5;

    private static final int PHOTOS_ID = 6;

    private static final int AUDIOS = 7;

    private static final int AUDIOS_ID = 8;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(PROVIDER_NAME, Game.TABLE_NAME, EVENTS);
        matcher.addURI(PROVIDER_NAME, Game.TABLE_NAME + "/#", EVENTS_ID);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        dbHelper = new PointsDbHelper(getContext());
        return dbHelper != null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "Delete: " + uri);
        String table = null, where = null;
        where = selection;
        switch (matcher.match(uri)) {
            case EVENTS:
                table = Game.TABLE_NAME;
                break;
            case EVENTS_ID:
                table = Game.TABLE_NAME;
                if (!TextUtils.isEmpty(selection)) {
                    where = " AND (" + selection + ")";
                }
                where = Game._ID + "=" + uri.getLastPathSegment() + where;
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(table, where, selectionArgs);
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: " + uri);
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Insert: " + uri);
        Uri contentUri = null;
        String table = null;
        switch (matcher.match(uri)) {

            case EVENTS:
                contentUri = Game.CONTENT_URI;
                table = Game.TABLE_NAME;
                break;
            case EVENTS_ID:
                contentUri = Game.CONTENT_URI;
                table = Game.TABLE_NAME;
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            id = db.replace(table, null, values);
            db.setTransactionSuccessful();
            // getContext().getContentResolver().notifyChange(uri, null);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return Uri.withAppendedPath(contentUri, String.valueOf(id));
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "Query: " + uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (matcher.match(uri)) {

            case EVENTS:
                queryBuilder.setTables(Game.TABLE_NAME);
                break;
            case EVENTS_ID:
                queryBuilder.setTables(Game.TABLE_NAME);
                queryBuilder.appendWhere(Game._ID + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        Cursor cursor = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "Update: " + uri);
        Uri contentUri = null;
        String table = null;
        switch (matcher.match(uri)) {

            case EVENTS:
                contentUri = Game.CONTENT_URI;
                table = Game.TABLE_NAME;
                break;
            case EVENTS_ID:
                contentUri = Game.CONTENT_URI;
                table = Game.TABLE_NAME;
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        long id = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            id = db.update(table, values, selection, selectionArgs);
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return (int) id;
    }

}
