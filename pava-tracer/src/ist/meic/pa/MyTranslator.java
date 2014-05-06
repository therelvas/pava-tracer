package ist.meic.pa;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class MyTranslator implements Translator {

	@Override
	public void onLoad(ClassPool cp, String className) throws NotFoundException, CannotCompileException {

		final CtClass cc = cp.get(className);	
		String packageName = cc.getPackageName();		

		if(packageName == null || !packageName.equals("ist.meic.pa")) {

			cc.instrument(new ExprEditor() {

				public void edit(MethodCall m) throws CannotCompileException {

					String name = null;
					try {
						name = m.getMethod().getLongName();
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String input = "<- " + name + " on " + m.getFileName() + ":" + m.getLineNumber();
					m.replace("{ $_ = $proceed($$); ist.meic.pa.Trace.putTrace($_, $args, " + '"' + input + '"' + "); } ");
				}

				public void edit(NewExpr e) throws CannotCompileException {			
					
					try {
						String name = e.getConstructor().getLongName();
						String input = "<- " + name + " on " + e.getFileName() + ":" + e.getLineNumber();
						e.replace("{ $_ = $proceed($$); ist.meic.pa.Trace.putTrace($_, $args, " + '"' + input + '"' + "); } ");
					
					} catch (NotFoundException e1) {
						e1.printStackTrace();
					}
				}		
			});
		}
	}

	@Override
	public void start(ClassPool arg0) throws NotFoundException, CannotCompileException {
		//Nothing to do here
	}
}
