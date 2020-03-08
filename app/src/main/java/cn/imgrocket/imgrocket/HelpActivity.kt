package cn.imgrocket.imgrocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.helpfragment.*
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.main_page_view

class HelpActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        Function.black(this)
        init()
        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
        main_page_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position.toString()) {
                    "0" -> {
                        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                    }
                    "1" -> {
                        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                    }
                    "2" -> {
                        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                    }
                    "3" -> {
                        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
                    }
                    "4" -> {
                        help_img_circle1.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle2.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle3.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle4.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                        help_img_circle5.setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
                    }
                }
            }
        })
    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(HelpFragment1(), HelpFragment2(), HelpFragment3(), HelpFragment4(), HelpFragment5()))
        main_page_view.adapter = adapter
        main_page_view.currentItem = 0
    }
}
