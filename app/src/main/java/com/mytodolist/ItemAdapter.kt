package com.mytodolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_row.*
import kotlinx.android.synthetic.main.items_row.view.*

class ItemAdapter(val context: Context, val items: ArrayList<TodoModelClass>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llMain = view.llMain
        val tvTodo = view.tvTodo
        val ivDelete = view.ivDelete
        val ivFinish = view.ivFinish
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {

        val item = items.get(position)

        holder.tvTodo.text = item.todo

        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightGray
                )
            )
        } else {
            holder.llMain.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }

        holder.ivDelete.setOnClickListener {
            if (context is MainActivity) {
                context.deleteTodo(item)
            }
        }

        holder.ivFinish.setOnClickListener {
            if (context is MainActivity) {
                context.finishTodo(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}