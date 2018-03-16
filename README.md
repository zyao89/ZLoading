# ZLoading
这是一个自定义控件合集。目前包含Loading View。持续更新中...

### 引入
1. Gradle方式引用
```gradle
compile 'com.zyao89:zloading:1.0.11'
```

### 更新说明
> - **1.0.6** 增加字号设置，颜色设置， 解决[#2](https://github.com/zyao89/ZLoading/issues/2)
    补充说明：设置字号后，提示文字不再有动画
> - **1.0.7** 新增 SINGLE_CIRCLE 类型动画， 
    修复 TEXT 类型简单缺陷。
    增加 TEXT、RotateCircleBuilder 类型的使用说明。
> - **1.0.8** 新增 SNAKE_CIRCLE 类型动画（详细见下方）。
> - **1.0.9** 新增 STAIRS_PATH 类型动画（详细见下方 or [跳转](https://www.zyao89.cn/blog/article/5aa40b3657f119669081f473)）。
> - **1.0.10** 新增 MUSIC_PATH 类型动画（详细见下方 or [跳转](https://www.zyao89.cn/blog/article/5aa55576a1d08a29a8017929)）。
> - **1.0.11** 新增 STAIRS_RECT 和 CHART_RECT 类型动画（详细见下方 or [跳转](https://www.zyao89.cn/blog/article/5aac1d06e72bc809a25e58ef)）。

### 使用方式：
1. XML:
```xml
<com.zyao89.view.zloading.ZLoadingView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:z_type="CircleBuilder"
    app:z_color="@color/colorAccent"/>
```
`z_type` 选择加载动画的类型，就是下面介绍的这些；
`z_color` 设置加载动画的颜色；

2. Java
```java
ZLoadingView zLoadingView = (ZLoadingView) findViewById(R.id.loadingView_1);
zLoadingView.setLoadingBuilder(Z_TYPE.CIRCLE);//设置类型
zLoadingView.setColorFilter(Color.WHITE);//设置颜色
```

3. 【新增】Dialog对话框模式
```java
ZLoadingDialog dialog = new ZLoadingDialog(MainActivity.this);
dialog.setLoadingBuilder(type)//设置类型
        .setLoadingColor(Color.BLACK)//颜色
        .setHintText("Loading...")
        .setHintTextSize(16) // 设置字体大小 dp
        .setHintTextColor(Color.GRAY)  // 设置字体颜色
        .show();
```

效果如下：

![效果图](capture/zLoading.gif)


---

### 目前拥有的类型：
索引 | 类名 | 枚举值（Z_TYPE）| 昵称 
:------:|:------:|:------:|:-------:
0|CircleBuilder | CIRCLE | 圆
1|ClockBuilder | CIRCLE_CLOCK | 计时器
2|StarBuilder | STAR_LOADING | 跳舞的星星
3|LeafBuilder | LEAF_ROTATE | 旋转的叶子
4|DoubleCircleBuilder | DOUBLE_CIRCLE | 画两个圈圈
5|PacManBuilder | PAC_MAN | PacMan
6|ElasticBallBuilder | ELASTIC_BALL | 颤抖吧！球球
7|InfectionBallBuilder | INFECTION_BALL | 感染体
8|IntertwineBuilder | INTERTWINE | 交织
9|TextBuilder | TEXT | 文字
10|SearchPathBuilder | SEARCH_PATH | 搜索等待
11|RotateCircleBuilder | ROTATE_CIRCLE | 多圆旋转
12|SingleCircleBuilder | SINGLE_CIRCLE | 单圆简单动画
13|SnakeCircleBuilder | SNAKE_CIRCLE | 引蛇出洞
14|StairsPathBuilder | STAIRS_PATH | 舞动阶梯
15|MusicPathBuilder | MUSIC_PATH | 跳动音符
16|StairsRectBuilder | STAIRS_RECT | 递增方块
17|ChartRectBuilder | CHART_RECT | 跳动的柱状图


### 效果图如下

[第一个，ClockBuilder](markdown/Android自定义动画-ClockLoadingView.md)

![展示图](capture/circle_loading.gif)

[第二个，StarBuilder](https://www.zyao89.cn/blog/article/59aedad0d6571857538cdd4c)

![展示图](capture/star_loading.gif)

[第三个，LeafBuilder](https://www.zyao89.cn/blog/article/59aedc12d6571857538cdd4d)

![展示图](capture/leaf_loading.gif)

[第四个，DoubleCircleBuilder](https://www.zyao89.cn/blog/article/59aedca4d6571857538cdd4e)

![展示图](capture/double_circle.gif)

[第五个，PacManBuilder](https://www.zyao89.cn/blog/article/59aedd70d6571857538cdd4f)

![展示图](capture/pac_man.gif)

[第六个，ElasticBallBuilder](https://www.zyao89.cn/blog/article/59b3bd65d6571857538cdd54)

![展示图](capture/elastic_ball.gif)

[第七个，InfectionBallBuilder](https://www.zyao89.cn/blog/article/59c784d85582770263c49039)

![展示图](capture/infection_ball.gif)

[第八个，IntertwineBuilder](https://www.zyao89.cn/blog/article/59c791785582770263c4903a)

![展示图](capture/intertwine.gif)

[第九个，TextBuilder](https://www.zyao89.cn/blog/article/59d906aa87737d05bab021f5)

![展示图](capture/text_loading.gif)

[第十个，SearchPathBuilder](https://www.zyao89.cn/blog/article/59c79b7f5582770263c4903b)

![展示图](capture/search_path.gif)

[第十一个，RotateCircleBuilder](https://www.zyao89.cn/blog/article/59d927aa43824776ebc77825)

![展示图](capture/rotate_circle.gif)

[第十二个，SingleCircleBuilder](https://www.zyao89.cn/blog/article/59d9284f43824776ebc77826)

![展示图](capture/single_circle.gif)

[第十三个，SnakeCircleBuilder](https://www.zyao89.cn/blog/article/59e3826d86f97804a8dfceab)

![展示图](capture/snake_circle.gif)

[第十四个，StairsPathBuilder](https://www.zyao89.cn/blog/article/5aa40b3657f119669081f473)

![展示图](capture/stairs_path.gif)

[第十五个，MusicPathBuilder](https://www.zyao89.cn/blog/article/5aa55576a1d08a29a8017929)

![展示图](capture/music_path.gif)

[第十六个，StairsRectBuilder](https://www.zyao89.cn/blog/article/5aac1d06e72bc809a25e58ef)

![展示图](capture/stairs_rect.gif)

[第十七个，ChartRectBuilder](https://www.zyao89.cn/blog/article/5aac1d06e72bc809a25e58ef)

![展示图](capture/chart_rect.gif)


更多可访问 [zyao89.cn](https://www.zyao89.cn/blog/article/59aed640d6571857538cdd46)


### License
```
   Copyright 2017 zyao89

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
