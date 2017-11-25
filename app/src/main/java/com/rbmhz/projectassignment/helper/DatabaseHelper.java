package com.rbmhz.projectassignment.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rbmhz.projectassignment.DTO.CommentDto;
import com.rbmhz.projectassignment.DTO.PostDto;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "ProjectAssignment";
    public static final String TABLE_NAME = "PostTable";
    public static final String TABLE_NAME_COMMENT = "CommentTable";

    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String BODY = "body";


    public static final String ID_COMMENT = "idComment";
    public static final String PostID_COMMENT = "postId";
    public static final String NAME_COMMENT = "name";
    public static final String EMAIL_COMMENT = "email";
    public static final String BODY_COMMENT = "bodyComment";

    //database version
    private static final int DB_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    private static final String sql = "CREATE TABLE " + TABLE_NAME
            + "("
            + ID + " INT, "
            + USER_ID + " VARCHAR, "
            + TITLE + " VARCHAR,"
            + BODY + " VARCHAR );";

    private static final String sqlComment = "CREATE TABLE " + TABLE_NAME_COMMENT
            + "("
            + ID_COMMENT + " INT, "
            + PostID_COMMENT + " VARCHAR, "
            + NAME_COMMENT + " LONG, "
            + EMAIL_COMMENT + " LONG, "
            + BODY_COMMENT + " LONG );";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlComment);
        db.execSQL(sql);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlComment = "DROP TABLE IF EXISTS " + TABLE_NAME_COMMENT;
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        db.execSQL(sqlComment);
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.delete(TABLE_NAME_COMMENT, null, null);
    }

    public void addPost(PostDto postDto) {

        Log.d("Values-Got-db-post", postDto.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, postDto.getId());
        contentValues.put(USER_ID, postDto.getUserId());
        contentValues.put(TITLE, postDto.getTitle());
        contentValues.put(BODY, postDto.getBody());

        try {
            db.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {

        }
        db.close();
    }

    public void addComment(CommentDto commentDto) {

        Log.d("Values-Got-db-COMMENT ", commentDto.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_COMMENT, commentDto.getId());
        contentValues.put(PostID_COMMENT, commentDto.getPostId());
        contentValues.put(NAME_COMMENT, commentDto.getName());
        contentValues.put(EMAIL_COMMENT, commentDto.getEmail());
        contentValues.put(BODY_COMMENT, commentDto.getBody());
        try {
            db.insert(TABLE_NAME_COMMENT, null, contentValues);
        } catch (Exception e) {

        }
        db.close();
    }


    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor getNamesComment() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_COMMENT + " ORDER BY " + ID_COMMENT + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}
