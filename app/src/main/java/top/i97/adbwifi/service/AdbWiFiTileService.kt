package top.i97.adbwifi.service

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ThreadUtils
import top.i97.adbwifi.R
import top.i97.adbwifi.utils.*

/**
 *  name: AdbWiFiTileService
 *  desc: 快捷设置
 *  date: 2020/8/16 4:15 PM
 *  author: Plain
 *  web-site: https://plain-dev.com
 *  email: im@i97.top
 */
@RequiresApi(Build.VERSION_CODES.N)
class AdbWiFiTileService : TileService() {

    override fun onTileAdded() {
        super.onTileAdded()
        update()
    }

    override fun onStartListening() {
        super.onStartListening()
        update()
    }

    override fun onClick() {
        super.onClick()
        qsTile.state = Tile.STATE_UNAVAILABLE
        qsTile.updateTile()
        // 开关 ADB WiFi，根据当前开启状态取反
        enableAdbWiFi(!adbWiFiIsActivated())
        ThreadUtils.runOnUiThreadDelayed({
            update()
            sendBroadcast(Intent(ADB_WIFI_ENABLE_ACTION))
        }, 500)
    }

    private fun update() {
        val activated = adbWiFiIsActivated()
        // update tile state
        qsTile.state = if (activated)
            Tile.STATE_ACTIVE
        else
            Tile.STATE_INACTIVE
        // update tile icon
        qsTile.icon = if (activated)
            Icon.createWithResource(this, R.drawable.ic_qs_network_adb_on)
        else
            Icon.createWithResource(this, R.drawable.ic_qs_network_adb_off)
        // update tile label
        qsTile.label = if (activated) "${getIP()}:${getPort()}" else getString(R.string.app_name)
        // update tile
        qsTile.updateTile()
    }

}