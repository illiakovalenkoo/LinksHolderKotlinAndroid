package com.example.linksholder

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var linksData = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: LinksAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        linksData = mutableListOf()

        val input_long = findViewById<EditText>(R.id.input_long_link)
        val input_short = findViewById<EditText>(R.id.input_short_link)
        val btn_add = findViewById<Button>(R.id.add_btn)
        val listView = findViewById<ListView>(R.id.listView)

        adapter = LinksAdapter(this, linksData)
        listView.adapter = adapter


        btn_add.setOnClickListener {
            val shortLink = input_short.text.toString().trim()
            val longLink = input_long.text.toString().trim()

            val nameExists = linksData.any { it.first == input_short.text.toString() }

            if(!(longLink.startsWith("http") || longLink.startsWith("www"))) {
                btn_add.text = "Enter a link"
            } else if(shortLink.length < 5) {
                btn_add.text = "Too short title"
            } else if(nameExists) {
                btn_add.text = "There is such title"
            } else {
                linksData.add(Pair(shortLink, longLink))
                adapter.notifyDataSetChanged()
                btn_add.text = "Done"
                input_long.text.clear()
                input_short.text.clear()
            }
        }
    }
}