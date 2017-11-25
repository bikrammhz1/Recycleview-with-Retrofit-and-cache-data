//package com.rbmhz.projectassignment.helper;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.rbmhz.projectassignment.DTO.CommentDto;
//import com.rbmhz.projectassignment.DTO.PostDto;
//
//public class DatabaseHelper2 extends SQLiteOpenHelper {
//
//    //Constants for Database name, table name, and column names
//    public static final String DB_NAME = "ProjectAssignment";
//    public static final String TABLE_NAME = "PostTable";
//
//    public static final String ID = "id";
//    public static final String USER_ID = "userId";
//    public static final String TITLE = "title";
//    public static final String BODY = "body";
//
//
//    public static final String ID_COMMENT = "idComment";
//    public static final String PostID_COMMENT = "postId";
//    public static final String NAME_COMMENT = "name";
//    public static final String EMAIL_COMMENT = "email";
//    public static final String BODY_COMMENT = "bodyComment";
//
//    //database version
//    private static final int DB_VERSION = 1;
//
//    //Constructor
//    public DatabaseHelper2(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }
//
//    //creating the database
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE " + TABLE_NAME
//                + "(" + ID +
//                " INT, " + USER_ID +
//                " VARCHAR, " + TITLE +
//                " VARCHAR," + BODY +
//                " VARCHAR );";
//        db.execSQL(sql);
//    }
//
//    //upgrading the database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "DROP TABLE IF EXISTS Persons";
//        db.execSQL(sql);
//        onCreate(db);
//    }
//
//    public void deleteAll() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, null, null);
//    }
//
//    public void addPost(PostDto postDto) {
//
//        Log.d("Values-Got-db ", postDto.getId());
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(ID, postDto.getId());
//        contentValues.put(USER_ID, postDto.getUserId());
//        contentValues.put(TITLE, postDto.getTitle());
//        contentValues.put(BODY, postDto.getBody());
//
//        try {
//            db.insert(TABLE_NAME, null, contentValues);
//        } catch (Exception e) {
//
//        }
//        db.close();
//    }
//
////    public void createUserTable(String TABLE_NAME_COMMENT) {
////        final SQLiteDatabase db = getWritableDatabase();
////        String sql = "CREATE TABLE " + TABLE_NAME_COMMENT
////                + "(" + ID +
////                " INT, " + "name" +
////                " VARCHAR, " + "email" +
////                " VARCHAR," +BODY +
////                " VARCHAR );";
////        db.execSQL(sql);
////        db.close();
////    }
//
//    public void deleteAllComment(String Table_Namea) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(Table_Namea, null, null);
//    }
//
//    public void deleteAllTable(String Table_Namea) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DROP TABLE IF EXISTS " + Table_Namea);
//    }
//
//    public void CreateDynamicTables(String Table_Namea, CommentDto commentDto) {
//        SQLiteDatabase dbs = this.getWritableDatabase();
//        dbs = this.getWritableDatabase();
//        dbs.execSQL("DROP TABLE IF EXISTS " + Table_Namea);
//
//        String sql = "CREATE TABLE " + Table_Namea +
//                "("
//                + ID_COMMENT + " INT, "
//                + PostID_COMMENT + " VARCHAR, "
//                + NAME_COMMENT + " LONG, "
//                + EMAIL_COMMENT + " LONG, "
//                + BODY_COMMENT + " LONG );";
//        dbs.execSQL(sql);
//
//        dbs = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//
//        contentValues.put(ID_COMMENT, commentDto.getId());
//        contentValues.put(PostID_COMMENT, commentDto.getPostId());
//        contentValues.put(NAME_COMMENT, commentDto.getName());
//        contentValues.put(EMAIL_COMMENT, commentDto.getEmail());
//        contentValues.put(BODY_COMMENT, commentDto.getBody());
//
//        try {
//            dbs.insert(Table_Namea, null, contentValues);
//        } catch (Exception e) {
//
//        }
//        dbs.close();
//    }
////
////    public void addComment(CommentDto commentDto) {
////
////        Log.d("Values-Got-db " ,commentDto.getId());
////        SQLiteDatabase db = this.getWritableDatabase();
////        ContentValues contentValues = new ContentValues();
////
////        contentValues.put(PostID_COMMENT, commentDto.getPostId());
////        contentValues.put(BODY_COMMENT, commentDto.getBody());
////        contentValues.put(NAME_COMMENT, commentDto.getName());
////        contentValues.put(EMAIL_COMMENT, commentDto.getEmail());
////        contentValues.put(ID_COMMENT, commentDto.getId());
////
////        try {
////            db.insert(TABLE_NAME, null, contentValues);
////        } catch (Exception e) {
////
////        }
////        db.close();
////    }
//
//    public Cursor getNamesDynamic(String TABLE_NAME) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID_COMMENT + " ASC;";
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//
//
//    public Cursor getNames() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID + " ASC;";
//        Cursor c = db.rawQuery(sql, null);
//        return c;
//    }
//
//}
