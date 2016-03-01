package com.zhx.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * 文件操作工具包
 */
public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory() + "/Baochun/";
	private static final int CACHE_TIME = 60 * 60000;//缓存失效时间
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();
	/**
	 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
	 * 
	 * @param context
	 */
	public static void write(Context context, String fileName, String content) {
		if (content == null)
			content = "";
		try {
			FileOutputStream fos = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			fos.write(content.getBytes());

			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文本文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String read(Context context, String fileName) {
		try {
			FileInputStream in = context.openFileInput(fileName);
			return readInStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 读取流文件
	 * 
	 * @param inStream
	 * @return
	 */
	private static String readInStream(FileInputStream inStream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, length);
			}

			outStream.close();
			inStream.close();
			return outStream.toString();
		} catch (IOException e) {
			Log.i("FileTest", e.getMessage());
		}
		return null;
	}


	/**
	 * 
	 * @date 2015-9-11
	 * @param folderPath
	 * @param fileName
	 * @return
	 * @TODO 创建文件
	 */
	public static File createFile(String folderPath, String fileName) {
		File destDir = new File(folderPath);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return new File(folderPath, fileName + fileName);
	}

	/**
	 * 向手机写图片
	 * 
	 * @param buffer
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static boolean writeFile(byte[] buffer, String folder,
			String fileName) {
		boolean writeSucc = false;

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

		String folderPath = "";
		if (sdCardExist) {
			folderPath = Environment.getExternalStorageDirectory()
					+ File.separator + folder + File.separator;
		} else {
			writeSucc = false;
		}

		File fileDir = new File(folderPath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		File file = new File(folderPath + fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(buffer);
			writeSucc = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return writeSucc;
	}

	/**
	 * 根据文件绝对路径获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 根据文件的绝对路径获取文件名但不包含扩展名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameNoFormat(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return "";
		}
		int point = filePath.lastIndexOf('.');
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
				point);
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filePath
	 * @return
	 */
	public static long getFileSize(String filePath) {
		long size = 0;

		File file = new File(filePath);
		if (file != null && file.exists()) {
			size = file.length();
		}
		return size;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param size
	 *            字节
	 * @return
	 */
	public static String getFileSize(long size) {
		if (size <= 0)
			return "0";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
		float temp = (float) size / 1024;
		if (temp >= 1024) {
			return df.format(temp / 1024) + "M";
		} else {
			return df.format(temp) + "K";
		}
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取目录文件大小
	 * 
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {
		if (dir == null) {
			return 0;
		}
		if (!dir.isDirectory()) {
			return 0;
		}
		long dirSize = 0;
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				dirSize += file.length();
			} else if (file.isDirectory()) {
				dirSize += file.length();
				dirSize += getDirSize(file); // 递归调用继续统计
			}
		}
		return dirSize;
	}

	/**
	 * 获取目录文件个数
	 * 
	 * @param f
	 * @return
	 */
	public long getFileList(File dir) {
		long count = 0;
		File[] files = dir.listFiles();
		count = files.length;
		for (File file : files) {
			if (file.isDirectory()) {
				count = count + getFileList(file);// 递归
				count--;
			}
		}
		return count;
	}

	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			out.write(ch);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkFileExists(String name) {
		boolean status;
		if (!name.equals("")) {
			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + name);
			status = newPath.exists();
		} else {
			status = false;
		}
		return status;

	}

	/**
	 * 计算SD卡的剩余空间
	 * 
	 * @return 返回-1，说明没有安装sd卡
	 */
	public static long getFreeDiskSpace() {
		String status = Environment.getExternalStorageState();
		long freeSpace = 0;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSizeLong();
				long availableBlocks = stat.getAvailableBlocksLong();
				freeSpace = availableBlocks * blockSize / 1024;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return (freeSpace);
	}

	/**
	 * 新建目录
	 * 
	 * @param directoryName
	 * @return
	 */
	public static boolean createDirectory(String directoryName) {
		boolean status;
		File path = Environment.getExternalStorageDirectory();
		if (!directoryName.equals("")) {
			File newPath = new File(path.toString() + directoryName);
			status = newPath.mkdir();
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 检查是否安装SD卡
	 * 
	 * @return
	 */
	public static boolean checkSaveLocationExists() {
		String sDCardStatus = Environment.getExternalStorageState();
		boolean status;
		if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
			status = true;
		} else
			status = false;
		return status;
	}

	/**
	 * 删除目录(包括：目录里的所有文件)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteDirectory(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isDirectory()) {
				String[] listfile = newPath.list();
				// delete all files within the specified directory and then
				// delete the directory
				try {
					for (int i = 0; i < listfile.length; i++) {
						File deletedFile = new File(newPath.toString() + "/"
								+ listfile[i].toString());
						deletedFile.delete();
					}
					newPath.delete();
					Log.i("DirectoryManager deleteDirectory", fileName);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
					status = false;
				}

			} else
				status = false;
		} else
			status = false;
		return status;
	}

	/**
	 * 清除缓存目录
	 * 
	 * @param dir
	 *            目录
	 * @param curTime
	 *            当前系统时间
	 * @return
	 */
	public static int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		boolean status;
		SecurityManager checker = new SecurityManager();

		if (!fileName.equals("")) {

			File path = Environment.getExternalStorageDirectory();
			File newPath = new File(path.toString() + fileName);
			checker.checkDelete(newPath.toString());
			if (newPath.isFile()) {
				try {
					Log.i("DirectoryManager deleteFile", fileName);
					newPath.delete();
					status = true;
				} catch (SecurityException se) {
					se.printStackTrace();
					status = false;
				}
			} else
				status = false;
		} else
			status = false;
		return status;
	}
	
	/**
	 * @date 2015-9-12
	 * @param paramBitmap
	 * @param paramString
	 * @TODO 保存图片
	 */
	  public static void saveBitmap(Bitmap paramBitmap, String paramString)
	  {
	    Log.e("", "保存图片");
	    try
	    {
	      if (!checkFileExists(SDPATH))
	    	  createDirectory(SDPATH);
	      File localFile = new File(SDPATH, paramString + ".JPEG");
	      if (localFile.exists())
	        localFile.delete();
	      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
	      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 90, localFileOutputStream);
	      localFileOutputStream.flush();
	      localFileOutputStream.close();
	      Log.e("", "已经保存");
	      return;
	    }
	    catch (FileNotFoundException localFileNotFoundException)
	    {
	      localFileNotFoundException.printStackTrace();
	      return;
	    }
	    catch (IOException localIOException)
	    {
	      localIOException.printStackTrace();
	    }
	  }
	  
		/**
		 * 判断缓存是否存在
		 * @param cachefile
		 * @return
		 */
		private static boolean isExistDataCache(Context context,String cachefile) {
			boolean exist = false;
			File data = context.getFileStreamPath(cachefile);
			if(data.exists())
				exist = true;
			return exist;
		}
		
		/**
		 * 判断缓存是否失效
		 * @param cachefile
		 * @return
		 */
		public boolean isCacheDataFailure(Context context,String cachefile) {
			boolean failure = false;
			File data = context.getFileStreamPath(cachefile);
			if(data.exists() && (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
				failure = true;
			else if(!data.exists())
				failure = true;
			return failure;
		}
		/**
		 * 将对象保存到内存缓存中
		 * @param key
		 * @param value
		 */
		public void setMemCache(String key, Object value) {
			memCacheRegion.put(key, value);
		}
		
		/**
		 * 从内存缓存中获取对象
		 * @param key
		 * @return
		 */
		public Object getMemCache(String key){
			return memCacheRegion.get(key);
		}
		
		/**
		 * 保存磁盘缓存
		 * @param key
		 * @param value
		 * @throws IOException
		 */
		public void setDiskCache(Context context,String key, String value) throws IOException {
			FileOutputStream fos = null;
			try{
				fos = context.openFileOutput("cache_"+key+".data", Context.MODE_PRIVATE);
				fos.write(value.getBytes());
				fos.flush();
			}finally{
				try {
					fos.close();
				} catch (Exception e) {}
			}
		}
		
		/**
		 * 获取磁盘缓存数据
		 * @param key
		 * @return
		 * @throws IOException
		 */
		public String getDiskCache(Context context,String key) throws IOException {
			FileInputStream fis = null;
			try{
				fis = context.openFileInput("cache_"+key+".data");
				byte[] datas = new byte[fis.available()];
				fis.read(datas);
				return new String(datas);
			}finally{
				try {
					fis.close();
				} catch (Exception e) {}
			}
		}
		
		/**
		 * 保存对象
		 * @param ser
		 * @param file
		 * @throws IOException
		 */
		public boolean saveObject(Context context,Serializable ser, String file) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try{
				fos = context.openFileOutput(file, Context.MODE_PRIVATE);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(ser);
				oos.flush();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}finally{
				try {
					oos.close();
				} catch (Exception e) {}
				try {
					fos.close();
				} catch (Exception e) {}
			}
		}
		
		/**
		 * 读取对象
		 * @param file
		 * @return
		 * @throws IOException
		 */
		public static Serializable readObject(Context context,String file){
			if(!isExistDataCache(context,file))
				return null;
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try{
				fis = context.openFileInput(file);
				ois = new ObjectInputStream(fis);
				return (Serializable)ois.readObject();
			}catch(FileNotFoundException e){
			}catch(Exception e){
				e.printStackTrace();
				//反序列化失败 - 删除缓存文件
				if(e instanceof InvalidClassException){
					File data = context.getFileStreamPath(file);
					data.delete();
				}
			}finally{
				try {
					ois.close();
				} catch (Exception e) {}
				try {
					fis.close();
				} catch (Exception e) {}
			}
			return null;
		}


}