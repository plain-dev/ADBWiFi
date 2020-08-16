package top.i97.adbwifi.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

/**
 *  name: GlobalExceptionCapture
 *  desc: 全局异常捕获
 *  date: 2020/8/16 4:28 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class GlobalExceptionCapture private constructor(private val context: Context) :
    Thread.UncaughtExceptionHandler {

    /**
     * 奔溃日志存放路径
     */
    private var logPath: String? = null

    /**
     * 设备信息 📱
     */
    private val deviceInfo = LinkedHashMap<String, String>()

    /**
     * 日期格式化 📅
     */
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    companion object {
        @Volatile
        private var instance: GlobalExceptionCapture? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: GlobalExceptionCapture(context).also {
                    instance = it
                }
            }
    }

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 外部设置日志存放路径
     */
    fun setLogPath(logPath: String?) {
        this.logPath = logPath
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        // 处理未捕获异常 ⚠️
        handleUncaughtException(e)
    }

    /**
     * 处理未捕获异常 ⚠️
     */
    private fun handleUncaughtException(e: Throwable) {
        logPath?.apply {
            val dir = File(this)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val newFile = File(dir, "Exception-${System.currentTimeMillis()}.txt")
            if (newFile.exists()) {
                newFile.delete()
            }
            try {
                newFile.createNewFile()
                val io = FileOutputStream(newFile)
                val ps = PrintStream(io);
                ps.print(getLogSummary(e));
                ps.close()
            } catch (e: Throwable) {
                //Empty
            }
        }
    }

    /**
     * 获取设备信息 📱
     */
    private fun putInfoToMap(context: Context) {
        deviceInfo["设备型号"] = Build.MODEL
        deviceInfo["设备品牌"] = Build.BOARD
        deviceInfo["硬件名称"] = Build.HARDWARE
        deviceInfo["硬件制造商"] = Build.MANUFACTURER
        deviceInfo["系统版本"] = Build.VERSION.RELEASE
        deviceInfo["系统版本号"] = "${Build.VERSION.SDK_INT}"

        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        if (pi != null) {
            deviceInfo["应用版本"] = pi.versionName
            deviceInfo["应用版本号"] = "${PackageInfoCompat.getLongVersionCode(pi)}"
        }
    }

    /**
     * 获取日志头信息 📒
     */
    private fun getLogHeader(): StringBuffer {
        val sb = StringBuffer()
        sb.append("时间: ${dateFormat.format(Date())}\n")
        putInfoToMap(context)
        deviceInfo.entries.forEach {
            sb.append("${it.key}: ${it.value}\n")
        }
        return sb
    }

    /**
     * 获取日志概要 📖
     */
    private fun getLogSummary(e: Throwable): String {
        val sb = getLogHeader().append("\n")
        sb.append("调用栈信息\n")
        sb.append("${e.javaClass.toString().substring(6)}: ${e.message}\n")
        for (i in e.stackTrace.indices) {
            sb.append("    ").append("at ")
            sb.append(e.stackTrace[i].className)
            sb.append(".")
            sb.append(e.stackTrace[i].methodName)
            sb.append("(${e.stackTrace[i].fileName}:${e.stackTrace[i].lineNumber})")
            sb.append("\n")
        }
        return sb.toString()
    }

}