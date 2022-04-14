package jp.ac.jec.cm0146.rirekisyo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class CompanySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "COMPANY_DAB";
    public static final int version = 1;
    public static final String TABLE_NAME = "COMPANY";



    public CompanySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " " +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " address TEXT," +
                " TEL TEXT," +
                " capital TEXT," +
                " profit TEXT," +
                " salary1st TEXT," +
                " memo TEXT," +
                " CONSTRAINT uq_id_name UNIQUE(name));");

        //デモ
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (1, '株式会社フェンリル', '大阪府大阪市北区大深町3-1 グランフロント大阪 タワーB 14F', '0663777606', '１億円'," +
                " NULL, NULL, NULL);");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (2, 'Apple Japan', '東京都港区六本木6丁目10番1号六本木ヒルズ', NULL, '14兆円', NULL, NULL, NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Company> getAllList() {
        ArrayList<Company> ary = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        if ( db == null ){
            return null;
        }

        try {
            String[] column = new String[]{"name"};
            Cursor cur = db.query(TABLE_NAME, column, null, null, null, null, null);

            while(cur.moveToNext()){
                Company tmp = new Company(cur.getString(0));
                ary.add(tmp);
            }
            cur.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        } finally {
            db.close();
        }
        return ary;
    }

    public ArrayList<Company> getCompanyData() {
        ArrayList<Company> ary = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        if( db == null ){
            return null;
        }

        try{
            String[] column = new String[]{"_id", "name", "address", "TEL", "capital", "profit", "salary1st", "memo"};
            Cursor cur = db.query(TABLE_NAME, column, null, null, null, null, null);

            while(cur.moveToNext()){
                Company tmp = new Company(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5), cur.getString(6), cur.getString(7));
                ary.add(tmp);
            }
            cur.close();
        } catch (SQLiteException e){
            e.printStackTrace();
        } finally {
            db.close();
        }
        return ary;

    }
}
