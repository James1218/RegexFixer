package com.feng.function;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FindBatchEmail {

	private static boolean isOverWriteWorkSpace = false;
	private static boolean isWriteToLocalTestFolder = false;
	private static boolean isTest = false;
	private static String localFolder = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\";
	private static int fileChangeCount = 0;
	private static List<List<String>> listJava = new LinkedList<>();
	private static List<List<String>> listXml = new LinkedList<>();
	private static List<List<String>> listJavaConstant = new LinkedList<>();


	public static void main(String[] args) {


		String [] directoryListTest = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\FindBatchEmail"};
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
		List<String> list1 = new LinkedList<>();
		list1.add("File Name");
		list1.add("Email");
		listJava.add(list1);
		List<String> list2 = new LinkedList<>();
		list2.add("Batch name");
		list2.add("Email");
		listXml.add(list2);
		List<String> list3 = new LinkedList<>();
		list3.add("Params");
		list3.add("Email");
		listJavaConstant.add(list3);

		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				execute(iterator.next().getAbsolutePath());
			}
		}
		writeToExcel();
		System.out.println("-------------Done. Check your desktop------------");
		System.out.println(listJava);
		System.out.println(listJavaConstant);
		System.out.println(listXml);

	}

	private static void writeToExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java");
		int rowNum = 0;
		for (List<String> list : listJava){
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String s : list){
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(s);
			}
		}

		sheet = workbook.createSheet("JavaConstant");
		rowNum = 0;
		for (List<String> list : listJavaConstant){
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String s : list){
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(s);
			}
		}

		sheet = workbook.createSheet("Xml");
		rowNum = 0;
		for (List<String> list : listXml){
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String s : list){
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(s);
			}
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(localFolder+"FindBatchEmail.xlsx");
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)){

			workbook.write(bufferedOutputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void execute(String absolutePath) {
		System.out.println(absolutePath);
		if (absolutePath.endsWith(".java")){
			if (absolutePath.toLowerCase().contains("constant")){
				executeConstant(absolutePath);
			}
			else{
				executeJava(absolutePath);
			}
		}
		else if (absolutePath.contains("batchconfig.xml")){
			executeXml(absolutePath);
		}
	}

	private static void executeConstant(String absolutePath) {

		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			String input = br.lines().collect(Collectors.joining("\n"));
			String regex; Pattern pattern; Matcher matcher;

			regex = "String.*?(\\w+)\\s*\\=\\s*\"anshul.kumar@wyo.gov\"";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			while(matcher.find()){
				System.out.println(matcher.group());
				List<String> list = new LinkedList<>();
				list.add(matcher.group(1));
				list.add("anshul.kumar@wyo.gov");
				listJavaConstant.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeXml(String absolutePath) {

		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			String input = br.lines().collect(Collectors.joining("\n"));
			String regex; Pattern pattern; Matcher matcher;

			regex = "\\<batch\\s+name\\s*\\=\\s*\"(\\w+)\"[\\S\\s]*?\\<value\\>anshul\\.kumar@wyo.gov\\</value\\>[\\S\\s]*?\\</batch>";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			while(matcher.find()){
				List<String> list = new LinkedList<>();
				list.add(matcher.group(1));
				list.add("anshul.kumar@wyo.gov");
				listXml.add(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeJava(String absolutePath) {
		if (!absolutePath.endsWith(".java")) return;
		String fileName = absolutePath.substring(absolutePath.lastIndexOf("\\")+1);
		boolean isFileChanged = false;

		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			String input = br.lines().collect(Collectors.joining("\n"));
			String inputOrigin = input;
			String regex; Pattern pattern; Matcher matcher;

			if (input.contains("anshul.kumar@wyo.gov")){
				List<String> list = new LinkedList<>();
				list.add(fileName);
				list.add("anshul.kumar@wyo.gov");
				listJava.add(list);
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

}
