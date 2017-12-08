package com.feng.function;

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


/*
 * 1.1 find window.open( in ___Decorator.java, then find jsp has key="xxx___Decorator", then find the action class
 * 1.2 find window.open('${ in jsp, then find action class
 * 2. add prepopup() and popup() if not exists
 * 3.1 add map.put("access.popup", "popup"); in getKeyMethodMap()
 * 3.2 if getKeyMethodMap() doesn't exist, create it
 * 4.1 add list.add("access.popup"); in getNonValidateKey()
 * 4.2 if getNonValidateKey() doesn't exists, create it
 * 
 * public IObjectAssembly prepopup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method preback");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
		return objAssembly;
	}

	public ActionForward popup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Start of method back");
		}
		IObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);
	    return mapping.getInputForward();
	}
	
	map.put("access.popup", "popup");
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("access.search","search");
		map.put("access.home", "home");
		map.put("access.popup", "popup");
		return map;
	}
	
	list.add("access.popup");
	public List<String> getNonValidateKey()
    {
        List<String> list = new ArrayList<String>(); 
        list.add("access.back");
        list.add("access.popup");
        return list;
    }
 */
public class WindowOpenAction {

	
	private static boolean isOverWriteWorkSpace = false;
	private static boolean PRINTCONSOLE = false;
	private static int fileChangeCount = 0;
	private static Set<String> jspHasWindowOpenSet = new HashSet<>();
	private static int jspHasWindowOpenCount = 0;

	private static final String prepopup = "\tpublic IObjectAssembly prepopup(ActionMapping mapping, ActionForm form,\r\n"
			+ "\t\t\tHttpServletRequest request, HttpServletResponse response)\r\n"
			+ "\t\tthrows IOException, ServletException {\r\n"
			+ "\t\tif(LOGGER.isDebugEnabled()){\r\n"
			+ "\t\t\tLOGGER.debug(\"Start of method preback\");\r\n"
			+ "\t\t}\r\n"
			+ "\t\tIObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);\r\n"
			+ "\t\treturn objAssembly;\r\n"
			+ "\t}\r\n";
	private static final String popup = "\tpublic ActionForward popup(ActionMapping mapping, ActionForm form,\r\n"
			+ "\t\t\tHttpServletRequest request, HttpServletResponse response)\r\n"
			+ "\t\t\tthrows IOException, ServletException {\r\n"
			+ "\t\tif(LOGGER.isDebugEnabled()){\r\n"
			+ "\t\t\tLOGGER.debug(\"Start of method back\");\r\n"
			+ "\t\t}\r\n"
			+ "\t\tIObjectAssembly objAssembly = super.getObjectAssemblyFromSession(request);\r\n"
			+ "\t\treturn mapping.getInputForward();\r\n"
			+ "\t}\r\n";
	private static final String getKeyMethodMap = "\tprotected Map<String,String> getKeyMethodMap() {\r\n"
			+ "\t\tMap<String,String> map = new HashMap<>();\r\n"
			+ "\t\tmap.put(\"access.popup\", \"popup\");\r\n"
			+ "\t\treturn map;"
			+ "\t}\r\n";
	private static final String getNonValidateKey = "\tpublic List<String> getNonValidateKey() {\r\n"
			+ "\t\tList<String> list = new ArrayList<String>();\r\n"
			+ "\t\tlist.add(\"access.popup\");\r\n"
			+ "\t\t return list;\r\n"
			+ "\t}\r\n";
	
	private static final List<String> exceptionList = new LinkedList<>();
	private static final Set<String> decoratorSet = new HashSet<>();
	private static final Set<String> actionSet = new HashSet<>();
	
//	private static String [] directoryList = {"C:\\Workspace_Feng_Yang\\NewWorkSpace2\\RegexFixer\\FunctionTest\\WindowOpenAction"};
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
				findJspByCheckingJava(iterator.next().getAbsolutePath());
			}
		}
		if (PRINTCONSOLE){
			System.out.println("\n\n");
			decoratorSet.forEach(System.out::println);
		}

		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				findActionByCheckingJsp(iterator.next().getAbsolutePath());
			}
		}
		
		if (PRINTCONSOLE){
			System.out.println("\n\nactionSet");
			actionSet.forEach(System.out::println);
		}
		
		for (String directoryPath : directoryList){
			List<File> list = listAllFiles(directoryPath);
			Iterator<File> iterator = list.iterator();
			while (iterator.hasNext()){
				modifyActionClass(iterator.next().getAbsolutePath());
			}
		}

		System.out.println("-------------Done------------");
		System.out.println("total files changed : "+fileChangeCount);
		System.out.println("jsp has WindowOpen: " + jspHasWindowOpenCount+" places, "+jspHasWindowOpenSet.size()+" files");
