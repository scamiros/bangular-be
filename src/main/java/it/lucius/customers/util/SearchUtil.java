package it.lucius.customers.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
	
	public static final String NULL = "<null>";
//	private final static Logger LOG = Logger.getLogger(SearchUtil.class);
	
	public static String startWith(String param){
		if(param == null){
			param = "%";
		} else {
			param = param.toLowerCase().trim() + "%";
		}
		return param;
	}

	public static String fullSearch(String param){
		if(param == null){
			param = "%";
		} else {
			param = "%" + param.toLowerCase().trim() + "%";
		}
		return param;
	}
	
	public static String notEmptyString(String string){
		return notEmptyString(string, NULL);
	}
	
	public static String notEmptyString(String string, String nullValue){
		return (string == null || string.length() <= 0)?nullValue:string;
	}

	public static List<Integer> splitIdByPipe(String stringToSplit){
		List<Integer> idList = null;
		if(stringToSplit != null && stringToSplit.length() > 0){
			idList = new ArrayList<Integer>();
			if(stringToSplit.contains("|")){
				for(String s: stringToSplit.split("\\|")){
					try{
						idList.add(Integer.parseInt(s));
					}catch(NumberFormatException e){
//						LOG.error("Number format exception. Unespected aziende: " + s);
					}
				}
			}else{
				try{
					idList.add(Integer.parseInt(stringToSplit));
				}catch(NumberFormatException e){
//					LOG.error("Number format exception. Unespected idList: " + stringToSplit);
				}
				
			}
		}
		return idList;
	}
	
	public static Class<?> classField(String[] tableView,String fieldName) throws ClassNotFoundException {	
		int i = 0;
		boolean found = false;
		Class<?> foundedClass = null;
		fieldName = fieldName.toLowerCase().trim();
		while(found == false) {
			foundedClass = Class.forName(tableView[i]);
			Field[] fArr = foundedClass.getDeclaredFields();
			for(int j=0;j< fArr.length && found == false;j++) {
				String fieldTmp = fArr[j].getName().toLowerCase();
				if(fieldName.equals(fieldTmp)) {
					found = true;
				}
			}
			i++;
		}
		return foundedClass;
	}
}