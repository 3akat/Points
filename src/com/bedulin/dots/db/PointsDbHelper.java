
package com.bedulin.dots.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.bedulin.dots.db.entity.Game;

import java.util.HashMap;

public class PointsDbHelper extends SQLiteOpenHelper {
	private static final String TAG = PointsDbHelper.class.getName();

	private static final String DB_NAME = "Points.db";

	private static final int DB_VERSION = 0;

	private static final String QUERY_DROP_TABLE = "DROP TABLE IF EXISTS ";

	private static final HashMap<String, String> CREATE_MAP;
	static {
		CREATE_MAP = new HashMap<String, String>();
		CREATE_MAP.put(Game.TABLE_NAME, Game.QUERY_CREATE);
	}

	public PointsDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Create database: " + DB_NAME);
		for (String query : CREATE_MAP.values()) {
			db.execSQL(query);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrade db from: " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		for (String tableName : CREATE_MAP.keySet()) {
			db.execSQL(QUERY_DROP_TABLE + tableName);
		}
		onCreate(db);
	}

}