//		System.out.println("-------------Please check the following exceptions : duplicate decorator classes-------------");
//		exceptionList.forEach(System.out::println);
	}

	private static void findJspByCheckingJava(String filePath){
		if (!filePath.endsWith("Decorator.java")) return;
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		if (PRINTCONSOLE){
			System.out.println("-------Scan Java-----------" + fileName + "------------------");
		}
		
		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bf = new BufferedReader(fileReader)){
			String input = bf.lines().collect(Collectors.joining("\n"));
			String regex; Pattern pattern; Matcher matcher;
			
			/*
			 * find onclick=\"window.open( in java, save into list which will be used to find jsp
			 */

			regex = "window\\s*\\.\\s*open\\s*\\(";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			if (matcher.find()){
				String group = matcher.group();
				if (PRINTCONSOLE){
					System.out.println("-----------wondow.open group------------");
					System.out.println(group);
				}
				String javaPath = getJavaPackage(input) + "." + fileName.substring(0,fileName.lastIndexOf("."));
				decoratorSet.add(javaPath);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void findActionByCheckingJsp(String filePath){
		if (!filePath.endsWith(".jsp")) return;
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		if (PRINTCONSOLE){
			System.out.println("-------Scan Jsp-----------" + fileName + "------------------");
		}
		
		boolean isJspContainDecorator = false;
		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bf = new BufferedReader(fileReader)){
			String input = bf.lines().collect(Collectors.joining("\n"));
			String regex; Pattern pattern; Matcher matcher;
			
			/*
			 * check any jsp has decorator="xxx___Decorator", if so and it's not commented out
			 */
			outer:
				for (String decorator : decoratorSet){
					if (PRINTCONSOLE){
						System.out.println(decorator);
					}
					regex = "decorator\\s*\\=\\s*\"\\s*"+decorator.replace(".", "\\.");
					pattern = Pattern.compile(regex);
					matcher = pattern.matcher(input);
					while(matcher.find()){
						if (PRINTCONSOLE){
							System.out.println("-------find decorator----");
							System.out.println(matcher.group());
						}
						if (isJspBlockCommentOut(matcher.start(), matcher.end(), input)){
							continue;
						}
						isJspContainDecorator = true;
						break outer;
					}
				}
			
			/*
			 * check if jsp havs window.open('${,
			 */
//			if (!isJspContainDecorator){
				regex = "window\\s*\\.\\s*open\\s*\\(\\s*'\\s*\\$\\s*\\{";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(input);
				while (matcher.find()){
					if (PRINTCONSOLE){
						System.out.println("-------find window.open('${----");
						System.out.println(matcher.group());
					}
					if (isJspBlockCommentOut(matcher.start(), matcher.end(), input)){
						jspHasWindowOpenSet.add(fileName);
						jspHasWindowOpenCount++;
						continue;
					}
					isJspContainDecorator = true;
					jspHasWindowOpenSet.add(fileName);
					jspHasWindowOpenCount++;
				}
//			}
			
			if (isJspContainDecorator){
				String action = fileName.replace(".jsp", "action.java").toLowerCase();
				actionSet.add(action);
				if (PRINTCONSOLE){
					System.out.println("---------find action : "+ action);
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
	
	private static void modifyActionClass(String filePath) {
		String fileName = filePath.substring(filePath.lastIndexOf("\\")+1);
		if (!actionSet.contains(fileName.toLowerCase())) return;
		if (PRINTCONSOLE){
			System.out.println("-------Action :   " + fileName + "------------------");
		}
		
		boolean isOverWrite = false;
		boolean isPrepopupExist = false;
		boolean isPopupExist = false;
		boolean isGetKeyMethodMapExist = false;
		boolean isGetKeyMethodMapHasPopup = false;
		boolean isGetNonValidateKeyExist = false;
		boolean isGetNonValidateKeyHasPopup = false;
		
		try (FileReader fileReader = new FileReader(filePath);
				BufferedReader bf = new BufferedReader(fileReader)){
			String input = bf.lines().collect(Collectors.joining("\n"));
			StringBuffer sb = new StringBuffer();
			String regex; Pattern pattern; Matcher matcher;
			
			/* check if prepopup() exists*/
			regex = "IObjectAssembly\\s+prepopup\\s*\\(\\s*ActionMapping\\s+\\w+\\s*,\\s*ActionForm\\s+\\w+\\s*,\\s*HttpServletRequest\\s+\\w+\\s*,\\s*HttpServletResponse\\s+\\w+\\s*\\)";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			if (!matcher.find()){
				isPrepopupExist = false;
				isOverWrite = true;
			}
			
			/* check if popup() exists*/
			regex = "ActionForward\\s+popup\\s*\\(\\s*ActionMapping\\s+\\w+\\s*,\\s*ActionForm\\s+\\w+\\s*,\\s*HttpServletRequest\\s+\\w+\\s*,\\s*HttpServletResponse\\s+\\w+\\s*\\)";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			if (!matcher.find()){
				isPopupExist = false;
				isOverWrite = true;
			}
			
			/* check if getKeyMethodMap() exists*/
			regex = "Map\\s*(\\<\\s*String\\s*,\\s*String\\s*\\>)?\\s*getKeyMethodMap\\s*\\(\\s*\\)\\s*\\{[\\S\\s]*?\\}";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			if (matcher.find()){
				isGetKeyMethodMapExist = true;
				String group = matcher.group();
				if (group.contains("access.popup")){
					isGetKeyMethodMapHasPopup = true;
				}
				else{
					isOverWrite = true;
					matcher.appendReplacement(sb, "");
					if (group.contains("null")){
						group = group.replace("return", "Map<String, String> map = new HashMap<String, String>();\r\n\t\tmap.put(\"access.popup\", \"popup\");\r\n\t\treturn");
						group = group.replace("null", "map");
					}
					else{
						group = group.replace("return", "map.put(\"access.popup\", \"popup\");\r\n\t\treturn");
					}
					sb.append(group);
				}
			}
			matcher.appendTail(sb);
			input = sb.toString();
			sb.setLength(0);
			
			/* check if getNonValidateKey() exists*/
			regex = "List\\s*(\\<\\s*String\\s*\\>)?\\s*getNonValidateKey\\s*\\(\\s*\\)\\s*\\{[\\S\\s]*?\\}";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(input);
			if (matcher.find()){
				isGetNonValidateKeyExist = true;
				String group = matcher.group();
				if (group.contains("access.popup")){
					isGetNonValidateKeyHasPopup = true;
				}
				else{
					isOverWrite = true;
					matcher.appendReplacement(sb, "");
					if (group.contains("null")){
						group = group.replace("return", "List<String> list = new ArrayList<String>();\r\n\t\tlist.add(\"access.popup\");\r\n\t\treturn");
						group = group.replace("null", "list");
					}
					else{
						group = group.replace("return", "list.add(\"access.popup\");\r\n\t\treturn");
					}
					sb.append(group);
				}
			}
			matcher.appendTail(sb);
			input = sb.toString();
			sb.setLength(0);
			
			if (!isGetKeyMethodMapExist){
				int last = input.lastIndexOf("}");
				input = input.substring(0, last) + "\r\n" +  getKeyMethodMap + "}";
			}
			
			if (!isGetNonValidateKeyExist){
				int last = input.lastIndexOf("}");
				input = input.substring(0, last) + "\r\n" +  getNonValidateKey + "}";
			}
			
			if (!isPrepopupExist){
				int last = input.lastIndexOf("}");
				input = input.substring(0, last) + "\r\n" +  prepopup + "}";
			}
			
			if (!isPopupExist){
				int last = input.lastIndexOf("}");
				input = input.substring(0, last) + "\r\n" +  popup + "}";
			}
			
			if (isOverWrite){
				if (isOverWriteWorkSpace){
					try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
						fileOutputStream.write(input.getBytes());
						fileOutputStream.close();
					}
				}
				System.out.println("\n"+fileName);
				if (!isPrepopupExist) System.out.println("create prepopup()");
				if (!isPopupExist) System.out.println("create popup()");
				if (!isGetKeyMethodMapExist) System.out.println("create getKeyMethodMap()");
				else
					if (!isGetKeyMethodMapHasPopup) System.out.println("add entry to getKeyMethodMap()");
				if (!isGetNonValidateKeyExist) System.out.println("create getNonValidateKey()");
				else
					if (!isGetNonValidateKeyHasPopup) System.out.println("add entry to getNonValidateKey()");
				fileChangeCount++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getJavaPackage(String input) {
		Pattern pattern = Pattern.compile("package\\s*(.*?);");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()){
			return matcher.group(1);
		}
		return null;
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
	
	/**
	 * Find JSP Comment Block. JSP comment starts with <!-- end with -\\s*-\\s*>
	 * @param String input string
	 * @return Map<Integer, Integer> hashmap that stores the starting and ending index of the input string
	 */
	private static Map<Integer, Integer> getJspCommentBlockIndexMap(String input){
		Map<Integer, Integer> map = new HashMap<>();
		String regex = "\\<\\!\\-\\-[\\S\\s]*?\\-\\s*\\-\\s*\\>";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()){
			map.put(matcher.start(), matcher.end());
		}
		return map;
	}
	
	/**
	 * Check if JSP code block is commented out
	 * @param int starting index of the block
	 * @param int ending index of the block
	 * @param String input string
	 * @return boolean true if the block is commented out; false otherwise
	 */
	private static boolean isJspBlockCommentOut(int start, int end, String input){
		Map<Integer, Integer> map = getJspCommentBlockIndexMap(input);
		boolean isBlockCommentOut = false;
		for (int key : map.keySet()){
			int value = map.get(key);
			if (key < start && end <= value){
				isBlockCommentOut = true;
				break;
			}
		}
		return isBlockCommentOut;
	}
}
