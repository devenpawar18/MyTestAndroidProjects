/**
 * 
 */
package com.arbetsformedlingen.controller;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;

/**
 * @author aramesan
 * 
 */
public class AppContentProvider extends ContentProvider {

	private AppSQLLiteHelper dbHelper;

	private static final String PROVIDER_NAME = "com.arbetsformedlingen.controller.AppContentProvider";

	private static final String DATABASE_NAME = "platsbankendb";
	public static int DATABASE_VERSION = 2;

	private static final int JOBDETAIL = 1;
	private static final int FILTER = 2;


	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, JobDetailEntity.JobDetailEntitySingle, JOBDETAIL);
		uriMatcher.addURI(PROVIDER_NAME, FilterEntity.FilterEntity, FILTER);
		
	}
	
	
	public class AppSQLLiteHelper extends SQLiteOpenHelper {

		/**
		 * @param context
		 * @param name
		 * @param factory
		 * @param version
		 */
		public AppSQLLiteHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("SQLiteDatabase:: OnCreate", "Creating database ");
			db.execSQL(JobDetailEntity.CREATE_TABLE_JOBDETAIL);
			db.execSQL(FilterEntity.CREATE_TABLE_FILTER);
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("SQLiteDatabase:: OnUpgrade", "Upgrading database from version" + oldVersion + " to " + newVersion + ", which will destroy all old data");
			if(newVersion>oldVersion){
			db.execSQL("DROP TABLE IF EXISTS " + JobDetailEntity.JOBDETAIL_TABLENAME);
			db.execSQL("DROP TABLE IF EXISTS " + FilterEntity.CREATE_TABLE_FILTER);
			onCreate(db);
			db.setVersion(newVersion);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#delete(android.net.Uri,
	 * java.lang.String, java.lang.String[])
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int recordcount = 0;
		SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();
		sqliteDB.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
			case JOBDETAIL:
				recordcount = sqliteDB.delete(JobDetailEntity.JOBDETAIL_TABLENAME, selection, selectionArgs);
				break;
			case FILTER:
				recordcount = sqliteDB.delete(FilterEntity.FILTER_TABLENAME, selection, selectionArgs);
				break;

			}
			sqliteDB.setTransactionSuccessful();
		} catch (Exception e) {
			Log.d("DBInsertException", e.getMessage());
		} finally {
			sqliteDB.endTransaction();
		}
		return recordcount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#getType(android.net.Uri)
	 */
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case JOBDETAIL:
			return JobDetailEntity.JobDetailTypeName;
		case FILTER:
			return FilterEntity.FilterTypeName;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#insert(android.net.Uri,
	 * android.content.ContentValues)
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long recordcount = 0;
		Uri newuri = null;
		SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();
		sqliteDB.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
			case JOBDETAIL:
				recordcount = sqliteDB.insert(JobDetailEntity.JOBDETAIL_TABLENAME, null, values);
				break;
			case FILTER:
				Cursor c = sqliteDB.query(FilterEntity.FILTER_TABLENAME, null,null, null, null, null, null);
				if(c.getCount()>19)
				{
					c.moveToFirst();
					sqliteDB.delete(FilterEntity.FILTER_TABLENAME, FilterEntity.ID_Column + "="+c.getString(c.getColumnIndex(FilterEntity.ID_Column)), null);
				}
				c.close();
				c = null;
				recordcount = sqliteDB.insert(FilterEntity.FILTER_TABLENAME, null, values);
				break;
			}
			sqliteDB.setTransactionSuccessful();
		} catch (Exception e) {
			Log.d("DBInsertException", e.getMessage());
		} finally {
			sqliteDB.endTransaction();
		}
		return newuri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		try {
			Context context = getContext();
			dbHelper = new AppSQLLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
			dbHelper.getReadableDatabase();	
			dbHelper.close();
			SQLiteDatabase sqliteDB = dbHelper.getReadableDatabase();
			return sqliteDB != null;
		} catch (Exception ex) {
			Log.d("DBException", ex.getMessage());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#query(android.net.Uri,
	 * java.lang.String[], java.lang.String, java.lang.String[],
	 * java.lang.String)
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy = sortOrder;
		String top = null;
		switch (uriMatcher.match(uri)) {
		case JOBDETAIL:
			qb.setTables(JobDetailEntity.JOBDETAIL_TABLENAME);
			break;
		case FILTER:
			qb.setTables(FilterEntity.FILTER_TABLENAME);
			orderBy = FilterEntity.FILTER_DATE + " desc";
			top = "20";
			break;
		}

		Cursor c = qb.query(sqliteDB, projection, selection, selectionArgs, null, null, orderBy, top);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#update(android.net.Uri,
	 * android.content.ContentValues, java.lang.String, java.lang.String[])
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int recordcount = 0;
		SQLiteDatabase sqliteDB = dbHelper.getWritableDatabase();
		sqliteDB.beginTransaction();
		try {
			switch (uriMatcher.match(uri)) {
			case JOBDETAIL:
				recordcount = sqliteDB.update(JobDetailEntity.JOBDETAIL_TABLENAME, values, selection, selectionArgs);
				break;
			case FILTER:
				recordcount = sqliteDB.update(FilterEntity.FILTER_TABLENAME, values, selection, selectionArgs);
				break;
			}
			sqliteDB.setTransactionSuccessful();
		} catch (Exception e) {
			Log.d("DBInsertException", e.getMessage());
		} finally {
			sqliteDB.endTransaction();
		}

		return recordcount;
	}

}
