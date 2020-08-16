<div align="center">
  <img src="./app/src/main/ic_launcher-playstore.png" width='150px' alt="ic_launcher-web">
</div>

# ADB WiFi 无线调试

> 🦥&nbsp;在手机上开启无线调试（需要Root）
> 
> Turn on wireless debugging on the phone (Root required)

## 预览

<table>
    <tr>
        <td>首页 🏠</td> 
        <td>导航 🧭</td> 
        <td>设置 ⚙️</td> 
   </tr>
    <tr>
        <td ><img src='./screenshot/screenshot_home.jpg' /></td>  
        <td ><img src='./screenshot/screenshot_navigation.jpg' /></td>  
        <td ><img src='./screenshot/screenshot_settings.jpg' /></td> 
    </tr>
</table>

## 使用帮助

### 基本

🔧&nbsp;使用本工具开启无线调试后，请在电脑终端中运行以下命令

- 🔗&nbsp;连接设备:&nbsp;adb connect [IP]:[PORT]

- 📱&nbsp;查看已连接设备列表:&nbsp;adb devices

- 🔪&nbsp;️断开连接:&nbsp;adb disconnect [IP]:[PORT]

### 快捷开关

在&nbsp;Android7.0&nbsp;及以上系统支持通知栏快捷开关

开启方式: `下拉通知栏` -> `编辑菜单` -> `添加开关`，如图

<img src="./screenshot/screenshot_quick_tile.jpg" height = "500" align=center />

## License

```
MIT License

Copyright (c) 2020 Plain

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```