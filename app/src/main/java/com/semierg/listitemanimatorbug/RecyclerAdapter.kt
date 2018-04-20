package com.semierg.listitemanimatorbug

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

class RecyclerAdapter(private val messages: MutableList<String>):
        RecyclerView.Adapter<RecyclerAdapter.TextHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return TextHolder(inflatedView)
    }

    override fun getItemCount() = messages.count()

    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        val message = messages[position]
        holder.bindMessage(message)
    }

    fun foo() {
        Log.d("messages: ", messages.joinToString { ", " })
    }

    class TextHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        companion object {
            private val TEXT_KEY = "TEXT"
        }

        private var view: View = v
        private var message: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindMessage(text: String) {
            this.message = text
            view.item_text.text = "Message: " + this.message
        }
    }
}