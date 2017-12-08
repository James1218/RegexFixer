package com.feng.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.feng.util.RegexUtil;


public class WindowOpen {

	private static boolean isOverWriteWorkSpace = false;
	private static boolean PRINTCONSOLE = false;
	private static int fileChangeCount = 0;
	private static int windowOpenCount = 0;
	private static int buttonCount = 0;
	private static final String CODEBEFOREWINDOWOPEN = "document.forms[0].method[0].click();";
	private static final String CODEBEFOREBUTTON_FIRST = "<access:nonvalidatesubmit property=\"method\" styleClass=\"formbutton\"";
	private static final String CODEBEFOREBUTTON_SECOND = "style=\"visibility:hidden\"";
	private static final String CODEBEFOREBUTTON_THIRD = "onmouseover=\"this.className='formbutton formbuttonhover'\"";
	private static final String CODEBEFOREBUTTON_FOURTH = "onmouseout=\"this.className='formbutton'\">";
	private static final String CODEBEFOREBUTTON_FIFTH = "<bean:message key=\"access.popup\" />";
	private static final String CODEBEFOREBUTTON_SIXTH = "</access:nonvalidatesubmit>";
	private static final List<String> exceptionList = new LinkedList<>();
	
//	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\WindowOpen"};
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
				excute(iterator.next().getAbsolutePath());
			}
		}
		System.out.println("-------------Done------------");
		System.out.println("total window.open('${ added : "+windowOpenCount);
		System.out.println("total button code added : "+buttonCount);
		System.out.println("total files changed : "+fileChangeCount);
		System.out.println("-------------Please check the following exceptions : have window.open('${, but not any button-------------");
		exceptionList.forEach(System.out::println);
	}

	private static void excute(String filePath){
		if (!filePath.endsWith("jsp")) return;
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		if (PRINTCONSOLE){
			System.out.println("-------Scan File-----------" + fileName + "------------------");
		}

		boolean isOverWrite = false;
		boolean isSearchButton = false;
		boolean exception = false;
		int localWindowOpenCount = 0;
		String addButtonStr = null;

		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bf = new BufferedReader(fileReader)){
			String input = bf.lines().collect(Collectors.joining("\n"));
			StringBuffer sb = new StringBuffer();
			String regex; Pattern pattern; Matcher matcher;
			
			/*
			 * find window.open('${, insert code right before it.
			 * note : if the code already exists, don't add it.
			 */
			{
				regex = "(?<!document\\.forms\\[0\\]\\.method\\[0\\]\\.click\\(\\);)window\\s*\\.\\s*open\\s*\\(\\s*'\\s*\\$\\s*\\{";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(input);
				while (matcher.find()){
					localWindowOpenCount++;
					windowOpenCount++;
					isOverWrite = true;
					isSearchButton = true;
					String group = matcher.group();
					if (PRINTCONSOLE){
						System.out.println("-----------wondow.open group------------");
						System.out.println(group);
					}
					matcher.appendReplacement(sb, "");
					sb.append(CODEBEFOREWINDOWOPEN).append(group);
				}
				matcher.appendTail(sb);
				input = sb.toString();
				sb.setLength(0);
			}
			
			/*
			 * if found window.open('${, search 4 kinds of buttons, add code before first button
			 * button 1 :	access:nonvalidatesubmit
			 * button 2 :	html:cancel
			 * button 3 :	html:submit
			 * button 4 :	access:localebuttontag
			 * button 5 :	html:button
			 * Note : if the code already exists, don't add. Use access.popup as unique identifier
			 * 
			 * group 1 : indentation
			 */
			
			{
				if (isSearchButton && !input.contains("access.popup")){
					regex = "([^\\S\\n]*)(.*?)(\\<access\\s*:\\s*nonvalidatesubmit|\\<html\\s*:\\s*cancel|\\<html\\s*:\\s*submit|\\<access\\s*:\\s*localebuttontag|\\<html\\s*:\\s*button)";
					pattern = Pattern.compile(regex);
					matcher = pattern.matcher(input);
					if (matcher.find()){
						String group = matcher.group();
						String group1 = matcher.group(1);//indentation
						String group2 = matcher.group(2);
						String group3 = matcher.group(3);
						if (PRINTCONSOLE){
							System.out.println("------button group--------");
							System.out.println(group);
							System.out.println("------button group1--------");
							System.out.println(group1);
							System.out.println("------button group2--------");
							System.out.println(group2);
							System.out.println("------button group3--------");
							System.out.println(group3);
						}
						buttonCount++;
						addButtonStr = group3;
						matcher.appendReplacement(sb, "");
						sb.append(group1).append(group2).append("\r\n")
						.append(group1).append("\t").append(CODEBEFOREBUTTON_FIRST).append("\r\n")
						.append(group1).append("\t\t").append(CODEBEFOREBUTTON_SECOND).append("\r\n")
						.append(group1).append("\t\t").append(CODEBEFOREBUTTON_THIRD).append("\r\n")
						.append(group1).append("\t\t").append(CODEBEFOREBUTTON_FOURTH).append("\r\n")
						.append(group1).append("\t\t").append(CODEBEFOREBUTTON_FIFTH).append("\r\n")
						.append(group1).append("\t").append(CODEBEFOREBUTTON_SIXTH).append("\r\n")
						.append(group1).append("\t").append(group3);
					}
					else{
						exception = true;
					}
					matcher.appendTail(sb);
					input = sb.toString();
					sb.setLength(0);
				}
			}
			
			if (exception){
				exceptionList.add(fileName);
			}
			/*if file has changes, overwrite the file*/
			{
				if (isOverWrite){
					if (isOverWriteWorkSpace){
						try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
							fileOutputStream.write(input.getBytes());
							fileOutputStream.close();
						}
					}
					fileChangeCount++;
					System.out.println("add to window.open('${ :  " + localWindowOpenCount + "x   " + fileName);
					if (addButtonStr != null){
						System.out.println("add "+addButtonStr+ " :       " + fileName);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<File> listAllFiles (String directoryPath){
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
}
