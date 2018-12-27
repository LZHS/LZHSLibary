#  LZHSLibary依赖库
[![](https://img.shields.io/badge/LZHSLibary-lzhs_v1.0.6-brightgreen.svg)](https://jitpack.io/#LZHS/LZHSLibary)

### gradle
Step 1.Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
 		 ...
 		 maven { url 'https://jitpack.io' }
 	}
}
```

Step 2. Add the dependency

```
dependencies {
    implementation 'com.github.LZHS:LZHSLibary:lzhs_v1.0.0'
}
```  


### 使用说明


### 提供供功能
1. [AndroidAutoSize][AndroidAutoSize]
该库引用了**AndroidAutoSize**该库是今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案，所以移除了张鸿洋大神的[AndroidAutoLayout][AndroidAutoLayout]
使用注意：本人修改了该库的默认值，这原版中这两个属性必填，目前改为如果你不填写该属性，默认是苹果设计图的尺寸**750*1334**
```
<manifest>
    <application>
        <meta-data
            android:name="design_width_in_dp"
            android:value="750"/>
        <meta-data
            android:name="design_height_in_dp"
            android:value="1334"/>
     </application>
</manifest>  ```


### DemoAPK

[DemoAPK][DemoAPK]   

 <img src="https://github.com/LZHS/LZHSLibary/blob/master/images/qrcode.png" width = 30% height = 40% />


### 功能说明  
**工具类**
> 
* [Home][Home]   
* [android 日志打印器][LogUtils]   
* [PermissionUtil][PermissionUtil]
* [StatusBarUtil android 实现状态栏 工具类][StatusBarUtil]
* [张鸿洋万能适配器][CommonAdapter]

**自定义控件**
>  
* [StatusView][StatusView]
* [LZHSFoldText][LZHSFoldText]
* [LZHSBanner][LZHSBanner]


**第三方库**
>
* [AndroidAutoSize][AndroidAutoSize]
* [SwipeBackLayout][SwipeBackLayout]  
* [BaseAdapter][BaseAdapter]


[AndroidAutoLayout]:https://github.com/hongyangAndroid/AndroidAutoLayout

[AndroidAutoSize]:https://github.com/JessYanCoding/AndroidAutoSize

[BaseAdapter]:https://github.com/hongyangAndroid/baseAdapter

[SwipeBackLayout]:https://github.com/ThirteenKilometers/SwipeBackLayout

[DemoAPK]:https://github.com/LZHS/LZHSLibary/raw/master/testApk/LZHSDemo.apk

[Home]:https://github.com/LZHS/LZHSLibary/wiki

[LogUtils]:https://github.com/LZHS/LZHSLibary/wiki/LogUtils

[PermissionUtil]:https://github.com/LZHS/LZHSLibary/wiki/PermissionUtil

[StatusBarUtil]:https://github.com/LZHS/LZHSLibary/wiki/StatusBarUtil

[CommonAdapter]:https://blog.csdn.net/lmj623565791/article/details/38902805

[LZHSFoldText]:https://github.com/LZHS/LZHSFoldText

[LZHSBanner]:https://github.com/LZHS/LZHSBanner

[StatusView]:https://github.com/LZHS/LZHSLibary/blob/master/library/src/main/java/lzhs/com/library/wedgit/views/StatusView.java

