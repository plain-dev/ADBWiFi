package top.i97.adbwifi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_app_bar.*
import top.i97.adbwifi.R
import top.i97.adbwifi.view.fragment.AboutFragment
import top.i97.adbwifi.view.fragment.AdbWiFiRootFragment
import top.i97.adbwifi.view.fragment.SettingsFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val drawerToggle by lazy {
        ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
    }

    private val fm by lazy {
        supportFragmentManager
    }

    private val adbWiFiRootFragment by lazy {
        AdbWiFiRootFragment.newInstance()
    }

    private val settingsFragment by lazy {
        SettingsFragment.newInstance()
    }

    private val aboutFragment by lazy {
        AboutFragment.newInstance()
    }

    private var doubleBackTag = false

    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setToolbar()
        tvTitle.text = getString(R.string.app_name)
        setDrawerAndNavigation()
        setShortcut()
    }

    private fun setToolbar() {
        toolbar.apply {
            setSupportActionBar(this)
            tvTitle.text = getString(R.string.app_name)
        }
    }

    // 通过桌面 Shortcut 调用
    private fun setShortcut() {
        when (intent.action) {
            getString(R.string.shortcut_action_setting) -> handleSelectedPage(R.id.nav_settings)
            getString(R.string.shortcut_action_about) -> handleSelectedPage(R.id.nav_about)
            else -> handleSelectedPage(R.id.nav_adb_root)
        }
    }

    private fun setDrawerAndNavigation() {
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigation.setNavigationItemSelectedListener(this)
        navigation.itemIconTintList = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        handleSelectedPage(item.itemId)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * 🚜处理选中页面
     */
    private fun handleSelectedPage(itemId: Int) {
        when (itemId) {
            R.id.nav_adb_root -> fragment = adbWiFiRootFragment
            R.id.nav_settings -> fragment = settingsFragment
            R.id.nav_about -> fragment = aboutFragment
        }

        // 替换页面
        fm.beginTransaction()
            .setCustomAnimations(
                android.R.animator.fade_in,
                android.R.animator.fade_out,
                android.R.animator.fade_in,
                android.R.animator.fade_out
            )
            .replace(R.id.container, fragment)
            .addToBackStack(itemId.toString())
            .commit()
    }

    override fun onBackPressed() {
        // 关闭导航🧭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        if (doubleBackTag) {
            finish()
        } else {
            ToastUtils.showShort(R.string.double_click_exit)
        }
        doubleBackTag = true
        ThreadUtils.runOnUiThreadDelayed({ doubleBackTag = false }, 2000)
    }

}