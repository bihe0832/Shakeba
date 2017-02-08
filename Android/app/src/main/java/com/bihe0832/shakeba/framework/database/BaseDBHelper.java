package com.bihe0832.shakeba.framework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDBHelper extends SQLiteOpenHelper {
	public BaseDBHelper(Context ctx, String name, int version) {
		super (ctx, name, null, version);
	}
	
    public long insert(String table, String nullColumnHack, ContentValues values) {
		SQLiteDatabase db = null;
		long rowid = -1;

		try {
			db = getWritableDatabase();
			rowid = db.insert(table, nullColumnHack, values);
		} catch (Exception e) {
			e.printStackTrace();
			rowid = -1;
		} finally {
		}

		return rowid;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = null;
		int rows = 0;
		
		try {
			db = getWritableDatabase();
			rows = db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			rows = 0;
		} finally {
		}

		return rows;
    }

    public int update(String table, ContentValues values, String whereClause,
            String[] whereArgs) {
		SQLiteDatabase db = null;
		int rows = 0;

		try {
			db = getWritableDatabase();
			rows = db.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			rows = 0;
		} finally {
		}

		return rows;
    }

    public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy, String limit) {
		SQLiteDatabase db = null;
		Cursor cursor = null;

		try {
			db = getReadableDatabase();
			cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy, limit);
		} catch (Exception e) {
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
			cursor = null;
		} finally {

		}

		return cursor;
	}
}
