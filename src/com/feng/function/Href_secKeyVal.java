package com.feng.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.feng.util.RegexUtil;

public class Href_secKeyVal {

	private static boolean PRINTCONSOLE = false;
	private static int fileChangeCount = 0;
	private static int addSecKeyVal = 0;
	private static int appendHref = 0;
	private static int appendHrefVariable = 0;
	private static int appendJavascript = 0;
	private static final String PATTERN_START = "\"<a href=\\\"\"";
	private static final String PATTERN_END = "\"\\\">\"";
	private static final String LINE_secKeyVal = "String secKeyVal = (String) super.getPageContext().getSession().getAttribute(SessionConstants.OSCWA_KEY);";
	private static final String LINE_beforeEndTag = "\"&amp;\"+ SessionConstants.OSCWA_KEY+\"=\"+secKeyVal+";
	private static final List<String> exceptionList = new LinkedList<>();
	
	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\Href_secKeyVal"};
//	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Benefits", 
//											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\BenefitsWY",
//											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Framework",
//											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Tax",
//											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\TaxWY",
//											"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\UIWorkflowListener"};

	public static void main(String[] args) {



		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				excute(iterator.next().getAbsolutePath());
			}
		}
		System.out.println("-------------Done------------");
		System.out.println("total add SecKeyVal        : "+addSecKeyVal);
		System.out.println("total append Href          : "+appendHref);
		System.out.println("total append HrefVariable  : "+appendHrefVariable);
		System.out.println("total append Javascript    : "+appendJavascript);
		System.out.println("total files changed : "+fileChangeCount);
		System.out.println("-------------Please check the following exceptions-------------");
		exceptionList.forEach(System.out::println);
	}

	private static void excute(String filePath){
		if (!filePath.endsWith("java")) return;
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);

		if (PRINTCONSOLE){
			System.out.println("-------Scan File-----------" + fileName + "------------------");
		}

		boolean needOverWrite = false;
		boolean exception = false;

		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bf = new BufferedReader(fileReader)){
			String input = bf.lines().collect(Collectors.joining("\n"));
			StringBuffer sb = new StringBuffer();

			String regex; Pattern pattern; Matcher matcher;
			
			System.out.println("****************");
			RegexUtil.getAssignmentSentenceByVariable("link", input, true).forEach(System.out::println);

//			System.out.println(input.indexOf(PATTERN_START));
//			System.out.println(input.lastIndexOf(PATTERN_START));
//			System.out.println(input.indexOf(PATTERN_END));
//			System.out.println(input.lastIndexOf(PATTERN_END));

			/* pattern start with "<a href=\"", end with " \ "> */
			{
				if (input.contains(PATTERN_START)){
					exception = true;
				}
				regex = "\"\\<a\\s*href\\s*\\=\\s*\\\\\"\"[\\S\\s]*?(\"\\s*\\\\\"\\s*\\>)";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(input);
				while (matcher.find()){
					exception = false;
					appendHref++;
					System.out.println(fileName + " : appendHref");
					needOverWrite = true;
					if (PRINTCONSOLE){
						System.out.println("------------------Group------------------");
						System.out.println(matcher.group());
						System.out.println("------------------Group 1------------------");
						System.out.println(matcher.group(1));
						System.out.println("------------------Change------------------");
						System.out.println(matcher.group().replace(matcher.group(1), LINE_beforeEndTag + matcher.group(1)));
					}
					matcher.appendReplacement(sb, "");
					sb.append(matcher.group().replace(matcher.group(1), LINE_beforeEndTag + matcher.group(1)));
				}
				matcher.appendTail(sb);
				input = sb.toString();
				sb.setLength(0);
				
				//check if "<a href=\"" exists, but no ending "\ ">
				if (exception){
					exceptionList.add(fileName);
				}
				/*
				exception = false;
				if (input.contains("<a href=JavaScript:openPopupWindow")){
					exception = true;
				}
				//start with "<a href=JavaScript:openPopupWindow, end with "');
				regex = "\"\\<a\\s*href\\s*\\=\\s*JavaScript:openPopupWindow[\\S\\s]*?(\"\\s*'\\s*\\)\\s*;)";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(input);
				while (matcher.find()){
					exception = false;
					appendJavascript++;
					System.out.println(fileName + " : appendJavascript");
					needOverWrite = true;
					if (PRINTCONSOLE){
						System.out.println("------------------Group------------------");
						System.out.println(matcher.group());
						System.out.println("------------------Group 1------------------");
						System.out.println(matcher.group(1));
						System.out.println("------------------Change------------------");
						System.out.println(matcher.group().replace(matcher.group(1), LINE_beforeEndTag + matcher.group(1)));
					}
					matcher.appendReplacement(sb, "");
					sb.append(matcher.group().replace(matcher.group(1), LINE_beforeEndTag + matcher.group(1)));
				}
				matcher.appendTail(sb);
				input = sb.toString();
				sb.setLength(0);
				if (exception){
					exceptionList.add(fileName);
				}
				*/
				
				if (!input.contains("String secKeyVal") && needOverWrite){
					exception = true;
					regex = "([^\\S\\n]*)String\\s*encKey\\s*\\=[\\S\\s]*?;";
					pattern = Pattern.compile(regex);
					matcher = pattern.matcher(input);
					if (matcher.find()){
						exception = false;
						addSecKeyVal++;
						System.out.println(fileName + " : addSecKeyVal");
						if (PRINTCONSOLE){
							System.out.println("------------------Group------------------");
							System.out.println(matcher.group());
						}
						matcher.appendReplacement(sb, "");
						sb.append(matcher.group()).append("\n").append(matcher.group(1)).append(LINE_secKeyVal);
					}
					matcher.appendTail(sb);
					input = sb.toString();
					sb.setLength(0);
				}
				if (exception){
					exceptionList.add(fileName);
				}
			}
			
			/* pattern start with "<a href=\"#\", end with "\">", add to variable link*/
			{
				List<String> list = new LinkedList<>();
				regex = "\"\\<a href\\=\\\\\"#\\\\\"[\\S\\s]*?\\+\\s*(\\w+)\\W";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(input);
				while (matcher.find()){
					exception = true;
					needOverWrite = true;
					if (PRINTCONSOLE){
						System.out.println("------------------\"<a href=\\\\\"#\\\\\"------------------");
						System.out.println(matcher.group());
						System.out.println("------------------Group 1------------------");
						System.out.println(matcher.group(1));
					}
					list.add(matcher.group(1));
				}
				//append variable captured above
				for (String s : list){
					String text = "+\"&amp;\"+ SessionConstants.OSCWA_KEY+\"=\"+secKeyVal";
					regex = "(\\W" + s + "\\s*\\=\\s*([\\S\\s]*?)\\s*)(;\\s)";
					pattern = Pattern.compile(regex);
					matcher = pattern.matcher(input);
					while (matcher.find()){
						if (matcher.group(2).equals("null")){
							continue;
						}
						exception = false;
						appendHrefVariable++;
						System.out.println(fileName + " : appendHrefVariable");
						if (PRINTCONSOLE){
							System.out.println("------------------variable captured------------------");
							System.out.println(matcher.group());
							System.out.println("------------------1------------------");
							System.out.println(matcher.group(1));
							System.out.println("------------------2------------------");
							System.out.println(matcher.group(2));
							System.out.println("------------------variable block Change------------------");
							System.out.println(matcher.group(1)+text+matcher.group(3));
						}
						matcher.appendReplacement(sb, "");
						sb.append(matcher.group(1)).append(text).append(matcher.group(3));
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
				if (needOverWrite == true){
//					try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
//						fileOutputStream.write(input.getBytes());
//						fileOutputStream.close();
//					}
					fileChangeCount++;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
