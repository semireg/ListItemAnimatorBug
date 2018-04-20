package com.semierg.listitemanimatorbug

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab_add.setOnClickListener { _ ->
//            Snackbar.make(view, "Adding", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

            main_fragment().insertMessages(listOf("Test 1", "Test 2", "Test 3"))
        }

        fab_remove.setOnClickListener { _ ->
//            Snackbar.make(view, "Removing", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()

            main_fragment().popMessages(3)
        }

        fab_animate.setOnClickListener { _ ->
//            Snackbar.make(view, "Animate", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }
    }

    private fun main_fragment(): MainActivityFragment {
        return (fragment as MainActivityFragment)
    }
}
