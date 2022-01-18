package com.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnAddTodo
import kotlinx.android.synthetic.main.activity_main.etTodo
import kotlinx.android.synthetic.main.activity_main.rvTodos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAddTodo.setOnClickListener { view ->
            addRecord(view)
        }

        setupListofDataIntoRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Intent(this, AnalyseActivity::class.java).also {
            startActivity(it)
        }
        return true
    }

    private fun addRecord(view: View?) {
        val todo = etTodo.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        if (todo.isNotEmpty()) {
            val status = databaseHandler.addTodo(TodoModelClass(0, todo, 0, 0))
            if (status > -1) {
                Toast.makeText(applicationContext, "Added to Todo List", Toast.LENGTH_SHORT).show()
                etTodo.text.clear()

                setupListofDataIntoRecyclerView()
            }
        } else {
            Toast.makeText(applicationContext, "Todo cannot be blank", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListofDataIntoRecyclerView() {

        if (getItemsList().size > 0) {
            rvTodos.visibility = View.VISIBLE
            rvTodos.layoutManager = LinearLayoutManager(this)

            rvTodos.adapter = ItemAdapter(this, getItemsList())

        } else {
            rvTodos.visibility = View.GONE
        }
    }

    private fun getItemsList(): ArrayList<TodoModelClass> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        var todoList: ArrayList<TodoModelClass> = ArrayList<TodoModelClass>()

        for (item in databaseHandler.viewTodo()){
            if (item.isDeleted != 1 && item.isFinished != 1) {
                todoList.add(item)
            }
        }

        return todoList
    }

    fun deleteTodo(todo: TodoModelClass) {

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val status = databaseHandler.updateTodo(TodoModelClass(todo.id, todo.todo, 1, 0))

        if (status > -1) {
            Toast.makeText(applicationContext, "Todo deleted.", Toast.LENGTH_SHORT).show()
            setupListofDataIntoRecyclerView()
        } else {
            Toast.makeText(applicationContext, "Error happened", Toast.LENGTH_LONG).show()
        }
        setupListofDataIntoRecyclerView()
    }

    fun finishTodo(todo: TodoModelClass) {

        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val status = databaseHandler.updateTodo(TodoModelClass(todo.id, todo.todo, 0, 1))

        if (status > -1) {
            Toast.makeText(applicationContext, "Todo finished.", Toast.LENGTH_SHORT).show()
            setupListofDataIntoRecyclerView()
        } else {
            Toast.makeText(applicationContext, "Error happened", Toast.LENGTH_LONG).show()
        }
        setupListofDataIntoRecyclerView()
    }
}
