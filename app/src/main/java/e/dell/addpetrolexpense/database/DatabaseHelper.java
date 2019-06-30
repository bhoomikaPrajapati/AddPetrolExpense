package e.dell.addpetrolexpense.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import e.dell.addpetrolexpense.model.Model;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "userData.db";
    public static String TABLE_NAME = "User";
    public static int DATABASE_VERSION = 1;
    public static String COLUMN_ID = "id";
    public static String COLUMN_DATE_PIK = "date";
    public static String COLUMN_TIME_PIK = "time";
    public static String COLUMN_AMOUNT = "amount";
    public static String COLUMN_KM = "km";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_DATE_PIK + " TEXT,"
                        + COLUMN_TIME_PIK + " TEXT,"
                        + COLUMN_AMOUNT + " TEXT,"
                        + COLUMN_KM + " TEXT "
                        + ")";


        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);

    }

    public long insertUserData(Model model) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_DATE_PIK, model.getDatepik());
        values.put(COLUMN_TIME_PIK, model.getTimepik());
        values.put(COLUMN_AMOUNT, model.getAmount());
        values.put(COLUMN_KM, model.getKm());


        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;

    }
    public List<Model> getUserData() {
        List<Model> userDataList=new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model data = new Model();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setDatepik(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_PIK)));
                data.setTimepik(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_PIK)));
                data.setAmount(cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT)));
                data.setKm(cursor.getString(cursor.getColumnIndex(COLUMN_KM)));

                userDataList.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();


        return userDataList;
    }

}
