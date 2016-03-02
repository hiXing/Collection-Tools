#如何高效学习Android动画？
作者：lightSky
链接：https://www.zhihu.com/question/27718787/answer/53012226
来源：知乎

曾经也对Android中的动画不是很清楚，什么帧，Tween，属性等等，很多知识，也很琐碎，后来觉得动画这一块是很多开发人员绕不过的吧，虽然每次通过google或者百度就可以基本满足需求，但是总觉得太零散，太模糊。其实当时系统学习Android动画还有一部分原因：将来成为一个android组件开发工程师，如果想做出一些高性能，nice的动画就必须对动画的基础知识有深入的理解，因此就打算系统的学习一下。经过一段时间的专业学习，android动画的整体以及简单的动画实现都不是问题了，后来没有再深入，是因为自己的兴趣方向变了。现在简单分享一些自己的学习成果和心得吧。

自己整个学习过程中的产出的文章:
- http://www.lightskystreet.com/2014/12/03/view-and-property-anim-knowldege-and-compare/

- http://www.lightskystreet.com/2014/12/04/propertyview-anim-analysis/

- http://www.lightskystreet.com/2014/12/10/propertyview-anim-practice/
 
- http://www.lightskystreet.com/2014/12/15/viewpager-anim/
 
- http://www.lightskystreet.com/2015/05/23/anim_basic_knowledge/  

（由于 http://www.zhihu.com/people/033aaa7b41577bdf4f5344943a28dbb0 发起的http://codekk.com/open-source-project-analysis下的开源项目原理分析项目https://github.com/android-cn/android-open-project-analysis 二期中，很多分析项目涉及到了动画，所以该篇主要是对PropertyAnim详解和基础篇中的一些知识进行整理，作为二期的动画公共知识，http://codekk.com/open-source-project-analysis绝对是超级棒的项目，大家可以多关注）
从
 http://www.lightskystreet.com/2014/12/03/view-and-property-anim-knowldege-and-compare/
以及
 http://www.lightskystreet.com/2015/05/23/anim_basic_knowledge/
 这两篇文章，你可以对android动画总体有些了解，
