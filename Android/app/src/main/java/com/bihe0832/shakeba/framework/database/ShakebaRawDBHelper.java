package com.bihe0832.shakeba.framework.database;

import com.bihe0832.shakeba.R;
/**
 * Created by hardyshi on 17/2/7.
 */
public class ShakebaRawDBHelper extends BaseRawDatabaseHelper{

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shakeba";

    private static volatile ShakebaRawDBHelper instance;
    public static ShakebaRawDBHelper getInstance() {
        if (instance == null) {
            synchronized (ShakebaRawDBHelper.class) {
                if (instance == null) {
                    instance = new ShakebaRawDBHelper();
                }
            }
        }
        return instance;
    }
    @Override
    protected int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    protected int getRawDatabaseResID() {
        return R.raw.shakeba;
    }
}
