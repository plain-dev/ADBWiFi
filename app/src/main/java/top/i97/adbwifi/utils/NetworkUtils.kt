package top.i97.adbwifi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import com.blankj.utilcode.util.Utils

/**
 *  name: NetworkUtils
 *  desc: 网络工具 🌍
 *  date: 2020/8/16 9:53 AM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */

/**
 * 检查 Wi-Fi
 */
fun checkWiFi(): Boolean {
    val cm = Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    if (activeNetwork != null) { // connected to the internet
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm.getNetworkCapabilities(cm.activeNetwork)
                    .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            ) {
                // connected to wifi
                return true
            } else if (cm.getNetworkCapabilities(cm.activeNetwork)
                    .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            ) {
                // connected to the mobile provider's data plan
                return false
            }
        } else {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return false
            }
        }
    } else {
        // not connected to the internet
        return false
    }
    return false
}

/**
 * 获取 IP
 */
fun getIP(): String {
    val wifiMgr =
        Utils.getApp().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo = wifiMgr.connectionInfo
    val ip = wifiInfo.ipAddress
    return String.format(
        "%d.%d.%d.%d",
        ip and 0xff,
        ip shr 8 and 0xff,
        ip shr 16 and 0xff,
        ip shr 24 and 0xff
    )
}