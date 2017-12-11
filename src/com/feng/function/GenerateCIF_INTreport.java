package com.feng.function;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateCIF_INTreport {

	private static boolean isTest = false;
	private static String REPORTFOLDER = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\";
	private static String INPUTFILE = "C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\GenerateCIF_INTreport_input\\input.txt";
	private static String [] directoryList;
	private static String directoryBase = "C:/Workspace_Feng_Yang/CIF_INT/MO_R2.14.1_Code/";
//	private static List<List<String>> LIST = new LinkedList<>();
	private static Map<String, List<String>> CIF_INT_MAP = new HashMap<>();

	public static void main(String[] args) throws Exception {


		String [] directoryListTest = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\GenerateCIF_INTreport"};
		String [] directoryListWorkSpace = {"C:\\Workspace_Feng_Yang\\UI_1.2_Tax\\Benefits", 
				directoryBase+"Benefits",
				directoryBase+"Framework",
				directoryBase+"config",
				directoryBase+"Tax",
				directoryBase+"UIWorkflowListener"};

		if (isTest){
			directoryList = directoryListTest;
		}else{
			directoryList = directoryListWorkSpace;
		}

		Set<String> cif_int_set = read_cif_int_file(INPUTFILE);
		if (cif_int_set == null){
			throw new Exception("readCIF_INTfile return null");
		}
		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				execute(iterator.next().getAbsolutePath(), cif_int_set);
			}
		}
		writeToExcel();
		System.out.println("-------------Done. Check your desktop for report------------");
	}
	
	private static void execute(String absolutePath, Set<String> cif_int_set) {
//		if (!absolutePath.endsWith("java")) return;
		System.out.println(absolutePath);
		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			
			String input = br.lines().collect(Collectors.joining("\n"));
			String regex; Pattern pattern; Matcher matcher;
			Set<String>set = new HashSet<>();
			
			

			regex = "CIF_(?:INT|ST)_(?:\\d+)";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			while(matcher.find()){
				String group = matcher.group();
				System.out.println(group);
				if (cif_int_set.contains(group)){
					set.add(group);
					if (CIF_INT_MAP.containsKey(group) && !CIF_INT_MAP.get(group).contains(absolutePath)){
						CIF_INT_MAP.get(group).add(absolutePath);
					}
					else if (!CIF_INT_MAP.containsKey(group)){
						List<String> temp = new LinkedList<>();
						temp.add(absolutePath);
						CIF_INT_MAP.put(group, temp);
					}
				}
			}
//			if (!set.isEmpty()){
//				List<String> tempList = new LinkedList<>();
//				tempList.add(absolutePath);
//				tempList.addAll(set);
//				System.out.println(tempList);
//				LIST.add(tempList);
//			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Set<String> read_cif_int_file(String absolutePath){
		try (FileReader fileReader = new FileReader(absolutePath);
				BufferedReader br = new BufferedReader(fileReader)) {
			String string;
			Set<String> set = new HashSet<>();
			while ( (string = br.readLine()) != null){
				set.add(string);
			}
			return set;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void writeToExcel() {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Defect_Based_Report");
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Defect");
		cell = row.createCell(1);
		cell.setCellValue("File");
		cell = row.createCell(2);
		cell.setCellValue("Project");
		cell = row.createCell(3);
		cell.setCellValue("Path");

		int rowNum = 1;
		for (String key : CIF_INT_MAP.keySet()){
			List<String> valueList = CIF_INT_MAP.get(key);
			for (String absolutePath : valueList){
				row = sheet.createRow(rowNum++);
				String fileName = absolutePath.substring(absolutePath.lastIndexOf("\\")+1);
				String project = absolutePath.substring(directoryBase.length(), absolutePath.indexOf("\\", directoryBase.length()));
				int colNum = 0;
				cell = row.createCell(colNum++);
				cell.setCellValue(key);
				cell = row.createCell(colNum++);
				cell.setCellValue(fileName);
				cell = row.createCell(colNum++);
				cell.setCellValue(project);
				cell = row.createCell(colNum++);
				cell.setCellValue(absolutePath);
			}
		}
//		
//		sheet = workbook.createSheet("File_Based_Report");
//		rowNum = 0;
//		
//		row = sheet.createRow(rowNum++);
//		cell = row.createCell(0);
//		cell.setCellValue("File");
//		cell = row.createCell(1);
//		cell.setCellValue("Project");
//		cell = row.createCell(2);
//		cell.setCellValue("Defect");
//		
//		for (List<String> list : LIST){
//			row = sheet.createRow(rowNum++);
//			int colNum = 0;
//			int count = 0;
//			String fileName = list.get(0);
//			String project = list.get(1);
//			for (String s : list){
//				if (count >= 2){
//					cell = row.createCell(colNum++);
//					cell.setCellValue(s);
//				}
//				else if (count == 1){
//					cell = row.createCell(colNum++);
//					cell.setCellValue(project);
//					count++;
//				}
//				else{
//					cell = row.createCell(colNum++);
//					cell.setCellValue(fileName);
//					count++;
//				}
//			}
//		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(REPORTFOLDER+"CIF_INT_REPORT.xlsx");
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)){

			workbook.write(bufferedOutputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
