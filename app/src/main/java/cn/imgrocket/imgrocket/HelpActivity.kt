package cn.imgrocket.imgrocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.helpfragment.*
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.main_page_view

class HelpActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    lateinit var helpImgCircles: ArrayList<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        Function.black(this)
        init()
        helpImgCircles = arrayListOf(help_img_circle1, help_img_circle2, help_img_circle3, help_img_circle4, help_img_circle5)
        currentPage(helpImgCircles, 1)
        main_page_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage(helpImgCircles, position + 1)
            }
        })
    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(HelpFragment1(), HelpFragment2(), HelpFragment3(), HelpFragment4(), HelpFragment5()))
        main_page_view.adapter = adapter
        main_page_view.currentItem = 0
    }

    private fun currentPage(imgs: ArrayList<ImageView>, pos: Int) {
        for (i in 0 until pos) imgs[i].setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
        for(i in pos until imgs.size) imgs[i].setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
    }
}
