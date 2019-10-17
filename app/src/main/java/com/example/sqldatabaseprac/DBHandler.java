package com.example.sqldatabaseprac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "friendsDb";
    public static final String TABLE_FRIENDS = "friends";
    public static final String KEY_ID = "id";
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_EMAIL = "email";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableString ="CREATE TABLE "
                + TABLE_FRIENDS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY, "
                + KEY_FNAME + " TEXT, " + KEY_LNAME
                + " TEXT,"+ KEY_EMAIL + " TEXT" + ")";
        db.execSQL(createTableString);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_FRIENDS);
        onCreate(db);
    }
    void clearDatabase() {
        onUpgrade(this.getWritableDatabase(),0,0);
    }
    void addFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, friend.getFirstName());
        values.put(KEY_LNAME, friend.getLastName());
        values.put(KEY_EMAIL, friend.getEmail());
        db.insert(TABLE_FRIENDS, null, values);
        db.close();
    }
    Friend getFriend(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FRIENDS, new String[] { KEY_ID,
                        KEY_FNAME, KEY_LNAME, KEY_EMAIL },
                KEY_ID + "=?", new String[] {
                        String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Friend friend = new Friend(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3));
        return friend;
    }
    public List<Friend> getAllFriends() {
        List<Friend> friendList = new ArrayList<Friend>();
        String selectQuery = "SELECT  * FROM "+TABLE_FRIENDS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Friend friend = new Friend();
                friend.setId(Integer.parseInt(cursor.getString(0)));
                friend.setFirstName(cursor.getString(1));
                friend.setLastName(cursor.getString(2));
                friend.setEmail(cursor.getString(3));
                friendList.add(friend);
            } while (cursor.moveToNext());
        }
        return friendList;
    }
    public int updateContact(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, friend.getFirstName());
        values.put(KEY_LNAME, friend.getLastName());
        values.put(KEY_EMAIL, friend.getEmail());
        return db.update(TABLE_FRIENDS, values,
                KEY_ID + " = ?", new String[] {
                        String.valueOf(friend.getId()) });
    }
    public void deleteContact(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, KEY_ID + " = ?",
                new String[] { String.valueOf(friend.getId()) });
        db.close();
    }

    public int getFriendsCount() {
        String countQuery="SELECT  * FROM "+TABLE_FRIENDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
