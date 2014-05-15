package ist.meic.pa;

import java.lang.Object;
import java.util.IdentityHashMap;

public class Trace {

	private static IdentityHashMap<Object, String> traceMap = new IdentityHashMap<Object, String>();

	public static void print(Object o) {

		if(o != null) {
			if(traceMap.containsKey(o)) {
				System.err.println(String.format("Tracing for %s", o.toString()));
				System.err.println(traceMap.get(o));
			}

			else {
				System.err.println(String.format("Tracing for %s is nonexistent!", o.toString()));
			}
		}
	}

	public static void putReturnTrace(Object key, String value) {

		String delims = "[/]";
		String[] returnString = value.split(delims);
		String returnDefaultString = String.format("  <- %s on %s:%s", returnString[0], returnString[1], returnString[2]); 

		appendResult(key, returnDefaultString);
	}

	public static void putArgumentTrace(Object[] args, String value) {

		if(args.length > 0) {

			String delims = "[/]";
			String[] argumentString = value.split(delims);
			String argumentDefaultString = String.format("  -> %s on %s:%s", argumentString[0], argumentString[1], argumentString[2]);

			for(int i = 0; i < args.length; i++) {
				appendResult(args[i], argumentDefaultString);
			}	
		}
	}

	public static void putExceptionTrace(Object exception, String value) {

		String delims = "[/]";
		String[] exceptionString = value.split(delims);
		String exceptionDefaultString = String.format("  Exception %s on %s:%s", exception.toString(), exceptionString[0], exceptionString[1]);

		appendResult(exception, exceptionDefaultString);
	}

	public static void putFieldTrace(Object fieldObj, String value) {

		String delims = "[/]";
		String[] fieldString = value.split(delims);
		String fieldDefaultString = String.format("  Field %s on %s:%s", fieldString[0], fieldString[1], fieldString[2]);

		appendResult(fieldObj, fieldDefaultString);
	}

	public static void putCastTrace(Object traceObj, String value) {

		String delims = "[/]";
		String[] fieldString = value.split(delims);
		String fieldDefaultString = String.format("  Cast to %s on %s:%s", fieldString[0], fieldString[1], fieldString[2]);

		appendResult(traceObj, fieldDefaultString);
	}

	/**
	 * Inserts a new value in the traceMap by appending 
	 * the old value with the new one
	 * 
	 * @param key The key where value is to be inserted
	 * @param value The value to be inserted
	 */
	private static void appendResult(Object key, String value) {

		if(traceMap.get(key) != null) {			
			StringBuffer stringBuffer = new StringBuffer(traceMap.get(key));
			stringBuffer.append("\n" + value);
			traceMap.put(key, stringBuffer.toString());
		}

		else if(key != null){
			traceMap.put(key, value);
		}

		else {
			return;
		}
	}
}