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
    implementation 'com.github.LZHS:LZHSLibary:lzhs_v1.0.6'
}
```  


### 使用  

```        
    Utils.init(this);
```  

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
* [SwipeBackLayout][SwipeBackLayout]  
* [BaseAdapter][BaseAdapter]
* [AndroidAutoLayout][AndroidAutoLayout]


[AndroidAutoLayout]:https://github.com/hongyangAndroid/AndroidAutoLayout

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

