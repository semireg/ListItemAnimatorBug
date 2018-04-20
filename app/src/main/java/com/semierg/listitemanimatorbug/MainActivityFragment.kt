package com.semierg.listitemanimatorbug

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var messages: MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messages = mutableListOf("Test 1", "Test 2", "Test 3")

        linearLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(messages = messages)
        recycler_view.adapter = adapter

        setRecyclerViewItemTouchListener()
    }

    fun insertMessages(newMessages: List<String>) {
        messages.addAll(0, newMessages)
        adapter.notifyItemRangeInserted(0, newMessages.count())
    }

    fun insertMessage(message: String, position: Int) {
        messages.add(position, message)
        adapter.notifyItemInserted(position)
    }

    fun popMessage() {
        val index = messages.count() - 1
        messages.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    fun popMessages(removeCount: Int) {
        if (removeCount > messages.count()) return

        (0 until removeCount).forEach { messages.removeAt(messages.count() - 1) }
        adapter.notifyItemRangeRemoved(messages.count(), removeCount)
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                messages.removeAt(position)
                recycler_view.adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
}
