package top.i97.adbwifi.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.ShellUtils
import com.blankj.utilcode.util.Utils

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
 * 无线调试是否开启
 */
fun adbWiFiIsActivated(): Boolean {
    val successMsg = ShellUtils.execCmd(
        "getprop service.adb.tcp.port\n",
        true,
        true
    ).successMsg
    return successMsg.matches("^[1-9]\\d*\$".toRegex()) && !successMsg.contains("-1");
}

/**
 * 开关无线调试
 *
 * [enable] true: 打开 , false: 关闭
 */
fun enableAdbWiFi(enable: Boolean) {
    ShellUtils.execCmd(
        arrayListOf(
            "setprop service.adb.tcp.port " + (if (enable) getPort() else "-1") + "\n",
            "stop adbd\n",
            "start adbd\n",
            "exit\n"
        ), true
    )
}