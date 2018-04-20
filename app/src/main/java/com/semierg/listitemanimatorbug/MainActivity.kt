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
            main_fragment().insertNewMessages()
        }

        fab_remove.setOnClickListener { _ ->
            main_fragment().popOldMessages()
        }

        fab_animate.setOnClickListener { _ ->
            main_fragment().forceChangeItems()
        }
    }

    private fun main_fragment(): MainActivityFragment {
        return (fragment as MainActivityFragment)
    }
}
