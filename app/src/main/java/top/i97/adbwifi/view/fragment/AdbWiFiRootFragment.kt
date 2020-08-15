package top.i97.adbwifi.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 *  name: AdbWiFiRootFragment
 *  desc: 无线调试页面
 *  date: 2020/8/15 10:35 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class AdbWiFiRootFragment : Fragment() {

    companion object {
        fun newInstance(): AdbWiFiRootFragment {
            val args = Bundle()
            val fragment = AdbWiFiRootFragment()
            fragment.arguments = args
            return fragment
        }
    }



}