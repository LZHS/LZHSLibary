#  LZHSLibary依赖库
[![](https://img.shields.io/badge/LZHSLibary-lzhs_v1.0.1-brightgreen.svg)](https://jitpack.io/#LZHS/LZHSLibary)

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
    implementation 'com.github.LZHS:LZHSLibary:lzhs_v1.0.1'
}
```  


### 使用说明
使用该库直接导入就行了，***重要的事情说三遍、重要的事情说三遍、重要的事情说三遍***  
具体的初始化操作全部放在[InitProvider][InitProvider],具体原理参见[android自定初始化][android自定初始化]    

> 利用 ContentProvider 来初始化,不用开发者编写任何代码。使用 ContentProvider 的原因有2个：  

> * 他们创建初始化在优先于其他组件（主线程），在 Activity、Service、BroadcastReceivers 之前
> * 在 manifest 合并的时候（编译期间) , 会把 Android 库里面的 ContentProvider 和 主 manifest 合并到一起。

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
* [StatusView][StatusView] android 状态控件
* [LZHSFoldText][LZHSFoldText] android 可折叠TextView
* [LZHSBanner][LZHSBanner] android Banner


**第三方库**
> 
* [SwipeBackLayout][SwipeBackLayout]   


### DemoAPK

[DemoAPK][DemoAPK]

 <img src="https://github.com/LZHS/LZHSLibary/blob/develop/images/apk_qr_cord.png?raw=true" width = 40% height = 40% />


[android自定初始化]:https://juejin.im/entry/58c654ffda2f605dc5ab411d
 
[InitProvider]:https://github.com/LZHS/LZHSLibary/blob/develop/Library/src/main/java/com/lzhs/library/InitProvider.java

[BaseAdapter]:https://github.com/hongyangAndroid/baseAdapter

[SwipeBackLayout]:https://github.com/ThirteenKilometers/SwipeBackLayout

[DemoAPK]:https://github.com/LZHS/LZHSLibary/raw/develop/apk/app-debug.apk

[Home]:https://github.com/LZHS/LZHSLibary/wiki

[LogUtils]:https://github.com/LZHS/LZHSLibary/wiki/LogUtils

[PermissionUtil]:https://github.com/LZHS/LZHSLibary/wiki/PermissionUtil

[StatusBarUtil]:https://github.com/LZHS/LZHSLibary/wiki/StatusBarUtil

[CommonAdapter]:https://blog.csdn.net/lmj623565791/article/details/38902805

[LZHSFoldText]:https://github.com/LZHS/LZHSFoldText

[LZHSBanner]:https://github.com/LZHS/LZHSBanner

[StatusView]:https://github.com/LZHS/StatusView

