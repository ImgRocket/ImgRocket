package cn.imgrocket.imgrocket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        black(this)
        init()
        if (needHelp()) {
            val intent = Intent()
            intent.setClass(this, HelpActivity::class.java)
            startActivity(intent)
        }
        binding.mainImgHelp.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, HelpActivity::class.java)
            startActivity(intent)
        }
        binding.mainImgAvatar.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.mainNavNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_processing -> {
                    binding.mainImgAvatar.visibility = View.VISIBLE
                    binding.mainPageView.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_done -> {
                    binding.mainImgAvatar.visibility = View.VISIBLE
                    binding.mainPageView.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
                    binding.mainImgAvatar.visibility = View.INVISIBLE
                    binding.mainPageView.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }

        binding.mainPageView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                binding.mainNavNav.selectedItemId = binding.mainNavNav.menu.getItem(position).itemId
            }
        })

    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(ProcessingFragment(), DoneFragment(), UserFragment()))
        binding.mainPageView.adapter = adapter
        binding.mainPageView.currentItem = 0
    }

    private fun needHelp(): Boolean {
        val f = File(applicationContext.filesDir.path + "/notNew")
        return !f.exists()
    }
}


