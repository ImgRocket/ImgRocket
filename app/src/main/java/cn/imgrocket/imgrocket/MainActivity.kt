package cn.imgrocket.imgrocket

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import cn.imgrocket.imgrocket.ExchangeActivity.Companion.post
import cn.imgrocket.imgrocket.adapter.SimplePageFragmentAdapter
import cn.imgrocket.imgrocket.databinding.ActivityMainBinding
import cn.imgrocket.imgrocket.room.AppDatabase
import cn.imgrocket.imgrocket.room.model.TaskItem
import cn.imgrocket.imgrocket.room.model.User
import cn.imgrocket.imgrocket.tool.*
import cn.imgrocket.imgrocket.tool.APP.Companion.context
import cn.imgrocket.imgrocket.tool.Function.black
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity(), AvatarListener, UserStateChangeListener, EasyPermissions.PermissionCallbacks {
    private lateinit var adapter: SimplePageFragmentAdapter
    private lateinit var binding: ActivityMainBinding
    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private val global: APP by lazy { application as APP }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermission()
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

        global.database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").allowMainThreadQueries().build()
        global.userDao = global.database.userDao()
        global.user = global.userDao.loadUser()
        global.userData = global.userDao.autoUpdateLoadUser()
        global.userData.observe(this) {
            global.user = it
        }

        global.addAvatarChangeListener(this)
        global.addOnUserStateChangeListener(this)

        onAvatarChange(0)
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
            if (global.user != null) {
                intent.setClass(this, UploadAvatarActivity::class.java)
            } else {
                intent.setClass(this, LoginActivity::class.java)
            }
            startActivity(intent)
        }

        binding.mainNavNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_processing -> {
//                    binding.mainImgAvatar.visibility = View.VISIBLE
                    binding.mainImgAvatar.fadeInAndShow()
                    changeSmallAvatar()
                    binding.mainPageView.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_done -> {
//                    binding.mainImgAvatar.visibility = View.VISIBLE
                    binding.mainImgAvatar.fadeInAndShow()
                    changeSmallAvatar()
                    binding.mainPageView.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
//                    binding.mainImgAvatar.visibility = View.INVISIBLE
                    binding.mainImgAvatar.fadeOutAndHide()
                    binding.mainPageView.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false

        }
        binding.mainPageView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                binding.mainNavNav.selectedItemId = binding.mainNavNav.menu.getItem(position).itemId
            }
        })
    }

    private fun View.fadeOutAndHide() {
        val before = if (this@fadeOutAndHide.visibility == View.GONE) 0f else 1f
        val fadeOut: Animation = AlphaAnimation(before, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = 200
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                this@fadeOutAndHide.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
        })
        this@fadeOutAndHide.startAnimation(fadeOut)
    }

    private fun View.fadeInAndShow() {
        val before = if (this@fadeInAndShow.visibility == View.VISIBLE) 1f else 0f
        val fadeIn: Animation = AlphaAnimation(before, 1f)
        fadeIn.interpolator = AccelerateInterpolator()
        fadeIn.duration = 200
        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                this@fadeInAndShow.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
        })
        this@fadeInAndShow.startAnimation(fadeIn)
    }

    private fun init() {
        adapter = SimplePageFragmentAdapter(supportFragmentManager, arrayListOf(ProcessingFragment(), DoneFragment(), UserFragment()))
        binding.mainPageView.adapter = adapter
        binding.mainPageView.currentItem = 0

        getProgress()
    }

    private fun needHelp(): Boolean {
        val f = File(applicationContext.filesDir.path + "/notNew")
        return !f.exists()
    }

    override fun onAvatarChange(avatarVersion: Int) {
        changeSmallAvatar()
    }

    private fun getProgress() {
        val user = global.user
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java)).also { finish() }
            return
        }

        GlobalScope.launch {
            post(URL.taskListURL, hashMapOf("userid" to user.uid, "usertoken" to user.token)) {
                Log.d(javaClass.name, it)
                val obj = JsonParser.parseString(it).asJsonObject
                val tasks = ArrayList<TaskItem>()
                obj["task"].asJsonArray.forEach { task ->
                    val id = task.asJsonObject["id"].asString
                    val token = task.asJsonObject["token"].asString
                    val progress = task.asJsonObject["progress"].asInt
                    tasks.add(TaskItem(id, token, progress))
                }
                runOnUiThread {
                    mProgressListener.forEach { listener -> listener.onProgressUpdated(tasks) }
                }
            }
        }
    }

    override fun onUserChange(user: User?) {
        changeSmallAvatar()
    }

    override fun onDestroy() {
        super.onDestroy()
        global.removeAvatarChangeListener(this)
        global.removeOnUserStateChangeListener(this)
        mProgressListener.clear()
    }

    private fun changeSmallAvatar() {
        global.user?.apply {
            Glide.with(this@MainActivity)
                    .load(URL.avatarURL + uid + "&version=" + global.avatarVersion)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.mainImgAvatar)
            binding.mainImgAvatar.setColorFilter(Color.TRANSPARENT)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    interface OnProgressUpdateListener {
        fun onProgressUpdated(items: ArrayList<TaskItem>)
    }

    private val mProgressListener: HashSet<OnProgressUpdateListener> = HashSet()

    fun addOnProgressListener(listener: OnProgressUpdateListener) {
        mProgressListener.add(listener)
    }

    private fun getPermission() {
        if (!EasyPermissions.hasPermissions(context, *permissions)) {
            EasyPermissions.requestPermissions(this, resources.getString(R.string.photo_permission), 1, *permissions)
        }
    }
}