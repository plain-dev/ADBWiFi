package top.i97.adbwifi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import top.i97.adbwifi.R

/**
 *  name: AboutFragment
 *  desc: 关于页面
 *  date: 2020/8/15 10:37 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class AboutFragment : Fragment() {

    companion object {
        fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

}