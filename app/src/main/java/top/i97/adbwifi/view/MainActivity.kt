package top.i97.adbwifi.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.blankj.utilcode.util.FragmentUtils
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

    private val fragments by lazy {
        mutableListOf(adbWiFiRootFragment, settingsFragment, aboutFragment)
    }

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initFragments()
        setToolbar()
        setDrawerAndNavigation()
        setShortcut()
    }

    private fun initFragments() {
        FragmentUtils.add(
            supportFragmentManager,
            fragments,
            R.id.container,
            arrayOf("fm1", "fm2", "fm3"),
            currentIndex
        )
    }

    private fun setToolbar() {
        toolbar.apply {
            setSupportActionBar(this)
            //tvTitle.text = getString(R.string.app_name)
            title = getString(R.string.app_name)
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
            R.id.nav_adb_root -> {
                showCurrentFragment(0)
                title = getString(R.string.action_adb_wifi_root)
            }
            R.id.nav_settings -> {
                showCurrentFragment(1)
                title = getString(R.string.action_settings)
            }
            R.id.nav_about -> {
                showCurrentFragment(2)
                title = getString(R.string.action_about)
            }
        }

        // navigation selected
        navigation.setCheckedItem(itemId)
    }

    private fun showCurrentFragment(index: Int) {
        currentIndex = index
        FragmentUtils.showHide(index, fragments)
    }

    override fun onBackPressed() {
        // 关闭导航🧭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        if (!FragmentUtils.dispatchBackPress(fragments[currentIndex])) {
            super.onBackPressed()
        }
    }

}