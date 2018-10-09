# 音频特效研究

## 简介

[修音变声器 ](http://app.mi.com/details?id=com.planapps.voice)项目的变声效果不够完善，产品需要更好的变声效果

现在采用的方案是 [SoundTouch](http://www.surina.net/soundtouch/)，如其官网所说，SoundTouch 是用来改变音频速度、音调、速率的库。而仅仅依靠这三种参数，是无法达到良好的变声效果的，那么需要新的解决方案来弥补

## 具体需求

1. 实时改变音频声音
2. 播放的开始、暂停、进度显示
3. 保存改变后的音频

## 搜索关键字

使用 Google 和 Github 搜索

`android 变声`

`android audio process`

`android change voice`

`fmod android`

`TarsosDSP android`

## 解决方案

除了 SoundTouch 之外的解决方案

### [FMOD](https://www.fmod.com)

#### 简介

非常强大的音频处理引擎，常用于游戏，支持各种 2D 和 3D 特效。U3D封装了它，而且很多 3A 大作甚至魔兽世界使用过的解决方案

#### 问题

网上的教程千变一律，都只是简单实现了改变声音并播放，并没有实现各种播放操作以及保存功能，也就是只实现了需求的第一点。我自己并没有很好的 C++ 基础，文档的介绍甚至并不直接介绍 C++ 的功能调用方法，而是基于Unitiy 的代码，我无法在短时间内看懂

我也反编译过市面上的产品，发现他们确实是用的 FMOD，并且能实现保存操作，可是对于播放操作却没有处理得很好，目前没有发现任何一款产品是带有进度条的，再就是我不知道如何通过反编译获取 C++ 代码

#### 成果

[FMODTest](https://github.com/ChenViVi/audiotest/tree/master/FMODTest)，克隆整个项目就可以看到了，我看到的所有的开源项目以及教程都是这么做的，千变一律。[ChangeVoice.cpp](https://github.com/ChenViVi/audiotest/blob/346b6534a0477208f7b309ded855f9de5f5c60c4/FMODTest/app/src/main/cpp/ChangeVoice.cpp) 中可以看到，有很多音效参数可以使用，而如何使用 Java 调用也简单得可怕，在 [MainActivity](https://github.com/ChenViVi/audiotest/blob/346b6534a0477208f7b309ded855f9de5f5c60c4/FMODTest/app/src/main/java/com/example/yanchunlan/changevoice/MainActivity.kt) 中，这样使用来播放，根本无法监听播放状态以及保存文件

```
fun clickFix(view : View) {
        when (view.id) {
            R.id.btn_normal -> VoiceUtil.fix(path, VoiceUtil.TYPE_NORMAL)
            R.id.btn_luoli -> VoiceUtil.fix(path, VoiceUtil.TYPE_LOLITA)
            R.id.btn_dashu -> VoiceUtil.fix(path, VoiceUtil.TYPE_UNCLE)
            R.id.btn_jingsong -> VoiceUtil.fix(path, VoiceUtil.TYPE_THRILLER)
            R.id.btn_gaoguai -> VoiceUtil.fix(path, VoiceUtil.TYPE_FUNNY)
            R.id.btn_kongling -> VoiceUtil.fix(path, VoiceUtil.TYPE_ETHEREAL)
        }
    }
```

[反编译不会报错的 App](https://github.com/ChenViVi/audiotest/tree/346b6534a0477208f7b309ded855f9de5f5c60c4/repack)，实现了变声和保存功能，同样无法监听播放状态

### [TarsosDSP](https://github.com/JorenSix/TarsosDSP)

#### 简介

一个纯 Java 实现的音频处理库。由个人开发者开发，仅 900 star，多用于检测音频的实时信息，音频处理功能被使用的例子倒是很少

#### 问题

如官网上所说，快速了解此项目有两种方式  运行 [TarsosDSP Example applications](http://0110.be/releases/TarsosDSP/TarsosDSP-latest/TarsosDSP-latest-Examples/) 和查看 [API documentation](http://0110.be/releases/TarsosDSP/TarsosDSP-latest/TarsosDSP-latest-Documentation/)，其实还有很懂东西他写在了个人博客上，源码里边也有 example 的代码，然而这些都不如搜索 Issue 学得快。毕竟他没有完善的文档， 很多 Demo 都无法运行，而且面向 PC 端开发的代码就算能运行拷到 Adnroid Studio 里也无法通过编译。但毕竟是 Java 的库，播放监听可以靠自己写，而且文件保存也在 Issue 里查到了

#### 成果

[TarsosDSPTest](https://github.com/ChenViVi/audiotest/tree/master/TarsosDSPTest)，采坑无数后终于可以播放音乐，并能添加各种不知道是什么特效的特效了，至于为什么不知道，这个项目坑就坑在文档不全

这里是 [MainActivity.java ](https://github.com/ChenViVi/audiotest/blob/346b6534a0477208f7b309ded855f9de5f5c60c4/TarsosDSPTest/app/src/main/java/com/iwritebug/myapplication/MainActivity.java)中添加特效并播放的方法

```
 public void onPlay(View view) {
        File mp3 = new File("/storage/emulated/0/netease/cloudmusic/Music/Hitchhiker - 11 (ELEVEN).MP3");
        adp = AudioDispatcherFactory.fromPipe(mp3.getAbsolutePath(),44100,5000,2500);
        adp.addAudioProcessor(new PitchShifter(1.1,44100,5000,2500));

        //adp.addAudioProcessor(new FadeIn(10));
        //adp.addAudioProcessor(new NoiseGenerator(8.0));
        //adp.addAudioProcessor(new LowPassFS(1000,44100));
        //adp.addAudioProcessor(new SineGenerator(0.05,220));
        //adp.addAudioProcessor(new AmplitudeLFO(0.1,0.7));
        //adp.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
        /*
        adp.addAudioProcessor(new SineGenerator(0.05,220));
        adp.addAudioProcessor(new AmplitudeLFO(10,0.9));
        adp.addAudioProcessor(new SineGenerator(0.2,440));
        adp.addAudioProcessor(new SineGenerator(0.1,880));
        adp.addAudioProcessor(new DelayEffect(1.5, 0.4, 44100));
        adp.addAudioProcessor(new AmplitudeLFO());
        adp.addAudioProcessor(new NoiseGenerator(0.02));
        adp.addAudioProcessor(new SineGenerator(0.05,1760));
        adp.addAudioProcessor(new SineGenerator(0.01,2460));
        adp.addAudioProcessor(new AmplitudeLFO(0.1,0.7));*/
        //adp.addAudioProcessor(new FlangerEffect(0.1,0.2,44100,4));
        adp.addAudioProcessor(new AndroidAudioPlayer(adp.getFormat(),5000, AudioManager.STREAM_MUSIC));
        new Thread(adp).start();
    }
```

至于这个项目可以实现的全部特效有哪些，我也不知道，因为没有文档，只能在项目中搜索哪些类实现了 `AudioProcessor`，我的 Demo 还参考了 [SynthesisExample.java](https://github.com/JorenSix/TarsosDSP/blob/c26e5004e203ee79be1ec25c2603b1f11b69d276/src/examples/be/tarsos/dsp/example/SynthesisExample.java)