package test.freelancer.com.fltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android 18 on 7/1/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TVPrograms";

    // Table name
    private static final String TABLE_PROGRAMS = "tb_programs";
    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STARTTIME = "start_time";
    private static final String KEY_ENDTIME = "end_time";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_RATING = "rating";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_HOST_TABLE = "CREATE TABLE " + TABLE_PROGRAMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_STARTTIME + " TEXT," + KEY_ENDTIME + " TEXT,"+ KEY_CHANNEL + " TEXT," + KEY_RATING + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_HOST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    void addprogram(ProgramsDTO prog) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, prog.getName());
        values.put(KEY_STARTTIME, prog.getStart_time());
        values.put(KEY_ENDTIME, prog.getEnd_time());
        values.put(KEY_RATING, prog.getRating());
        values.put(KEY_CHANNEL, prog.getChannel());

        db.insert(TABLE_PROGRAMS, null, values);
        db.close(); // Closing database connection
    }

    public List<ProgramsDTO> getAllUrl() {
        List<ProgramsDTO> programList = new ArrayList<ProgramsDTO>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProgramsDTO prog = new ProgramsDTO();
                prog.setID(Integer.parseInt(cursor.getString(0)));
                prog.setName(cursor.getString(1));
                prog.setStart_time(cursor.getString(2));
                prog.setEnd_time(cursor.getString(3));
                prog.setRating(cursor.getString(4));
                prog.setChannel(cursor.getString(5));

                programList.add(prog);
            } while (cursor.moveToNext());
        }

        return programList;
    }

    public boolean hasData() {

        boolean sdata = false;
        List<ProgramsDTO> programList = new ArrayList<ProgramsDTO>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                sdata = true;
                break;
            } while (cursor.moveToNext());
        }

        return sdata;
    }
    public int getCount() {

        List<ProgramsDTO> programList = new ArrayList<ProgramsDTO>();

        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

int cnt = cursor.getCount();
        return cnt;
    }

}
