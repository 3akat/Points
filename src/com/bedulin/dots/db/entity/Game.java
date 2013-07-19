
package com.bedulin.dots.db.entity;

import java.util.Arrays;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

import com.bedulin.dots.db.PointsContentProvider;
import com.beeline.beein.db.BeeInContentProvider;
import com.beeline.beein.net.entity.EventEntity;

public class Game implements BaseColumns {

	public static final String TABLE_NAME = "EVENTS";

	public static final Uri CONTENT_URI = Uri.parse("content://" + PointsContentProvider.PROVIDER_NAME + "/"
			+ TABLE_NAME);

	// fields
	public static final String PLAYER = "player";

	public static final String X = "X";

	public static final String Y = "y";

	public static final String NEXT_MOVE = "next_move";

	public static final String PLAYER_ONE_TEMP_POINT_X = "PLAYER_ONE_TEMP_POINT_X";

	public static final String PLAYER_ONE_TEMP_POINT_Y = "PLAYER_ONE_TEMP_POINT_Y";

	public static final String PLAYER_TWO_TEMP_POINT_X = "PLAYER_TWO_TEMP_POINT_X";

	public static final String PLAYER_TWO_TEMP_POINT_Y = "PLAYER_TWO_TEMP_POINT_Y";

	public static final String QUERY_CREATE = "create table " + TABLE_NAME + "(" +
            _ID + " integer primary key, "
			+
            PLAYER + " integer, " +X + " text, "
			+ Y + " text, " + NEXT_MOVE + " text, " + PLAYER_ONE_TEMP_POINT_X + " text, " + PLAYER_ONE_TEMP_POINT_Y + " text, " + PLAYER_TWO_TEMP_POINT_X
			+ " text, " + PLAYER_TWO_TEMP_POINT_Y + " text, " + START_TIME + " text, " + END_TIME + " text, " + FLOORS + " text, "
			+ TIME + " text, " + SHARE_LINK + " text, " + FACEBOOK + " text, " + VK + " text, " + TWITTER + " text, "
			+ LAT + " real, " + LON + " real, " + SHOW_IN_LIST + " integer, " + MAP_TYPE + " integer, " + ITUNES
			+ " text);";


	private int mGameId;

	public Game(int gameId) {
		this.mGameId = gameId;
	}

	public int insertValues(Context context) {
		int count = 0;
		final ContentResolver resolver = context.getContentResolver();
		if (events != null && events.length != 0) {
			ContentValues values = new ContentValues();
			values.put(PLAYER, parentId);
			for (EventEntity event : events) {
				values.put(_ID, event.getId());
				values.put(X, event.getTitle());

				values.put(Y, event.getSubtitle());

				values.put(NEXT_MOVE, event.getType());

				values.put(PLAYER_ONE_TEMP_POINT_X, event.getDescription());

				values.put(PLAYER_ONE_TEMP_POINT_Y, event.getImageUrl());

				values.put(PLAYER_TWO_TEMP_POINT_X, String.valueOf(Long.parseLong(event.getStartDate()) * 1000));

				values.put(PLAYER_TWO_TEMP_POINT_Y, String.valueOf(Long.parseLong(event.getEndDate()) * 1000));

				values.put(START_TIME, event.getStartTime());

				values.put(END_TIME, event.getEndTime());

				values.put(FLOORS, Arrays.toString(event.getFloors()));

				values.put(TIME, event.getTime());

				values.put(SHARE_LINK, event.getShareLink());

				values.put(ALL_DAY, event.isAllday() ? 1 : 0);

				values.put(IS_PLANNED, event.isPlanned() ? 1 : 0);

				values.put(FACEBOOK, event.getFacebook());

				values.put(VK, event.getVk());

				values.put(TWITTER, event.getTwitter());

				values.put(ITUNES, event.getItunes());

				values.put(LAT, event.getLat());

				values.put(LON, event.getLon());

				values.put(SHOW_IN_LIST, event.isShow_in_list() ? 1 : 0);

				values.put(MAP_TYPE, event.getMap_type());

				resolver.insert(CONTENT_URI, values);
				new Audios(event.getAudios(), event.getId(), ParentType.EVENT).insertValues(context);
				new Photos(event.getPhotos(), event.getId(), ParentType.EVENT).insertValues(context);
				count++;
			}
		}
		return count;
	}
}
