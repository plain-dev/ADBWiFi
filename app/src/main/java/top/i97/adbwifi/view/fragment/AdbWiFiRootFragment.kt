package top.i97.adbwifi.view.fragment

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.NotificationUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_adb_wifi_root.*
import top.i97.adbwifi.R
import top.i97.adbwifi.utils.*
import top.i97.adbwifi.view.MainActivity

/**
 *  name: AdbWiFiRootFragment
 *  desc: 无线调试页面
 *  date: 2020/8/15 10:35 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class AdbWiFiRootFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    companion object {

        private const val NOTIFY_ID = 0x101

        fun newInstance(): AdbWiFiRootFragment {
            val args = Bundle()
            val fragment = AdbWiFiRootFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val enableReceiver by lazy {
        AdbWiFiEnableReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adb_wifi_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 更新状态
        updateState(adbWiFiIsActivated())
        // Switch click listener
        switchAdbWiFi.setOnCheckedChangeListener(this)
        registerReceiver()
    }

    private fun registerReceiver() {
        requireActivity().registerReceiver(enableReceiver, IntentFilter(ADB_WIFI_ENABLE_ACTION))
    }

    private fun unRegisterReceiver() {
        requireActivity().unregisterReceiver(enableReceiver)
    }

    override fun onResume() {
        super.onResume()
        checkRootState()
    }

    /**
     * 检查Root权限授予情况
     */
    private fun checkRootState() {
        if (!AppUtils.isAppRoot()) {
            tvWarring.visibility = View.VISIBLE
            ToastUtils.showShort(R.string.no_root_privileges_granted)
        } else {
            tvWarring.visibility = View.GONE
        }
    }

    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        if (checkWiFi()) {
            if (isChecked) {
                enableAdbWiFi(true)
                updateMessage(true)
                ToastUtils.showShort(R.string.adb_wifi_turned_on)
            } else {
                enableAdbWiFi(false)
                updateMessage(false)
                ToastUtils.showShort(R.string.adb_wifi_turned_off)
            }
        } else {
            view.isChecked = false
            ToastUtils.showShort(R.string.not_connected_wifi)
        }
    }

    private fun updateState(enable: Boolean) {
        updateChecked(enable)
        updateMessage(enable)
    }

    private fun updateMessage(enable: Boolean) {
        if (enable) {
            tvMessage.text = getString(R.string.adb_connect_message, getIP(), getPort())
        } else {
            tvMessage.text = getString(R.string.adb_disconnect_message)
        }
        notify(tvMessage.text.toString())
    }

    private fun updateChecked(isChecked: Boolean) {
        switchAdbWiFi.isChecked = isChecked
    }

    private fun notify(message: String) {
//        NotificationUtils.notify(NOTIFY_ID) {
//            it.setSmallIcon(R.drawable.ic_adb)
//                .setContentText(message)
//                .setContentIntent(
//                    PendingIntent.getActivity(
//                        requireActivity(),
//                        0,
//                        Intent(requireActivity(), MainActivity::class.java),
//                        0
//                    )
//                )
//                .setAutoCancel(false)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unRegisterReceiver()
        NotificationUtils.cancelAll()
    }

    private inner class AdbWiFiEnableReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ADB_WIFI_ENABLE_ACTION) {
                updateState(adbWiFiIsActivated())
            }
        }

    }

}