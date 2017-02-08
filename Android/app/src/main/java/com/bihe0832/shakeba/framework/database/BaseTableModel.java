package com.bihe0832.shakeba.framework.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bihe0832.shakeba.libware.util.TextUtils;


public abstract class BaseTableModel {
    
    public static int deleteAll (BaseDBHelper helper, String tableName) { 
        int rows = 0;

		try {
			SQLiteDatabase db = helper.getWritableDatabase();
			rows = db.delete(tableName, null, null);
		} catch (Exception e) {
			e.printStackTrace();
			rows = 0;
		} finally {
		}

		return rows;
    }
    
	public static void putValues(ContentValues cv, String key, String value) {
		if (!TextUtils.ckIsEmpty(value)) {
			cv.put(key, value);
		} else {
			cv.put(key, "");
		}
	}

	public static void putValues(ContentValues cv, String key, int value) {
		cv.put(key, value);
	}

    public static void putValues(ContentValues cv, String key, boolean value) {
        if (value) {
            cv.put(key, "1");
        } else {
            cv.put(key, "0");
        }
    }

	public static String getStringByName(Cursor c, String columnName) {
		return c.getString(c.getColumnIndex(columnName));
	}

	protected static int getIntByName(Cursor c, String columnName) {
		return c.getInt(c.getColumnIndex(columnName));
	}

	protected static long getLongByName(Cursor c, String columnName) {
		return c.getLong(c.getColumnIndex(columnName));
	}
}
