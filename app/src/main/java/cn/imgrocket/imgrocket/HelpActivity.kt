package cn.imgrocket.imgrocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.databinding.ActivityHelpBinding
import cn.imgrocket.imgrocket.helpfragment.*
import cn.imgrocket.imgrocket.tool.Function

class HelpActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    lateinit var helpImgCircles: ArrayList<ImageView>
    private lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Function.black(this)
        init()
        helpImgCircles = arrayListOf(binding.helpImgCircle1, binding.helpImgCircle2, binding.helpImgCircle3, binding.helpImgCircle4, binding.helpImgCircle5)
        currentPage(helpImgCircles, 1)
        binding.helpPageView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        binding.helpPageView.adapter = adapter
        binding.helpPageView.currentItem = 0
    }

    private fun currentPage(img: ArrayList<ImageView>, pos: Int) {
        for (i in 0 until pos) img[i].setImageDrawable(resources.getDrawable(R.drawable.ic_lens_black_24dp, null))
        for (i in pos until img.size) img[i].setImageDrawable(resources.getDrawable(R.drawable.ic_panorama_fish_eye_black_24dp, null))
    }
}
