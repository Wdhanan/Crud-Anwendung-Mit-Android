package com.example.meinebibliothek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MeinDatenBankManager extends SQLiteOpenHelper {
    private Context context;
    private static final String DATENBANK_NAME = "Bibi.db";
    private static final int DATENBANK_VERSION = 1;
    private  static final String TABELLE_NAME = "Bibi";
    private   static final String SPALTE_ID = "_id";
    private   static final String SPALTE_TITEL = "book_title";
    private   static final String SPALTE_AUTHOR = "book_author";
    private   static final String SPALTE_SEITE = "book_pages";


     MeinDatenBankManager(@Nullable Context context ) {
        super(context, DATENBANK_NAME, null, DATENBANK_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         // create statement
        String query = "CREATE TABLE " + TABELLE_NAME +
                " (" + SPALTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SPALTE_TITEL + " TEXT, " +
                SPALTE_AUTHOR + " TEXT, " +
                SPALTE_SEITE + " INTEGER) ;";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         //Delete statement
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_NAME);
        onCreate(db);
    }
    void fügBuchEin(String title, String author, int pages){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SPALTE_TITEL,title );
        cv.put(SPALTE_AUTHOR,author );
        cv.put(SPALTE_SEITE,pages );
        long result= db.insert(TABELLE_NAME, null, cv);
        // Daten wurden nicht hinzugefugt

        if( result == -1){
            Toast.makeText(context," Das Buch Wurde nicht Hinzugefuegt", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context," Das Buch Wurde  Hinzugefuegt", Toast.LENGTH_SHORT).show();

        }
    }

    Cursor ausgabeAlles(){
        String query = "SELECT * FROM " + TABELLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=null;
        //  wenn die Datenbank nicht leer ist
        if(db != null){
            // alle daten werden im Cursor gespeichert
            cursor= db.rawQuery(query, null);

        }
        return cursor;
    }
    void updateData(int row_id,String title, String author, String pages){
         SQLiteDatabase db= this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(SPALTE_TITEL, title);
        cv.put(SPALTE_AUTHOR, author);
        cv.put(SPALTE_SEITE, pages);
        String where = SPALTE_ID + "=?";
        String[] whereArg = new String[]{Integer.toString(row_id)};

        db.update(TABELLE_NAME,cv, where, whereArg);

    }
    void LöschEinElement(int row_id){
         SQLiteDatabase db= this.getWritableDatabase();
        String where = SPALTE_ID + "=?";
        String[] whereArg = new String[]{Integer.toString((row_id))};
          db.delete(TABELLE_NAME, where, whereArg);

    }


}
