package com.mytodolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.items_row.*
import kotlinx.android.synthetic.main.items_row.view.*
import kotlinx.android.synthetic.main.items_row_read_only.view.*

class ItemAdapterReadOnly(val context: Context, val items: ArrayList<TodoModelClass>) :
    RecyclerView.Adapter<ItemAdapterReadOnly.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llMain = view.llMainReadOnly
        val tvTodo = view.tvTodoReadOnly
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterReadOnly.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_row_read_only,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapterReadOnly.ViewHolder, position: Int) {

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
    }

    override fun getItemCount(): Int {
        return items.size
    }
}