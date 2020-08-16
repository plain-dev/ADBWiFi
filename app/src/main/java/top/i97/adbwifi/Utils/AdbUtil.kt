package top.i97.adbwifi.Utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ShellUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import top.i97.adbwifi.R

/**
 *  name: AdbUtil
 *  desc: ADB 工具 🚌
 *  date: 2020/8/16 9:56 AM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */

fun getSharedPreferences(): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(Utils.getApp())
}

fun setPort(port: String) = getSharedPreferences().edit().putString(PORT_KEY, port).apply()

fun getPort(): String {
    return getSharedPreferences().getString(PORT_KEY, DEFAULT_PORT) ?: DEFAULT_PORT
}

/**
 * 开关无线调试
 *
 * [enable] true: 打开 , false: 关闭
 */
fun enableAdbWifi(enable: Boolean) {
    ShellUtils.execCmd(
        arrayListOf(
            "setprop service.adb.tcp.port " + (if (enable) getPort() else "-1") + "\n",
            "stop adbd\n",
            "start adbd\n",
            "exit\n"
        ), true, true
    )
}