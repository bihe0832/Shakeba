package com.bihe0832.shakeba.framework.database;

import android.database.sqlite.SQLiteDatabase;

import com.bihe0832.shakeba.framework.Shakeba;
import com.bihe0832.shakeba.libware.file.Logger;
import com.bihe0832.shakeba.libware.thread.ShakebaThreadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hardyshi on 17/2/7.
 */
public abstract class BaseRawDatabaseHelper {

    private boolean mIsDBOK = false;

    public BaseRawDatabaseHelper() {
        checkDBIsGood();
    }

    public void buildDatabase(){
        ShakebaThreadManager.getInstance().start(new Runnable() {
            @Override
            public void run() {
                buildDatabaseAsync();
            }
        });
    }

    public void buildDatabaseAsync(){
        File file = Shakeba.getInstance().getApplicationContext().getDatabasePath(getDatabaseName());
        if(!file.exists()){
            try {
                InputStream myInput = Shakeba.getInstance().getApplicationContext().getResources().openRawResource(getRawDatabaseResID());
                OutputStream myOutput = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer))>0){
                    myOutput.write(buffer, 0, length);
                }
                myOutput.close();
                myInput.close();

                mIsDBOK = checkDBIsGood();
            } catch (Exception e) {
                e.printStackTrace();
                mIsDBOK = false;
            }
        }
    }

    private boolean checkDBIsGood(){
        File file = Shakeba.getInstance().getApplicationContext().getDatabasePath(getDatabaseName());
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);
        int versionInRawDB = database.getVersion();
        int versionInCode = getDatabaseVersion();
        Logger.d("file Exist:" + file.exists() + ";versionInRawDB:" + versionInRawDB + ";versionInCode:" + versionInCode);
        if(file.exists()){
            if (versionInRawDB != versionInCode) {
                database.close();
                file.delete();
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
    public boolean isDBOK(){
        return mIsDBOK;
    }

    protected abstract int getDatabaseVersion();

    protected abstract String getDatabaseName();

    protected abstract int getRawDatabaseResID();
}
