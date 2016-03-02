AndroidStudio 运行项目报错
----------

----------

*	Gradle 'mProject' project refresh failed: Build script error, unsupported Gradle DSL method found: 'exclude()'

*	Error:(28, 0) Gradle DSL method not found: 'exclude()'
Possible causes:<ul><li>The project 'BaoChunApp' may be using a version of Gradle that does not contain the method.
<a href="open.wrapper.file">Open Gradle wrapper file</a></li><li>The build file may be missing a Gradle plugin.
<a href="apply.gradle.plugin">Apply Gradle plugin</a></li>

----------

```
dependencies {
	    compile 'com.google.code.gson:gson:2.2.4'
	    compile 'de.keyboardsurfer.android.widget:crouton:1.8.3'
	    compile 'de.greenrobot:eventbus:2.2.0'
		  compile 'com.intellij:annotations:+@jar'
	    compile 'com.jpardogo.googleprogressbar:library:1.0.0'
	    compile project(':floatlabel')
	    compile project(':Android-SwipeToDismiss')
	    compile project(':Android-UndoBar') {
        exclude group: 'com.nineoldandroids', module: 'library' // without or without this one
    }
	    compile project(':AndroidSlidingUpPanel:library') {
        exclude group: 'com.nineoldandroids', module: 'library' // without or without this one
    }
}
```
----------

----------
