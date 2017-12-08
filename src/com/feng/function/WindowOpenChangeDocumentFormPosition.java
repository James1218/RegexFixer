package com.feng.function;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class WindowOpenChangeDocumentFormPosition {

	private static boolean isOverWriteWorkSpace = false;
	private static boolean isWriteToLocalTestFolder = true;
	private static boolean isTest = true;
	private static boolean isPrintConsole = false;
	private static int fileChangeCount = 0;
	private static String localFolder = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\WindowOpenChangeDocumentFormPosition";

	public static void main(String[] args) {

		String [] directoryListTest = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\WindowOpenChangeDocumentFormPosition"};
		String [] directoryListWorkSpace = {"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Benefits", 
				"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\BenefitsWY",
				"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Framework",
				"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Tax",
				"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\TaxWY",
		"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\UIWorkflowListener"};
		String [] directoryList;
		if (isTest){
			directoryList = directoryListTest;
		}else{
			directoryList = directoryListWorkSpace;
		}

		for (String uri : directoryList){
			try {
				Files.walk(Paths.get(uri)).forEach(path->execute(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("-------------Done------------");
		System.out.println("total files changed : "+fileChangeCount);
	}

	private static void execute(Path path) {
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:.*java");
		if (pathMatcher.matches(path)){
			executeJava(path);
		}
		pathMatcher = FileSystems.getDefault().getPathMatcher("regex:.*jsp");
		if (pathMatcher.matches(path)){
			executeJsp(path);
		}
	}

	private static void executeJava(Path path) {
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:.*java");
		if (!pathMatcher.matches(path)) return;
		boolean isFileChanged = false;

		try {
			String input = Files.lines(path).collect(Collectors.joining("\n"));
			StringBuffer sb = new StringBuffer();
			String inputOrigin = input;
			String regex; Pattern pattern; Matcher matcher;

			regex = "(document\\.forms\\[0\\].method\\[0\\]\\.click\\(\\);)(window\\.open[\\S\\s]*?)(\\\\\\s*\"\\s*\\>)";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			while (matcher.find()){
				isFileChanged = true;
				String group1 = matcher.group(1);
				String group2 = matcher.group(2);
				String group3 = matcher.group(3);
				if (isPrintConsole){
					System.out.println("-------Group ---------");
					System.out.println(matcher.group());
					System.out.println("-------Group 1---------");
					System.out.println(group1);
					System.out.println("-------Group 2---------");
					System.out.println(group2);
					System.out.println("-------Group 3---------");
					System.out.println(group3);
				}
				matcher.appendReplacement(sb, "");
				sb.append(group2).append(";").append(group1).append(group3);
			}
			matcher.appendTail(sb);
			input = sb.toString();
			sb.setLength(0);

			if (isFileChanged){
				if (isOverWriteWorkSpace){
					Files.write(path, input.getBytes(), StandardOpenOption.WRITE);
				}
				else if (isWriteToLocalTestFolder){
					Path pathLocalFolder = Paths.get(localFolder).resolve(path.getFileName());
					Path pathLocalFolder_origin = Paths.get(localFolder+"_Origin").resolve(path.getFileName());
					if (!Files.exists(pathLocalFolder,LinkOption.NOFOLLOW_LINKS))
						Files.createDirectories(pathLocalFolder);
					if (!Files.exists(pathLocalFolder_origin, LinkOption.NOFOLLOW_LINKS))
						Files.createDirectories(pathLocalFolder_origin);
					Files.write(pathLocalFolder, input.getBytes(), StandardOpenOption.WRITE);
					Files.write(pathLocalFolder_origin, inputOrigin.getBytes(), StandardOpenOption.WRITE);
				}
				System.out.println(path);
				fileChangeCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeJsp(Path path) {
		PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("regex:.*jsp");
		if (!pathMatcher.matches(path)) return;
		boolean isFileChanged = false;
		try {
			String input = Files.lines(path, Charset.defaultCharset()).collect(Collectors.joining(FileSystems.getDefault().getSeparator()));
			StringBuffer sb = new StringBuffer();
			String inputOrigin = input;
			String regex; Pattern pattern; Matcher matcher;

			regex = "(document\\.forms\\[0\\].method\\[0\\]\\.click\\(\\);)(window\\.open[\\S\\s]*?)(\")";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			while (matcher.find()){
				isFileChanged = true;
				String group1 = matcher.group(1);
				String group2 = matcher.group(2);
				String group3 = matcher.group(3);
				if (isPrintConsole){
					System.out.println("-------Group ---------");
					System.out.println(matcher.group());
					System.out.println("-------Group 1---------");
					System.out.println(group1);
					System.out.println("-------Group 2---------");
					System.out.println(group2);
					System.out.println("-------Group 3---------");
					System.out.println(group3);
				}
				matcher.appendReplacement(sb, "");
				sb.append(group2).append(";").append(group1).append(group3);
			}
			matcher.appendTail(sb);
			input = sb.toString();
			sb.setLength(0);

			if (isFileChanged){
				if (isOverWriteWorkSpace){
					Files.write(path, input.getBytes());
				}
				else if (isWriteToLocalTestFolder){
					Path pathLocalFolder = Paths.get(localFolder).resolve(path.getFileName());
					Path pathLocalFolder_origin = Paths.get(localFolder+"_Origin").resolve(path.getFileName());
					if (!Files.exists(pathLocalFolder,LinkOption.NOFOLLOW_LINKS))
						Files.createDirectories(pathLocalFolder);
					if (!Files.exists(pathLocalFolder_origin,LinkOption.NOFOLLOW_LINKS))
						Files.createDirectories(pathLocalFolder_origin);
					Files.write(pathLocalFolder, input.getBytes(), StandardOpenOption.WRITE);
					Files.write(pathLocalFolder_origin, inputOrigin.getBytes(), StandardOpenOption.WRITE);
				}
				System.out.println(path);
				fileChangeCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
