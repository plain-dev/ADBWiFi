package top.i97.adbwifi.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 *  name: SettingsFragment
 *  desc: 设置页面⚙️
 *  date: 2020/8/15 10:36 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class SettingsFragment:Fragment() {

    companion object{
        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}