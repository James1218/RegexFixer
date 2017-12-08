package com.feng.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * Change FormFile to FileItem to the followings:
 * 1. instance variable
 * 2. getter
 * 3. setter
 * 4. add new setter
 * Need to import FileItem
 * 
 */
public class ChangeFormFileToFileItem {
	
	private static boolean isOverWriteWorkSpace = false;
	private static boolean isWriteToLocalTestFolder = true;
	private static boolean isPrintConsole = true;
	private static int fileChangeCount = 0;
	private static String importList = "import java.util.List;";
	private static String importFileItem = "import org.apache.commons.fileupload.FileItem;";
	
	private static String setFileName = "\tpublic void setterName(Object setterParam){\n"
			+ "\t\tif(setterParam!=null){\n"
			+ "\t\t\tList<FileItem>fileItems=(List<FileItem>)setterParam;\n"
			+ "\t\t\tif(fileItems!=null && !fileItems.isEmpty()){\n"
			+ "\t\t\t\tthis.fileName=fileItems.get(0);\n"
			+ "\t\t\t}\n"
			+ "\t\t}\n"
			+ "\t}\n";
	
	private static String localFolder = "C:\\Users\\feng\\Desktop\\ChangeFormFileToFileItem";
//	private static String localFolder = "C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\ChangeFormFileToFileItem";

//	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\ChangeFormFileToFileItem"};
	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Benefits", 
											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\BenefitsWY",
											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Framework",
											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Tax",
											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\TaxWY",
											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\UIWorkflowListener"};
	
	public static void main(String[] args) {
		
		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				execute(iterator.next().getAbsolutePath());
			}
		}
		System.out.println("-------------Done------------");
		System.out.println("total files changed : "+fileChangeCount);
	}
	
	private static void execute(String absolutePath) {
		if (!absolutePath.endsWith(".java")) return;
		String fileName = absolutePath.substring(absolutePath.lastIndexOf("\\")+1);
		boolean isFileChanged = false;
		
		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			String input = br.lines().collect(Collectors.joining("\n"));
			String inputOrigin = input;
			String regex; Pattern pattern; Matcher matcher;
			if (input.contains("FormFile ")){
				isFileChanged = true;
				input = input.replace("FormFile ", "FileItem ");
				input = input.replaceAll("(public\\s+void\\s+(set\\w+\\s*)\\(\\s*(?:final)?\\s*FileItem\\s+(\\w+)[\\S\\s]*?\\})", 
						"$1"+"\n"+setFileName.replace("setterName", "$2").replace("setterParam", "$3"));
				if (!input.contains(importList)){
					input = addImport(input, importList);
				}
				if (!input.contains(importFileItem)){
					input = addImport(input, importFileItem);
				}
			}
			
			if (isFileChanged){
				if (isOverWriteWorkSpace){
					try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath);){
						fileOutputStream.write(input.getBytes());
						fileOutputStream.close();
					}
				}
				else if (isWriteToLocalTestFolder){
					Path path = Paths.get(localFolder);
					Path path_origin = Paths.get(localFolder+"_Origin");
					if (!Files.exists(path)){
						Files.createDirectories(path);
						Files.createDirectories(path_origin);
					}
					try (FileOutputStream fileOutputStream = new FileOutputStream(localFolder+"\\"+fileName);){
						fileOutputStream.write(input.getBytes());
						fileOutputStream.close();
					}
					try (FileOutputStream fileOutputStream = new FileOutputStream(localFolder+"_Origin\\"+fileName);){
						fileOutputStream.write(inputOrigin.getBytes());
						fileOutputStream.close();
					}
				}
				System.out.println(absolutePath);
				fileChangeCount++;
			}
			
		} catch (Exception e) {
			
		}
	}

	private static List<File> listAllFiles (String directoryPath){
		List<File> list = new LinkedList<>();
		File directory = new File(directoryPath);
		File [] fileList = directory.listFiles();
		for (File file : fileList){
			if (file.isFile()){
				list.add(file);
			}
			else if (file.isDirectory()){
				list.addAll(listAllFiles(file.getAbsolutePath()));
			}
		}
		return list;
	}
	
	private static String addImport(String input, String importStatement){
		StringBuffer sb = new StringBuffer();
		String regex_package = "package.*?;";
		Pattern pattern = Pattern.compile(regex_package);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()){
			matcher.appendReplacement(sb, "");
			sb.append(matcher.group());
			sb.append("\n\n" + importStatement);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
