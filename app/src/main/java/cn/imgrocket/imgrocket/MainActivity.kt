package cn.imgrocket.imgrocket

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.databinding.ActivityMainBinding
import cn.imgrocket.imgrocket.tool.APP
import cn.imgrocket.imgrocket.tool.Function.black
import cn.imgrocket.imgrocket.tool.URL
import com.bumptech.glide.Glide
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SimplePageFragmentAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var global: APP

    override fun onStart() {
        super.onStart()
        if (global.login) {
            Glide.with(this).load(URL.avatarURL + global.uid).into(binding.mainImgAvatar)
            binding.mainImgAvatar.setColorFilter(Color.TRANSPARENT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        global = application as APP
        when (intent.extras?.get("Function")) {
            1 -> {
                val intent = Intent()
                intent.setClass(this, ChooseMultiPhotoActivity::class.java)
                intent.putExtra("Function", 1)
                startActivity(intent)
            }
            2 -> {
                val intent = Intent()
                intent.setClass(this, ChooseSinglePhotoActivity::class.java)
                intent.putExtra("Function", 2)
                startActivity(intent)
            }
            3 -> {
                val intent = Intent()
                intent.setClass(this, ChooseMultiPhotoActivity::class.java)
                intent.putExtra("Function", 3)
                startActivity(intent)
            }
        }
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
            if (global.login) {
                intent.setClass(this, UploadAvatarActivity::class.java)
            } else {
                intent.setClass(this, LoginActivity::class.java)
            }
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


