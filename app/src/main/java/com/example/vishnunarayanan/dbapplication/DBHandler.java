package com.example.vishnunarayanan.dbapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Group16.db";
    private static String TABLE_NAME="";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TIMESTAMP="timestamp";
    private static final String COLUMN_VALUEX="value_x";
    private static final String COLUMN_VALUEY="value_y";
    private static final String COLUMN_VALUEZ="value_z";
    private static DBHandler currInstance;

    public DBHandler(Context ctx, String tablename){
        super(ctx, tablename, null, 1);
        TABLE_NAME=tablename;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ( "
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_TIMESTAMP+" REAL, "
                +COLUMN_VALUEX+" REAL, "
                +COLUMN_VALUEY+" REAL, "
                +COLUMN_VALUEZ+" REAL "
                +" ); ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void batchInsert(List<AccelerometerTuple> records){
        for(int i=0; i<records.size(); ++i){
            addTuple(records.get(i));
        }
    }

    public void addTuple(long timestamp, float x, float y, float z){
        AccelerometerTuple acT= new AccelerometerTuple(timestamp, x, y, z);
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_TIMESTAMP, acT.getTimestamp());
        cv.put(COLUMN_VALUEX, acT.getValue_x());
        cv.put(COLUMN_VALUEY, acT.getValue_y());
        cv.put(COLUMN_VALUEZ, acT.getValue_z());
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void addTuple(AccelerometerTuple acT){
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_TIMESTAMP, acT.getTimestamp());
        cv.put(COLUMN_VALUEX, acT.getValue_x());
        cv.put(COLUMN_VALUEY, acT.getValue_y());
        cv.put(COLUMN_VALUEZ, acT.getValue_z());
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public String dbToString(){
        String result="";
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE 1;";
        Cursor records=db.rawQuery(query, null);
        records.moveToFirst();
        while(!records.isAfterLast()){
            if(records.getString(records.getColumnIndex("timestamp"))!=null) {
                long timestamp = records.getLong(records.getColumnIndex("timestamp"));
                float value_x = records.getFloat(records.getColumnIndex("value_x"));
                float value_y = records.getFloat(records.getColumnIndex("value_y"));
                float value_z = records.getFloat(records.getColumnIndex("value_z"));
                result += timestamp + "\t" + value_x + "\t" + value_y + "\t" + value_z + "\n";
                records.moveToNext();
            }
        }
        db.close();
        return result;
    }

    public List<AccelerometerTuple> dbTopTen(){
        List<AccelerometerTuple> result = new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" ORDER BY "+COLUMN_TIMESTAMP+" DESC;";
        Cursor records = db.rawQuery(query, null);
        records.moveToFirst();
        int count =0;
        while(count<10 && !records.isAfterLast()){
            if(records.getString(records.getColumnIndex("timestamp"))!=null) {
                long timestamp = records.getLong(records.getColumnIndex("timestamp"));
                float value_x = records.getFloat(records.getColumnIndex("value_x"));
                float value_y = records.getFloat(records.getColumnIndex("value_y"));
                float value_z = records.getFloat(records.getColumnIndex("value_z"));
                AccelerometerTuple currAct=new AccelerometerTuple(timestamp, value_x, value_y, value_z);
                count++;
                records.moveToNext();
                result.add(currAct);
            }
        }
        db.close();
        return result;
    }

    public void clearTable(){
        String query="DELETE FROM "+TABLE_NAME+"; ";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

}