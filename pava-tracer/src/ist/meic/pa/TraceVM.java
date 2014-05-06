package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;

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
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotCompileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}	
}
