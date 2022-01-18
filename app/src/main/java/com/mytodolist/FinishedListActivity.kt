package com.mytodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_finished_list.*
import kotlinx.android.synthetic.main.activity_main.*

class FinishedListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_list)

        setupListofDataIntoRecyclerView()

        btnFinishBack.setOnClickListener {
            finish()
        }
    }

    private fun setupListofDataIntoRecyclerView() {

        if (getItemsList().size > 0) {
            rvFinish.visibility = View.VISIBLE
            rvFinish.layoutManager = LinearLayoutManager(this)

            rvFinish.adapter = ItemAdapterReadOnly(this, getItemsList())

        } else {
            rvFinish.visibility = View.GONE
        }
    }

    private fun getItemsList(): ArrayList<TodoModelClass> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)

        var todoList: ArrayList<TodoModelClass> = ArrayList<TodoModelClass>()

        for (item in databaseHandler.viewTodo()){
            if (item.isFinished == 1) {
                todoList.add(item)
            }
        }

        return todoList
    }
}























