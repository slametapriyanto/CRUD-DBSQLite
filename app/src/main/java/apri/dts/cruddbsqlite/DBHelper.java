package apri.dts.cruddbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    static String DBNAME = "studentdb";
    static final int DBVER = 1;
    static String TB_STUDENT = "students";
    static String KEY_ID = "id";
    static String KEY_NAME = "name";
    static String KEY_MAJORS = "majors";

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TB_STUDENT +" ("+
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME+" TEXT," + KEY_MAJORS+ " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS '"+ TB_STUDENT +"'";
        db.execSQL(dropTable);
        onCreate(db);
    }

    public long addStudent(String name, String major){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_MAJORS, major);

        long insert = db.insert(TB_STUDENT, null, cv);
        return insert;
    }

    public ArrayList<HashMap<String, String>> getStudentList(){
        ArrayList<HashMap<String, String>> listStudent;
        listStudent = new ArrayList<HashMap<String, String>>();
        String querySelect = "SELECT id, name, majors FROM "+TB_STUDENT;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(querySelect, null);
        if(c.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID, c.getString(0));
                map.put(KEY_NAME, c.getString(1));
                map.put(KEY_MAJORS, c.getString(2));
                listStudent.add(map);
            }while (c.moveToNext());
        }

        return listStudent;
    }
    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "DELETE FROM " + TB_STUDENT + " WHERE " + KEY_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
    public void update(int id, String name, String majors) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TB_STUDENT + " SET "
                + KEY_NAME + "='" + name + "', "
                + KEY_MAJORS + "='" + majors + "'"
                + " WHERE " + KEY_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }
}
