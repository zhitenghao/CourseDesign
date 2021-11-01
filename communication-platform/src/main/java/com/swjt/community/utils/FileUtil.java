package com.swjt.community.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

//import cn.graceearth.platform.system.exception.SysRuntimeException;


public class FileUtil {

	public static void writeFile(String filename, String content) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			f.createNewFile();
		}

		byte bytes[] = new byte[1024];
		bytes = content.getBytes();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.close();
	}

	public static void writeFile(String filename, byte[] bytes) throws IOException {

		File f = new File(filename);

		File fileParent = f.getParentFile();
		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}

		f.createNewFile();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.close();
	}

	/**
	 * 
	 * @Title: uploadFile
	 * @Description: 保存上传文件
	 * @param file     文件字节数组
	 * @param filePath 文件保存路径
	 * @param fileName 文件名称
	 * @throws Exception
	 * @return String 保存文件的全路径
	 */
	public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		String saveFileName = combine(filePath, fileName);
		FileOutputStream out = new FileOutputStream(saveFileName);
		out.write(file);
		out.flush();
		out.close();

		return saveFileName;
	}

	public static String getPath(String srcFileName) {
		if (srcFileName.endsWith("/") || srcFileName.endsWith("\\")) {
			return srcFileName;
		}

		int idx1 = srcFileName.lastIndexOf('/');
		int idx2 = srcFileName.lastIndexOf('\\');
		int idx = Math.max(idx1, idx2);

		return srcFileName.substring(0, idx + 1);
	}

	public static String getFileName(String srcFileName) {
		int idx1 = srcFileName.lastIndexOf('/');
		int idx2 = srcFileName.lastIndexOf('\\');
		int idx = Math.max(idx1, idx2);

		return srcFileName.substring(idx + 1);
	}

	public static String getFileNameWithoutExtension(String srcFileName) {
		String fileNameWithExtension = getFileName(srcFileName);
		int idx = fileNameWithExtension.lastIndexOf('.');

		return fileNameWithExtension.substring(0, idx);
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}

	public static String getExtensionWithDot(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'));
	}

	public static String makeDirs(String strPath) {
		File file = new File(strPath);
		file.mkdirs();

		return strPath;
	}

	/**
	 * 
	 * @Title: combine
	 * @Description: 合并文件路径
	 * @param strRoot 根路径
	 * @param strSubs 子路径
	 * @return String 合并后的路径
	 */
	public static String combine(String strRoot, String... strSubs) {
		StringBuilder builder = new StringBuilder(strRoot);

		String strTemp = strRoot;
		for (String strAfter : strSubs) {
			if (needSeparator(strTemp, strAfter)) {
				builder.append(File.separator + strAfter);
			} else {
				builder.append(strAfter);
			}

			strTemp = strAfter;
		}

		return builder.toString();
	}

	private static boolean needSeparator(String strBefore, String strAfter) {
		return !(strBefore.endsWith("/") || strBefore.endsWith("\\") || strAfter.startsWith("/")
				|| strAfter.startsWith("\\"));
	}

	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName 要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				// System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				// System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			// System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir 要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			// System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtil.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtil.deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			// System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			// System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	public static void traverseAllFilesInFolder(String folderPath, List<String> fileList) {

		File file = new File(folderPath);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {

						traverseAllFilesInFolder(file2.getAbsolutePath(), fileList);
					} else {
						fileList.add(file2.getAbsolutePath());

					}
				}
			}
		}
	}
	public static boolean isPhotoJudgement(String fileName)
	{
		String imageArray[] = { "bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico" };
		for (String str : imageArray) {
			if (str.equalsIgnoreCase(FileUtil.getExtension(fileName)))
				return true;
		}
		return false;
	}
	public static boolean isVideoJudgement(String fileName){
		String imageArray[] = { "mp4", "mov", "wmv", "avi", "mpg", "mpeg", "rm", "ram", "swf", "flv"};
		for (String str : imageArray) {
			if (str.equalsIgnoreCase(FileUtil.getExtension(fileName)))
				return true;
		}
		return false;
	}
	
//	public static Workbook isExcel(MultipartFile multipartFile)
//	{
//		String orginName=multipartFile.getOriginalFilename();
//		Workbook orkbook = null;
//		try {
//			if(orginName.endsWith(".xls"))
//				orkbook = new HSSFWorkbook(multipartFile.getInputStream());
//			else if(orginName.endsWith(".xlsx")) {
//			    orkbook = new XSSFWorkbook(multipartFile.getInputStream());
//			}
//			return orkbook;
//		}catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				throw new SysRuntimeException("Err_excel_01", HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//	}
	
	private static String UNKNOWN_SUFFIX = ".unknown";
	
	public static String getSuffixFromFileName(String fileName) {
		int indexOfDot = fileName.lastIndexOf(".");
		return indexOfDot != -1 ? fileName.substring(indexOfDot) : UNKNOWN_SUFFIX;
	}
	

	public static void main(String args[]) {
		String a="2131321.mp4";
		System.out.println(getExtension(a));
//		System.out.println(getExtension("ddd.java"));
//		System.out.println(getExtensionWithDot("ddd.java"));
//		System.out.println(combine("E:", "ddd", "main.java"));
//		System.out.println(combine("E:\\", "ddd", "main.java"));
//		System.out.println(combine("E:", "\\ddd", "main.java"));
//		System.out.println(combine("E:", "\\ddd", "\\main.java"));
//		System.out.println(combine("E:", "ddd\\", "\\main.java"));
//		System.out.println(combine("E:", "ddd/", "main.java"));
//		
//		System.out.println("makeDirs: " + makeDirs(combine("E:\\", "ddd/", "main.java")));

//		System.out.println(getPath("E:/dd\\bb/c.c"));
//		List<String> fileList = new ArrayList<String>();
//
////		FileUtil.traverseAllFolder("D:\\Orieange\\datastore\\40288af072bba8980172bbad40380000", fileList);
//		
//		for(String str : fileList) {			
//			System.out.println(str);
//		}

	}
}
