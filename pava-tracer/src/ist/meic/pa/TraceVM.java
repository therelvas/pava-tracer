package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;

public class TraceVM {

	public static void main(String[] args) {

		if(args.length < 1) {
			System.err.println("No arguments found");
			System.exit(1);

		} else {

			ClassPool cp = ClassPool.getDefault();
			Loader loader = new Loader();
			
			try {
				loader.addTranslator(cp, new MyTranslator());
				loader.run(args);
			} catch (Exception e) {
				System.err.println(e.getCause() + ": " + e.getLocalizedMessage());
			} catch (Throwable e) {
				System.err.println(e.getCause() + ": " + e.getLocalizedMessage());
			}
		}
	}	
}
