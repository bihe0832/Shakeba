package com.bihe0832.shakeba.module.game.adventure;

import android.database.Cursor;

import com.bihe0832.shakeba.framework.database.BaseTableModel;
import com.bihe0832.shakeba.framework.database.ShakebaDBHelper;
import com.bihe0832.shakeba.libware.util.Math;

public class AdventureTableModel extends BaseTableModel {
    public static final String TABLE_NAME = "t_adventure";

    private static final String col_type = "item_type";
    private static final String col_content = "content";

    public static AdventureItem getAdventureItemByType() {
        String[] columns = null;
        String selection =  null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        int randNum = Math.getRandNumByLimit(1,getTableRecordNums());
        String limit = randNum + ", 1 ";
        Cursor cursor = ShakebaDBHelper.getInstance().query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        AdventureItem item = getColumnData(cursor);

        if (cursor != null) {
            cursor.close();
        }

        return item;
    }

    private static AdventureItem getColumnData(Cursor cursor) {
        AdventureItem item = new AdventureItem();
        try {
            if (null != cursor && cursor.getCount() > 0) {
                cursor.moveToFirst();

                item.type = getIntByName(cursor, col_type);
                String content = getStringByName(cursor, col_content);
                item.content = (content == null ? "" : content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private static int getTableRecordNums() {
        String[] columns = null;
        String selection =  null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        Cursor cursor = ShakebaDBHelper.getInstance().query(TABLE_NAME, columns,
                selection, selectionArgs, groupBy, having, orderBy, limit);
        try {
            return cursor.getCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
