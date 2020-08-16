package top.i97.adbwifi

import android.app.Application
import top.i97.adbwifi.utils.GlobalExceptionCapture

/**
 *  name: GlobalApplication
 *  desc: 全局 Application
 *  date: 2020/8/15 9:56 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalExceptionCapture.getInstance(this).setLogPath(externalCacheDir?.absolutePath)
    }

}