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

					try {
						String input = m.getMethod().getLongName() + "//" + m.getFileName() + "//" + m.getLineNumber();
						m.replace("{ ist.meic.pa.Trace.putArgumentTrace($args, " + '"' + input + '"' + "); $_ = $proceed($$); ist.meic.pa.Trace.putReturnTrace(($w)$_, " + '"' + input + '"' + "); } ");
						
					} catch (NotFoundException e) {
						System.err.println(e.getLocalizedMessage());
					}
				}

				public void edit(NewExpr expr) throws CannotCompileException {			

					try {
						String input = expr.getConstructor().getLongName() + "//" + expr.getFileName() + "//" + expr.getLineNumber();
						expr.replace("{ $_ = $proceed($$); ist.meic.pa.Trace.putReturnTrace($_, " + '"' + input + '"' + "); } ");

					} catch (NotFoundException e) {
						System.err.println(e.getLocalizedMessage());
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
