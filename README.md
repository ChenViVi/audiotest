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

`android change voice `

## 结论

除了 SoundTouch 之外的结论

### [FMOD](https://www.fmod.com)

#### 简介

非常强大的音频处理引擎，常用于游戏，支持各种 2D 和 3D 特效。U3D封装了它，而且很多 3A 大作甚至魔兽世界使用过的解决方案

#### 问题

网上的教程千变一律，都只是简单实现了改变声音并播放，并没有实现各种播放操作以及保存功能，也就是只实现了需求的第一点。我自己并没有很好的 C++ 基础，文档的介绍甚至并不直接介绍 C++ 的功能调用方法，而是基于Unitiy 的代码，我无法在短时间内看懂

我也反编译过市面上的产品，发现他们确实是用的 FMOD，并且能实现保存操作，可是对于播放操作却没有处理得很好，目前没有发现任何一款产品是带有进度条的，再就是我不知道如何通过反编译获取 C++ 代码

### [TarsosDSP](https://github.com/JorenSix/TarsosDSP)

#### 简介

一个纯 Java 实现的音频处理库。由个人开发者开发，仅 900 star，多用于检测音频的实时信息，音频处理功能被使用的例子倒是很少

#### 问题

如官网上所说，快速了解此项目有两种方式  运行 [TarsosDSP Example applications](http://0110.be/releases/TarsosDSP/TarsosDSP-latest/TarsosDSP-latest-Examples/) 和查看 [API documentation](http://0110.be/releases/TarsosDSP/TarsosDSP-latest/TarsosDSP-latest-Documentation/)，其实还有很懂东西他写在了个人博客上，源码里边也有 example 的代码，然而这些都不如搜索 Issue 学得快。毕竟他没有完善的文档， 很多 Demo 都无法运行，而且面向 PC 端开发的代码就算能运行拷到 Adnroid Studio 里也无法通过编译

