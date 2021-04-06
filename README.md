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

