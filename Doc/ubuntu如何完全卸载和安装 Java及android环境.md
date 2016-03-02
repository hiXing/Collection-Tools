#ubuntu如何完全卸载和安装 Java及android环境?

注意，以下手法请斟酌使用，我仅仅使用了第二种方法中的自动卸载方法卸载，然后重新apt安装的。（OS:ubuntu 12.10）

##一、卸载旧有包
####大神法一：

1、 移除所有 Java相关包 (Sun, Oracle, OpenJDK, IcedTea plugins, GIJ):

	# apt-get update
	# apt-cache search java | awk '{print($1)}' | grep -E -e '^(ia32-)?(sun|oracle)-java' -e '^openjdk-' -e '^icedtea' -e '^(default|gcj)-j(re|dk)' -e '^gcj-(.*)-j(re|dk)' -e 'java-common' | xargs sudo apt-get -y remove
	# apt-get -y autoremove
		
2、清除配置信息

	# dpkg -l | grep ^rc | awk '{print($2)}' | xargs sudo apt-get -y purge
3、清除java配置及缓存

	# bash -c 'ls -d /home/*/.java' | xargs sudo rm -rf
4、手动清除JVMs

	# rm -rf /usr/lib/jvm/*
5、清除所有java实体，如果还有，则使用Alternatives命令（Remove Java entries, if there is still any, from the alternatives）：

	# for g in ControlPanel java java_vm javaws jcontrol jexec keytool mozilla-javaplugin.so orbd pack200 policytool rmid rmiregistry servertool tnameserv unpack200 appletviewer apt extcheck HtmlConverter idlj jar jarsigner javac javadoc javah javap jconsole jdb jhat jinfo jmap jps jrunscript jsadebugd jstack jstat jstatd native2ascii rmic schemagen serialver wsgen wsimport xjc xulrunner-1.9-javaplugin.so; do sudo update-alternatives --remove-all $g; done
6、查找可能存在 Java的路径:

	# updatedb
	# locate -b '\pack200'
  如果上述命令产生了任何类似于这样（ /path/to/jre1.6.0_34/bin/pack200）的结果，则 删除 bin对父目录,可以这样做: sudo rm -rf /path/to/jre1.6.0_34

####大神法二：

1、卸载之前，先检查Java对安装情况

	# update-alternatives --display java
To check the setup before uninstalling Java.

接下来，删除符号链接

2、（替换(version)为你的java具体版本.命令 “java -version”来得到，我的所1.7.0_15，那么你可以用命令 update-alternatives --remove "java" "/usr/lib/jvm/jdk1.7.0_15/bin/java"来删除）

	# update-alternatives --remove "java" "/usr/lib/jvm/jdk<version>/bin/java"
	# update-alternatives --remove "javac" "/usr/lib/jvm/jdk<version>/bin/javac"
	# update-alternatives --remove "javaws" "/usr/lib/jvm/jdk<version>/bin/javaws"
	
确认这些符号链接（symlinks）是否都删除了

	# java -version
	# javac -version
	# which javaws
后面俩命令必须要慎之又慎，不然可能毁掉你的系统.

	# cd /usr/lib/jvm
	# sudo rm -rf jdk<version>
然后，

	# update-alternatives --config java
	# update-alternatives --config javac
	# update-alternatives --config javaws
最后，

	# vi  /etc/environment
删除JAVA_HOME 这行环境变量。

-----------------分割线---------------------------------

要删除 OpenJDK (如果已安装的话)。首先，检查是安装的哪个 OpenJDK包。

	# dpkg --list | grep -i jdk
移除 openjdk包:

	# apt-get purge openjdk*
卸载 OpenJDK 相关包：

	# apt-get purge icedtea-* openjdk-*
检查所有 OpenJDK包是否都已卸载完毕：

	# dpkg --list | grep -i jdk
完毕。


##二、安装新java环境

   有了上面卸载经验，自然环境搭建经验也有了。
   安装法子，有两种，一种是直接在java官网去下载最新包，我的机器是64位的，所以对应含有amd64字样。
（关于amd64的说法，可以google一下，大约就是amd最先推出64位机，于是乎后来者比如因特尔在推出64位CPU时也沿用了此叫法。）

####法一：
   下载zip包对方法我就不详述了，google一大把，很多的。无非就是解压，然后指定环境到解压的目录即可。

####法二：

1、 apt-get方式安装，其实也简单，如此即可：

	#apt-get install openjdk-7-sdk
	
2、配置环境变量   
   先找到java环境在哪里

	#update-alternatives --display java

----结果如下-------

	#update-alternatives --display java
java - 自动模式
 链接目前指向 /usr/lib/jvm/java-7-openjdk-amd64/jre/bin/java
/usr/lib/jvm/java-7-openjdk-amd64/jre/bin/java - 优先级 1071
  slave java.1.gz：/usr/lib/jvm/java-7-openjdk-amd64/jre/man/man1/java.1.gz
目前“最佳”的版本为 /usr/lib/jvm/java-7-openjdk-amd64/jre/bin/java
看到了结果，即java环境是在这个路径（/usr/lib/jvm/java-7-openjdk-amd64）之下的，再查看此路径：


	#ls -l /usr/lib/jvm/ 
>总用量 8 
lrwxrwxrwx 1 root root   24  4月 27  2012 
default-java -> java-1.7.0-openjdk-amd64 
lrwxrwxrwx 1 root root   24  4月 27  2012 java-1.6.0-openjdk -> java-1.7.0-openjdk-amd64 
lrwxrwxrwx 1 root root   20  2月 21 14:22 java-1.7.0-openjdk-amd64 -> java-7-openjdk-amd64 
lrwxrwxrwx 1 root root   24  4月 27  2012 java-6-openjdk -> java-1.7.0-openjdk-amd64 
drwxr-xr-x 7 root root 4096  2月 27 13:27 java-7-openjdk-amd64 drwxr-xr-x 3 root root 4096  2月 26 16:16 java-7-openjdk-common
默认default-java最终是指向的 java-7-openjdk-amd64，那么就所我们要的JAVA_HOME路径了。 
设置环境变量

	#vi .bashrc
在末尾添加如下几行

	###-----java & android path------###
	export JAVA_HOME=/usr/lib/jvm/default-java
	export JRE_HOME=$JAVA_HOME/jre
	export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH
	export ANDROID_SDK_HOME=/home/nil/software/android-sdk-linux
	export ANDROID_SDK_TOOLS=/home/nil/software/android-sdk-linux/tools
	export PATH=$ANDROID_SDK_HOME:$ANDROID_SDK_TOOLS:$JAVA_HOME/bin:$PATH
	
至此，android开发环境就好了 