import ist.meic.pa.Trace;

class _Test3 {
	
	private Integer[] teste = new Integer[2];
	private Integer teste2 = new Integer(2);
	
	public void test() {
		
		try {
			Object o = new String("ola");
			String str = (String) o;
			Trace.print(o);
			
			Trace.print(teste2);
			teste2 = teste[1];	
			Trace.print(teste2);
			
		} catch (IndexOutOfBoundsException e){
			Trace.print(e);
		}
	}
}

public class Test3 {
    public static void main(String args[]) {
        (new _Test3()).test();
    }
}