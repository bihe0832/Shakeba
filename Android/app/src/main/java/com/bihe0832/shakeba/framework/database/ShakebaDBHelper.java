package com.bihe0832.shakeba.framework.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bihe0832.shakeba.framework.Shakeba;

public class ShakebaDBHelper extends BaseDBHelper {
    private static final String DB_NAME = ShakebaRawDBHelper.DATABASE_NAME;
    // TODO CHECK 检查DB版本是否需要更新
    private static final int DB_VERSION = 1;
    
    private static volatile ShakebaDBHelper instance;
    
    public static ShakebaDBHelper getInstance() {
        if (instance == null) {
            synchronized (ShakebaDBHelper.class) {
                if (instance == null) {
                    instance = new ShakebaDBHelper(Shakeba.getInstance().getApplicationContext(), DB_NAME, DB_VERSION);
                }
            }
        }
        return instance;
    }

    private ShakebaDBHelper(Context ctx, String name, int version) {
        super(ctx, name, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressLint("Override")
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOk(){
        return ShakebaRawDBHelper.getInstance().isDBOK();
    }
}
