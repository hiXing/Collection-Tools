[http://blog.csdn.net/carrey1989/article/details/8060155#](http://blog.csdn.net/carrey1989/article/details/8060155#)

##  在api level 9之后，android系统为我们提供了DownLoadManager类
通过
    ``` 
    context.getSystemService(Context.DOWNLOAD_SERVICE);
    ```
    获取 *DownLoadManager* 实例
## 初始化

 ``` 
 DownloadManager.Request request = new DownloadManager.Request(Uri.parse("下载地址")); 
 ```
 
 request设置一些属性：

* addRequestHeader(String header,String value):添加网络下载请求的http头信息
* allowScanningByMediaScanner():用于设置是否允许本MediaScanner扫描。
* setAllowedNetworkTypes(int flags):设置用于下载时的网络类型，默认任何网络都可以下载，提供的网络常量有：NETWORK_BLUETOOTH、NETWORK_MOBILE、NETWORK_WIFI。
* setAllowedOverRoaming(Boolean allowed):用于设置漫游状态下是否可以下载
* setTitle(CharSequence):设置Notification的title信息
* setDescription(CharSequence):设置Notification的message信息
* setDestinationInExternalFilesDir、setDestinationInExternalPublicDir、 setDestinationUri等方法用于设置下载文件的存放路径，注意如果将下载文件存放在默认路径，那么在空间不足的情况下系统会将文件删除，所 以使用上述方法设置文件存放目录是十分必要的。
* setNotificationVisibility(int visibility):用于设置下载时时候在状态栏显示通知信息
   * setNotificationVisibility方法可以用来控制什么时候显示Notification，甚至是隐藏该request的Notification。有以下几个参数：
    * Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
    * Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification。
    * Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
  
 创建Request对象的代码如下：
 
    ```
    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
        request.setTitle("下载");
        request.setDescription("今日头条正在下载");
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "mydown");
        //request.setDestinationUri(Uri.fromFile(f));//在外部存储中指定一个任意的保存位置(f是一个File对象) 
        //如果下载的文件希望被其他的应用共享,可以指定你的下载路径在外部存储的公共文件夹之下
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,"Android_Rock.mp3");
        //在默认的情况下，通过Download Manager下载的文件是不能被Media Scanner扫描到的.
        //让下载的音乐文件可以被其他应用扫描到，我们需要调用Request对象的allowScaningByMediaScanner方法.
        //希望下载的文件可以被系统的Downloads应用扫描到并管理，我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true。
    ```
  
  如果想取消、终止、或删除下载，则可以调用remove方法完成，此方法可以将下载任务和已经下载的文件同时删除：
  
    ```  downManager.remove(id); ```
  
  在文件下载完成时，我们经常需要做一下后操作，比如apk，怎需要直接显示安装，那么我们如何监听文件时候已经下载完成了呢？
  DownLoadManager在文件现在完成时会发送一个action为ACTION_DOWNLOAD_COMPLETE的广播，我们只要注册一 个广播接收器即可进行处理(在intent中包含了已经完成的这个下载的ID)：
  
    ```
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
        
        private class DownLoadCompleteReceiver extends BroadcastReceiver{
                @Override
                public void onReceive(Context context, Intent intent) {
                    if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        Toast.makeText(MainActivity.this, "编号："+id+"的下载任务已经完成！", Toast.LENGTH_SHORT).show();
                    }else if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)){
                        Toast.makeText(MainActivity.this, "别瞎点！！！", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    ```
  DownManager会对所有的现在任务进行保存管理，那么我们如何获取这些信息呢？这个时候就要用到DownManager.Query对象，通过此对象，我们可以查询所有下载任务信息。
  
  * setFilterById(long… ids)：根据任务编号查询下载任务信息
  
  * setFilterByStatus(int flags)：根据下载状态查询下载任务
  
  具体使用方法如下：
  
    ```
        private void queryDownTask(DownloadManager downManager,int status) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterByStatus(status);
            Cursor cursor= downManager.query(query);
            while(cursor.moveToNext()){
                String downId= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                //String statuss = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                String size= cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                String sizeTotal = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                Map<String, String> map = new HashMap<String, String>();
                map.put("downid", downId);
                map.put("title", title);
                map.put("address", address);
                map.put("status", sizeTotal+":"+size);
                this.data.add(map);
            }
            cursor.close();
        }
    ```
  下面的代码段是通过注册监听下载完成事件的广播接受者来查询下载完成文件的本地文件名和URI的实现方法：
  
    ```
    @Override  
    public void onReceive(Context context, Intent intent) {  
        long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);  
        if (myDownloadReference == reference) {  
            Query myDownloadQuery = new Query();  
            myDownloadQuery.setFilterById(reference);  
            Cursor myDownload = downloadManager.query(myDownloadQuery);  
            if (myDownload.moveToFirst()) {  
                int fileNameIdx =   
                myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);  
                int fileUriIdx =   
                myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);  
                String fileName = myDownload.getString(fileNameIdx);  
                String fileUri = myDownload.getString(fileUriIdx);  
                // TODO Do something with the file.  
                Log.d(TAG, fileName + " : " + fileUri);  
            }  
            myDownload.close();  
        }  
     }  
    ```