http://www.lightskystreet.com/2014/12/04/propertyview-anim-analysis/
 
 这一篇则是专门介绍了3.0出现的属性动画，其中也涉及到了[JakeWharton大神](https://github.com/JakeWharton)为3.0之前实现的兼容库[NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)。  
 通过ViewPager来实现动画效果的方式越来越多，一些视差效果也确实很赞，比如雅虎天气的视差效果，知乎导航界面中元素的视差，关于它们的实现原理，在  http://www.lightskystreet.com/2014/12/15/viewpager-anim/    这篇中有详细的分析，其中也提到了GitHub上一些和ViewPager动画类似的实现，也分析了它们之间的区别，具体可以参考文章。

#####  PropertyAnim 
实际应用主要介绍了一些简单API的使用，亮点是其中介绍了很多GitHub 上的动画项目，其中实现多数使用了[NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)兼容库，可以作为不错的学习资料。
上面几篇文章中都有非常详细的介绍，就不多说了，因为文章中对于一些概念，使用，原理介绍的确实太详细了，现在想起来，当时钻的确实有点深了，比如
http://www.lightskystreet.com/2014/12/04/propertyview-anim-analysis/
这篇文章，对属性动画中的ValueAnimator，Evaluators，Interpolators的介绍以及TimeInterpolator和TypeEvaluator的比较等等，具体的可以参考文章。这几篇文章看下来，你应该对android动画有一些更系统更深入的认识了。
基本的动画实现应该没啥难度了。每篇文章的信息量很是很大的，因为在学习过程中主要是通过大量的GitHub动画项目，也参考了不少资料，在文章结尾处有提及。  
   如果你还想进阶，这里再分享一些不错的项目，这些项目都是在android动画基础上建立起来的，所以看他们的源码，完全没有难度：
#####开源组织 Yalantis： https://github.com/Yalantis，

该组织开源了很多特效的项目。很新颖，很有创意，绝对眼前一亮，可以作为不错的学习资料：
1. https://github.com/Yalantis/GuillotineMenu-Android   锉刀效果，不过我看跟拍电影时的打板有点像
2. https://github.com/Yalantis/Phoenix   很有创意的下拉动画
3. https://github.com/Yalantis/Taurus    “升起的太阳”,也是下拉动画，
4. https://github.com/Yalantis/Context-Menu.Android . “滑翔机”,菜单动画，不过个人觉得貌似有点太炫技了
5. https://github.com/Yalantis/Side-Menu.Android 同样是菜单动画，不过加上了Reveal的转场效果
6. https://github.com/Yalantis/FlipViewPager.Draco 折叠动画
7. https://github.com/Yalantis/Euclid 转场动画，感觉可以归为 MaterialDesign 设计风格

#####代码家  https://github.com/daimajia

1. https://github.com/daimajia/AndroidViewAnimations  动画集合
2. https://github.com/daimajia/AndroidImageSlider    ViewPager动画集合
3. https://github.com/daimajia/AnimationEasingFunctions 定义了很多Evaluator，你也可以寻找自己的函数来实现更赞的动画

#####@Android笔记 http://www.race604.com/
1. 纸飞机 https://github.com/race604/FlyRefresh ，效果真是sweet，配上Blog，看看作者的思路和实现原理：http://www.race604.com/flyrefresh/

---------
关于我的几篇动画文章，如有不准确的地方，还望指正，希望对您和以后有兴趣学习动画的朋友有些帮助。其它更多动画相关学习资料或者不错的开源库后面补充吧，还是那句话，原理都一样，脑洞不够大，去https://dribbble.com/找找灵感吧。

分享一下最近star的几个不错的动画库：
* https://github.com/TakeoffAndroid/AppIntroAnimation
* https://github.com/recruit-lifestyle/WaveSwipeRefreshLayout
* https://github.com/recruit-lifestyle/BeerSwipeRefresh
* https://github.com/dodola/MetaballLoading
* https://github.com/Q42/AndroidScrollingImageView

-----------
    
   来更新一下,因为前两天看到了Google工程师NickButcher放出了他们在droidconuk15上演讲的Buiding Meaningful Motion 的slides，看了之后特别激动。
slides共100多张，特别精细，内容质量也相当高，无论是Slide还是内容都非常棒。整个演讲以[iosched](https://github.com/google/iosched)，[googlesamples/android-topeka · GitHub](https://github.com/googlesamples/android-topeka)以及NickButcher自己的plaid · GitHub作为演示项目，通过各种motion的具体实现去展示如何构建有意义的动效。

从slides来看，这次演讲的内容主要介绍了一些Android 动画的一些基础知识，一些之前在上面的文章中写过的一些动画API：ViewPropertyAnimator，ObjectAnimator，Interpolator。
另外还介绍了一些MaterialDesign风格的动画类和概念：Circular Reveals ,AnimatedVectorDrawable,Window content transition，Shared element transitions，这些在5.0之前都是没有的，5.0自带了transition，circualr reveals这样的API，使得动画的种类更多样，同时动画的性能方面也表现的更好，一些support lib的支持，也可以让你在老版本上实现一些5.0中的动画效果。
具体的可以参考 [googlesamples/android-topeka · GitHub](https://github.com/googlesamples/android-topeka) ，google官方放出的Demo，刚放出来的时候只支持5.0，现在通过support lib兼容到了4.0了，可以作为不错的参考项目。我觉得有必要强调一下”有意义“，有时候很多开发者有时候在炫技，动画很生硬，转场不自然。包括Yalantis开源的个别项目也是。NickButcher的这场演讲就来告诉你，如何构建有意义的动效，当然有些产品中的动效还和交互设计师的专业水平有关。所以可以仔细琢磨下slides和sample 项目中的动画实现，一些细节的把握。这次演讲的视频还未放出，不过精美的slides已经是整个演讲的精髓了，强烈建议大家看看，对于动画学习很有帮助；https://plus.google.com/+RomanNurik/posts/AwXCwixwnCb （自备梯子）