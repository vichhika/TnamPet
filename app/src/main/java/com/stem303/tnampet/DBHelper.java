package com.stem303.tnampet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.stem303.tnampet.ui.recycleView.TnampetItem;

public class BookmarkDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Tnampet";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "bookmark";
    public static final String ID = "id";
    public static final String title = "title";
    public static final String intro_content = "intro_content";
    public static final String side_effect = "side_effect";
    public static final String side_effect_content = "side_effect_content";
    public static final String warning = "warning";
    public static final String warning_content = "warning_content";
    public static final String usage = "usage";
    public static final String usage_content = "usage_content";

    private static String CREATE_BOOKMARK_TABLE = "CREATE TABLE bookmark (\n" +
            "        id    INTEGER,\n" +
            "        title TEXT,\n" +
            "        intro_content TEXT,\n" +
            "        side_effect   TEXT,\n" +
            "        side_effect_content   TEXT,\n" +
            "        warning       TEXT,\n" +
            "        warning_content      TEXT,\n" +
            "        usage TEXT,\n" +
            "        usage_content TEXT,\n" +
            "        PRIMARY KEY(id));";
    private static String CREATE_HOME_TABLE = "CREATE TABLE home (\n" +
            "        id    INTEGER,\n" +
            "        title TEXT,\n" +
            "        intro_content TEXT,\n" +
            "        side_effect   TEXT,\n" +
            "        side_effect_content   TEXT,\n" +
            "        warning       TEXT,\n" +
            "        warning_content      TEXT,\n" +
            "        usage TEXT,\n" +
            "        usage_content TEXT,\n" +
            "        PRIMARY KEY(id));";

    public BookmarkDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_BOOKMARK_TABLE);
        sqLiteDatabase.execSQL(CREATE_HOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void onInsert(TnampetItem tnampetItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",tnampetItem.getId());
        contentValues.put("title",tnampetItem.getTitle());
        contentValues.put("intro_content",tnampetItem.getIntro_content());
        contentValues.put("side_effect",tnampetItem.getSide_effect());
        contentValues.put("side_effect_content",tnampetItem.getSide_effect_content());
        contentValues.put("warning",tnampetItem.getWarning());
        contentValues.put("warning_content",tnampetItem.getWarning_content());
        contentValues.put("usage",tnampetItem.getUsage());
        contentValues.put("usage_content",tnampetItem.getUsage_content());
        db.insert(TABLE_NAME,null,contentValues);
    }

    public void onClear(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "DELETE FROM bookmark";
        sqLiteDatabase.execSQL(sql);
    }

    public Cursor onRetrive(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM bookmark WHERE id = "+id;
        return db.rawQuery(sql,null,null);
    }

    public Cursor onRetriveAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM bookmark";
        return db.rawQuery(sql,null,null);
    }

    public void onRemove(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "DELETE FROM bookmark WHERE id = "+id;
        sqLiteDatabase.execSQL(sql);
    }
}
