ListView+CheckBox两种解决方案及原因分析
---

最近在用ListView+CheckBox搞一个item选中的项目，我将CheckBox的focus设置为false，另我大喜的是，CheckBox竟然可以选中（窃喜中），这么简单就搞定了，因为数据量较小，也没有发现什么问题。

后来数据多了， 页面需要滑动了， 发现了一个奇怪的问题，前面明明选中了，而再次滑动回去的时候竟然变成未选中状态！

这是我刚开始写的那段错误的代码：

```
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	if (convertView == null) {
		convertView = View.inflate(App.sContext, R.layout.our_sp_item, null);
		holder = new ViewHolder();
		holder.checked = (CheckBox) convertView.findViewById(R.id.cb_our_sp_checked);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
		
	if(isBatchMode) {
		holder.checked.setVisibility(View.VISIBLE);
		holder.checked.setChecked(mTasks.get(position).isChecked());
	}else {
		holder.checked.setVisibility(View.GONE);
	}
		
	final int pos = position;
	holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		mTasks.get(pos).setChecked(isChecked);
			}
	});

	return convertView;
}
```
>原因分析

关于错误的原因，我们先来分析一下， 哦，对了，在分析之前，先来看看我的上一篇博客：从一次意外开始说java匿名内部类， 相信对你理解很有帮助。

下面我们开始分析出现上述状况的原因， 怎么分析呢？ 跟着操作和代码走。

当我们滑动到第一条数据不再显示开始显示下面的数据的时候，因为复用了convertView， 也就是当前item的和第一条item共用了第一条convertView，这时看代码15行，setChecked修改了CheckBox的状态，而此时此刻肯定会去回调OnCheckedChangeListener，但是我们并没有给checkBox设置新的OnCheckedChangeListener， 也就是说，此时还是执行的以前的回调，但是我们在匿名类中使用了外部方法的变量，鉴于在博客：从一次意外开始说java匿名内部类 所说的，此时的pos参数还是第一条item的pos，我们第一条数据的check状态就这么被操蛋的修改了。



>解决方案

我们提供两种解决方案。

*	第一种，我们不使用setOnCheckedChangeListener的方式去修改保存的状态，而是换用Click

```
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	if (convertView == null) {
		convertView = View.inflate(App.sContext, R.layout.our_sp_item, null);
		holder = new ViewHolder();
		holder.checked = (CheckBox) convertView.findViewById(R.id.cb_our_sp_checked);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
		
	if(isBatchMode) {
		holder.checked.setVisibility(View.VISIBLE);
		holder.checked.setChecked(mTasks.get(position).isChecked());
	}else {
		holder.checked.setVisibility(View.GONE);
	}
		
	final int pos = position;
	holder.checked.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			mTasks.get(pos).changeChecked();
		}
	});

	return convertView;
}
```
这种方式为什么能解决呢？ 很简单，setChecked后，我们并没有提供Listener去修改值，代码也就没有了偷偷去修改的机会了。


*	第二种，上面分析原因也说了，主要的原因还是在setChecked之前我们并没有设置新的Listener，那好办，我们把setOnCheckedChangeListener放到setChecked之前不就解决了嘛。

```
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	final ViewHolder holder;
	if (convertView == null) {
		convertView = View.inflate(App.sContext, R.layout.our_sp_item, null);
		holder = new ViewHolder();
		holder.checked = (CheckBox) convertView.findViewById(R.id.cb_our_sp_checked);
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}
		
	final int pos = position;
	holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			mTasks.get(pos).setChecked(isChecked);
		}
	});
		
	if(isBatchMode) {
		holder.checked.setVisibility(View.VISIBLE);
		holder.checked.setChecked(mTasks.get(position).isChecked());
	}else {
		holder.checked.setVisibility(View.GONE);
	}

	return convertView;
}
```
ok，这两种方式都可以解决这个问题，各位客官喜欢哪个自己去挑，原因呢，我们也解析了，如果你对从一次意外开始说java匿名内部类 能充分的理解，那肯定是恍然大悟的，如果你还是不太明白，建议你再去看看前面一篇博客