package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.Function.black
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        black(this)
        init()
        if (needHelp()) {
            val intent = Intent()
            intent.setClass(this, HelpActivity::class.java)
            startActivity(intent)
        }
        main_img_help.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, HelpActivity::class.java)
            startActivity(intent)
        }
        main_nav_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_processing -> {
                    main_img_avatar.visibility = View.VISIBLE
                    main_page_view.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_done -> {
                    main_img_avatar.visibility = View.VISIBLE
                    main_page_view.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
                    main_img_avatar.visibility = View.INVISIBLE
                    main_page_view.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }

        main_page_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                main_nav_nav.selectedItemId = main_nav_nav.menu.getItem(position).itemId
            }
        })

    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(ProcessingFragment(), DoneFragment(), UserFragment()))
        main_page_view.adapter = adapter
        main_page_view.currentItem = 0
    }

    private fun needHelp(): Boolean {
        val f = File(applicationContext.filesDir.path + "/notNew")
        return !f.exists()
    }
}


