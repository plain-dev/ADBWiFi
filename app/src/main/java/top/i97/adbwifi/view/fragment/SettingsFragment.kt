package top.i97.adbwifi.view.fragment

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.blankj.utilcode.util.ToastUtils
import top.i97.adbwifi.R
import top.i97.adbwifi.Utils.getPort
import top.i97.adbwifi.Utils.setPort

/**
 *  name: SettingsFragment
 *  desc: 设置页面⚙️
 *  date: 2020/8/15 10:36 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val portPrefKeys by lazy { getString(R.string.preference_key_port) }
    private val rebootPrefKeys by lazy { getString(R.string.preference_key_reboot) }
    private val powerOffPrefKeys by lazy { getString(R.string.preference_key_power_off) }
    private val softRebootPrefKeys by lazy { getString(R.string.preference_key_soft_reboot) }

    private val portPref by lazy { findPreference<EditTextPreference>(portPrefKeys) }
    private val rebootPref by lazy { findPreference<Preference>(rebootPrefKeys) }
    private val powerOffPref by lazy { findPreference<Preference>(powerOffPrefKeys) }
    private val softRebootPref by lazy { findPreference<Preference>(softRebootPrefKeys) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        handleEditPort()
        handleSystemPref()
    }

    private fun handleEditPort() {
        portPref?.setOnBindEditTextListener {
            it.inputType = InputType.TYPE_CLASS_NUMBER
            val port = getPort()
            it.setText(port)
            it.setSelection(port.length)
        }

        portPref?.setOnPreferenceChangeListener { _, newValue ->
            val newPort = newValue.toString()
            if (checkNewPortValid(newPort)) {
                setPort(newPort)
                ToastUtils.showShort(R.string.save_port_success, newPort)
                true
            } else {
                ToastUtils.showShort(R.string.save_port_failed)
                false
            }
        }
    }

    private fun handleSystemPref() {
        rebootPref?.setOnPreferenceClickListener {
            ToastUtils.showShort("正在施工🚧")
            true
        }
        powerOffPref?.setOnPreferenceClickListener {
            ToastUtils.showShort("正在施工🚧")
            true
        }
        softRebootPref?.setOnPreferenceClickListener {
            ToastUtils.showShort("正在施工🚧")
            true
        }
    }

    private fun checkNewPortValid(newPort: String): Boolean {
        return newPort.isEmpty() || newPort.toInt() in 0..10000
    }


}