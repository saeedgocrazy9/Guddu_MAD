package com.example.guddu_project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotel.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_ROOMS = "rooms";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_BOOKINGS = "bookings";

    // Columns for rooms table
    public static final String COLUMN_ROOM_ID = "id";
    public static final String COLUMN_ROOM_NUMBER = "room_number";
    public static final String COLUMN_ROOM_TYPE = "room_type";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STATUS = "status";

    // Columns for users table
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_PASSWORD = "password";

    // Columns for bookings table
    public static final String COLUMN_BOOKING_ID = "id";
    public static final String COLUMN_BOOKING_USER_ID = "user_id";
    public static final String COLUMN_BOOKING_ROOM_ID = "room_id";
    public static final String COLUMN_CHECK_IN_DATE = "check_in_date";
    public static final String COLUMN_CHECK_OUT_DATE = "check_out_date";
    public static final String COLUMN_BOOKING_STATUS = "status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create all tables when the database is first created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Rooms Table
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROOM_NUMBER + " TEXT NOT NULL, "
                + COLUMN_ROOM_TYPE + " TEXT NOT NULL, "
                + COLUMN_PRICE + " REAL NOT NULL, "
                + COLUMN_STATUS + " TEXT DEFAULT 'available'"
                + ")";
        db.execSQL(CREATE_ROOMS_TABLE);

        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT NOT NULL, "
                + COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, "
                + COLUMN_ROLE + " TEXT DEFAULT 'guest', "
                + COLUMN_PASSWORD + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Bookings Table
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BOOKING_USER_ID + " INTEGER NOT NULL, "
                + COLUMN_BOOKING_ROOM_ID + " INTEGER NOT NULL, "
                + COLUMN_CHECK_IN_DATE + " TEXT NOT NULL, "
                + COLUMN_CHECK_OUT_DATE + " TEXT NOT NULL, "
                + COLUMN_BOOKING_STATUS + " TEXT DEFAULT 'confirmed', "
                + "FOREIGN KEY(" + COLUMN_BOOKING_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_BOOKING_ROOM_ID + ") REFERENCES " + TABLE_ROOMS + "(" + COLUMN_ROOM_ID + ")"
                + ")";
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    // Upgrade the database if version changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        onCreate(db);
    }

    // Method to add a new room.
    public long addRoom(String roomNumber, String roomType, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NUMBER, roomNumber);
        values.put(COLUMN_ROOM_TYPE, roomType);
        values.put(COLUMN_PRICE, price);
        long id = db.insert(TABLE_ROOMS, null, values);
        db.close();
        return id;
    }

    // Method to retrieve all rooms as a list of strings.
    public Cursor getAllRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ROOMS;
        return db.rawQuery(query, null);
    }

    // +3: Additional methods

    // 1. Update a room's details.
    public int updateRoom(int id, String roomNumber, String roomType, double price, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NUMBER, roomNumber);
        values.put(COLUMN_ROOM_TYPE, roomType);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STATUS, status);
        int rowsAffected = db.update(TABLE_ROOMS, values, COLUMN_ROOM_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }

    // 2. Delete a room by its ID.
    public int deleteRoom(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_ROOMS, COLUMN_ROOM_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    // 3. Get a room by its ID.
    public Cursor getRoomById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + COLUMN_ROOM_ID + "=?";
        return db.rawQuery(query, new String[]{String.valueOf(id)});
    }
    // For filtering by type
    public Cursor getRoomsByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ROOMS + " WHERE " + COLUMN_ROOM_TYPE + "=?";
        return db.rawQuery(query, new String[]{type});
    }
    public int getTotalRooms() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ROOMS, null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }


    // For retrieving a specific room


}
