package com.mytodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_deleted_list.*

class DeletedListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deleted_list)

        setupListofDataIntoRecyclerView()

        btnDeleteBack.setOnClickListener {
            finish()
        }
    }

    private fun setupListofDataIntoRecyclerView() {

        if (getItemsList().size > 0) {
            rvDelete.visibility = View.VISIBLE
            rvDelete.layoutManager = LinearLayoutManager(this)

            rvDelete.adapter = ItemAdapterReadOnly(this, getItemsList())

        } else {
            rvDelete.visibility = View.GONE
        }
    }

    private fun getItemsList(): ArrayList<TodoModelClass> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        var todoList: ArrayList<TodoModelClass> = ArrayList<TodoModelClass>()

        for (item in databaseHandler.viewTodo()){
            if (item.isDeleted == 1) {
                todoList.add(item)
            }
        }

        return todoList
    }
}

