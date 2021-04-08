## danceView使用方法
  ```xml
             <com.masoudss.lib.DanceView
                android:id="@+id/danceView"
                android:layout_width="320dp"
                android:layout_height="300dp"
                android:layout_gravity="center"
                app:color_center="@color/red"
                app:color_end="@color/white"
                app:color_start="@color/yellow"
                app:dance_color="@color/yellow"
                app:dance_corner_radius="2dp"
                app:dance_gap="2dp"
                app:max_dance_num="30"
                app:min_dance_num="2"
                app:shader_num="3" />

```
```xml
shader_num 顶部加渐变的个数

color_end 渐变尾部颜色

color_start 渐变开头颜色

color_center 渐变中间颜色

min_dance_num 每一列中最少显示的个数

max_dance_num 每一列中最大显示的个数

dance_gap 每一个音频格之间的间距

```

## Visualizer使用

**1、获取实例**

```java
visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
```

 **2、设置采样值**

```java
visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
```

通过Visualizer.getCaptureSizeRange()这一底层实现的方法来返回一个采样值的范围数组，0为最小值128,1为最大值1024！采样值都为2的n次幂！

**3、设置监听器**

```java
setDataCaptureListener(OnDataCaptureListener listener, rate,iswave,isfft )
```

先说后面三个参数：rate采样的频率，下边通过方法Visualizer.getMaxCaptureRate()返回最大的采样频率，单位为milliHertz毫赫兹，iswave是波形信号，isfft是频域信号。
第一个参数OnDataCaptureListener接口，这里可以一个它的匿名内部类，然后它有两个回调方法：

```java
onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate)
```
和
```java
onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate)
```

这两个回调对应着上边的两个参数iswave和isfft！如果iswave为true，isfft为false则会回调onWaveFormDataCapture方法，如果iswave为false，isfft为true则会回调onFftDataCapture方法。


<img src="./files/music.gif" width="300">

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

