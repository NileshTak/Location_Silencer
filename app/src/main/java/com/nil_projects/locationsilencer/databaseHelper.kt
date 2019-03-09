package com.nil_projects.locationsilencer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import android.widget.Toast

val DATABASE_NAME ="MyDB"
val TABLE_NAME="UserData"
val COL_NAME = "name"
val COL_ID = "id"
val COL_Longi = "long"
val COL_Lati = "lati"

class databaseHandler(var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " VARCHAR(256)," +
                COL_Longi + " VARCHAR(256)," +
                COL_Lati + " VARCHAR(256))"

        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(user: DataModel) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, user.Name)
        cv.put(COL_Longi, user.Longitude)
        cv.put(COL_Lati, user.Latitude)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    fun readData(): MutableList<DataModel> {
        var list: MutableList<DataModel> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var user = DataModel()
                user.ID = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.Name = result.getString(result.getColumnIndex(COL_NAME))
                user.Latitude = result.getString(result.getColumnIndex(COL_Lati))
                user.Longitude = result.getString(result.getColumnIndex(COL_Longi))
                list.add(user)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun deleteData(posi : Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COL_ID+"=?", arrayOf(posi.toString()))
        db.close()
    }
}