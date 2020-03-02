package cn.imgrocket.imgrocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        main_nav_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_processing -> {
                    main_page_view.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_done -> {
                    main_page_view.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
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
}