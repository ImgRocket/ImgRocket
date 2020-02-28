package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tty.community.adapter.SimplePageFragmentAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        main_nav_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_file -> {
                    main_page_view.currentItem = 0
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
                    main_page_view.currentItem = 1
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }
        main_fab_add.setOnClickListener {

        }
    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(FileFragment(), UserFragment()))
        main_page_view.adapter = adapter
        main_page_view.currentItem = 0
    }
}