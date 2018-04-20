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

    private val groupCount = 3
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var messages: MutableList<String>
    private var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messages = mutableListOf()

        linearLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(messages = messages)
        recycler_view.adapter = adapter



        setRecyclerViewItemTouchListener()
    }

    fun updateList() {

    }

    fun insertNewMessages() {
        insertNewMessages(groupCount)
    }

    private fun insertNewMessages(count: Int) {
        val newMessages = (counter until (counter + count)).map { "Test $it" }
        counter += count

        messages.addAll(0, newMessages)
        adapter.notifyItemRangeInserted(0, count)
    }

    fun popOldMessages() {
        popOldMessages(groupCount)
    }

    private fun popOldMessages(removeCount: Int) {
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
