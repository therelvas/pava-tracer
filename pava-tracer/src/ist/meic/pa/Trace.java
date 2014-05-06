package ist.meic.pa;

import java.lang.Object;
import java.util.IdentityHashMap;

public class Trace {

	private static IdentityHashMap<Object, String> traceMap = new IdentityHashMap<Object, String>();

	public static void print(Object o) {
		System.err.println("Tracing for " + o);
		System.err.println(traceMap.get(o));
	}

	public static void putTrace(Object key, Object[] args, String value) {

		StringBuffer stringBuffer = null;
		
		if(traceMap.get(key) != null) {			
			stringBuffer = new StringBuffer(traceMap.get(key));
			stringBuffer.append("\n" + value);
			traceMap.put(key, stringBuffer.toString());
		}
		else {
			traceMap.put(key, value);
		}
	}
}
