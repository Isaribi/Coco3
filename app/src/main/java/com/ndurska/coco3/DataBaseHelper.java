package com.ndurska.coco3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CLIENT_TABLE = "CLIENT_TABLE";
    public static final String COLUMN_CLIENT_NAME = "CLIENT_NAME";
    public static final String COLUMN_CLIENT_ADJECTIVE = "CLIENT_ADJECTIVE";
    public static final String COLUMN_CLIENT_BREED = "CLIENT_BREED";
    public static final String COLUMN_CLIENT_ID = "ID";
    public static final String COLUMN_CLIENT_OWNER_ID = "CLIENT_OWNER_" + COLUMN_CLIENT_ID;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "coco.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + CLIENT_TABLE +
                " (" + COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENT_NAME + " TEXT, " +
                COLUMN_CLIENT_ADJECTIVE + " TEXT, " +
                COLUMN_CLIENT_BREED + " TEXT," +
                COLUMN_CLIENT_OWNER_ID + " INT)";
        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addClient(Client client){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLIENT_NAME,client.getName());
        cv.put(COLUMN_CLIENT_ADJECTIVE,client.getAdjective());
        cv.put(COLUMN_CLIENT_BREED,client.getBreed());
        long insert = db.insert(CLIENT_TABLE, null, cv);

        db.close();
        if (insert == -1)
            return false;
        else
            return true;
    }
    public boolean deleteClient(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString =  "DELETE FROM " + CLIENT_TABLE + " WHERE " + COLUMN_CLIENT_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        }  else{
            cursor.close();
            db.close();
            return false;
        }
    }
    public boolean editClient(Client client){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CLIENT_NAME,client.getName());
        cv.put(COLUMN_CLIENT_ADJECTIVE,client.getAdjective());
        cv.put(COLUMN_CLIENT_BREED,client.getBreed());
        cv.put(COLUMN_CLIENT_OWNER_ID,client.getOwnerId());

        int insert=db.update(CLIENT_TABLE,cv,COLUMN_CLIENT_ID+" = "+client.getClientId(),null);
        db.close();

        if (insert == -1)
            return false;
        else
            return true;

    }
    public List<Client> getClients(){
        List<Client> clientList = new ArrayList<>();
        String queryString = "SELECT * FROM "+CLIENT_TABLE;

        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int clientID=cursor.getInt(0);
                String clientName= cursor.getString(1);
                String clientAdjective= cursor.getString(2);
                String clientBreed= cursor.getString(3);
                int clientOwnerID= cursor.getInt(4);
                Client newClient =  new Client(clientID,clientName,clientAdjective,clientBreed,clientOwnerID);
                clientList.add(newClient);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return clientList;
    }
}
