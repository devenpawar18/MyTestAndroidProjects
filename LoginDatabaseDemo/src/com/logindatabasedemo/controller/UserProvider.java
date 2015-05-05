package com.logindatabasedemo.controller;

import java.io.IOException;
import java.util.HashMap;

import com.logindatabasedemo.entity.Person;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class UserProvider extends ContentProvider {
	private static final int DATABASE_VERSION = 1;
	public static final String AUTHORITY = "com.logindatabasedemo.controller.UserProvider";
	private static final String DATABASE_NAME = "login.db";
	private static final String USERS_TABLE_NAME = "person";

	private static final int USERS = 1;
	private static final int CUSTOM_QUERY = 2;

	private static String DB_PATH = "/data/data/com.logindatabasedemo.activities/databases/";
	private static final UriMatcher sUriMatcher;
	private DBHelper mOpenHelper;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, USERS_TABLE_NAME, USERS);

	}

	private static HashMap<String, String> usersProjectionMap;
	static {
		usersProjectionMap = new HashMap<String, String>();
		usersProjectionMap.put(Person.PERSON_ID, Person.PERSON_ID);
		usersProjectionMap.put(Person.PERSON_NAME, Person.PERSON_NAME);
		usersProjectionMap.put(Person.PERSON_PHONE, Person.PERSON_PHONE);
		usersProjectionMap.put(Person.PERSON_PASSWORD, Person.PERSON_PASSWORD);
		usersProjectionMap.put(Person.PERSON_EMAIL, Person.PERSON_EMAIL);
	}

	/**
	 * This class helps open, create, and upgrade the database file.
	 */
	private static class DBHelper extends SQLiteOpenHelper {
		private Context context;
		private SQLiteDatabase dataBase;

		DBHelper(Context argContext) {
			super(argContext, DATABASE_NAME, null, DATABASE_VERSION);
			context = argContext;

		}

		@Override
		public void onCreate(SQLiteDatabase argDB) {

			String table = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME
					+ " (";
			table += Person.PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
			table += Person.PERSON_NAME + " text,";
			table += Person.PERSON_PHONE + " text,";
			table += Person.PERSON_PASSWORD + " text,";
			table += Person.PERSON_EMAIL + " text";
			table += " );";
			argDB.execSQL(table);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

		/**
		 * Creates a empty database on the system and rewrites it with your own
		 * database.
		 * */
		public void createDataBase() throws IOException {

			boolean dbExist = checkDataBase();
			SQLiteDatabase db_Read = null;

			if (dbExist) {
				// do nothing - database already exist
			} else {
				// By calling this method and empty database will be created
				// into the default system path
				// of your application so we are gonna be able to overwrite that
				// database with our database.
				db_Read = this.getReadableDatabase();
				db_Read.close();
				try {
					copyDataBase();
				} catch (IOException e) {
					Log.d("Ex in copying database:", e.toString());
				}
			}
		}

		/**
		 * Check if the database already exist to avoid re-copying the file each
		 * time you open the application.
		 * 
		 * @return true if it exists, false if it doesn't
		 */
		private boolean checkDataBase() {

			SQLiteDatabase checkDB = null;

			try {
				// SQLiteDatabase.
				String myPath = DB_PATH + DATABASE_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READONLY);
			} catch (Exception e) {
				// database does't exist yet.
				Log.d("Ex in opening database:", e.toString());
			}

			try {
				if (checkDB != null) {
					checkDB.close();
				}
			} catch (Exception e) {
				// Exception while closing DB.
			}

			return checkDB != null ? true : false;
		}

		/**
		 * Copies your database from your local assets-folder to the just
		 * created empty database in the system folder, from where it can be
		 * accessed and handled. This is done by transfering bytestream.
		 * */
		private void copyDataBase() throws IOException {

		}

		public void openDataBase() throws SQLException {
			// Open the database
			String myPath = DB_PATH + DATABASE_NAME;
			dataBase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		}
	}

	@Override
	public int delete(Uri argUri, String argWhere, String[] argWhereArgs) {

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(argUri)) {
		case USERS:
			count = db.delete(USERS_TABLE_NAME, argWhere, argWhereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + argUri);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {

		switch (sUriMatcher.match(uri)) {
		case USERS:
			return Person.CONTENT_TYPE;
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		Uri rowUri = null;
		long rowId;
		switch (sUriMatcher.match(uri)) {
		case USERS:
			rowId = db.insert(USERS_TABLE_NAME, null, values);
			if (rowId > 0) {
				rowUri = ContentUris.withAppendedId(Person.CONTENT_URI, rowId);
			}
			break;
		default:
			throw new SQLException("Failed to insert row into " + uri);
		}
		return rowUri;
	}

	@Override
	public boolean onCreate() {

		mOpenHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String groupBy = null;
		String having = null;
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		switch (sUriMatcher.match(uri)) {
		case CUSTOM_QUERY:
			return db.rawQuery(selection, selectionArgs);
		case USERS:
			qb.setTables(USERS_TABLE_NAME);
			if (projection == null) {
				qb.setProjectionMap(usersProjectionMap);
			}
			break;
		}
		Cursor curssor = qb.query(db, projection, selection, selectionArgs,
				groupBy, having, sortOrder);
		return curssor;
	}

	@Override
	public int update(Uri argUri, ContentValues argValues, String argWhere,
			String[] argWhereArgs) {

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		int match = sUriMatcher.match(argUri);
		switch (match) {
		case USERS:
			count = db.update(USERS_TABLE_NAME, argValues, argWhere,
					argWhereArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + argUri);
		}
		return count;
	}

}
