package com.mytodolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoDatabase"
        private const val TABLE_CONTACTS = "TodoTable"

        private const val KEY_ID = "id"
        private const val KEY_TODO = "todo"
        private const val KEY_DELETE = "_delete"
        private const val KEY_FINISH = "finish"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO + " TEXT,"
                + KEY_DELETE + " INTEGER DEFAULT 0," + KEY_FINISH + " INTEGER DEFAULT 0" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addTodo(todo: TodoModelClass): Long {
        var db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TODO, todo.todo)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewTodo(): ArrayList<TodoModelClass> {
        val todoList: ArrayList<TodoModelClass> = ArrayList<TodoModelClass>()

        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var todo: String
        var isDeleted: Int
        var isFinished: Int

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                todo = cursor.getString(cursor.getColumnIndex(KEY_TODO))
                isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_DELETE))
                isFinished = cursor.getInt(cursor.getColumnIndex(KEY_FINISH))

                val todo = TodoModelClass(
                    id = id,
                    todo = todo,
                    isDeleted = isDeleted,
                    isFinished = isFinished
                )
                todoList.add(todo)


            } while (cursor.moveToNext())
        }
        return todoList
    }

    fun updateTodo(todo: TodoModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TODO, todo.todo)
        contentValues.put(KEY_DELETE, todo.isDeleted)
        contentValues.put(KEY_FINISH, todo.isFinished)

        val success = db.update(TABLE_CONTACTS, contentValues, KEY_ID + "=" + todo.id, null)

        db.close()
        return success
    }

    fun deleteTodo(todo: TodoModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, todo.id)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + todo.id, null)
        db.close()
        return success
    }
}
