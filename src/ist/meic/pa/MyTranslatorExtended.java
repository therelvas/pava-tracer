package ist.meic.pa;

import javassist.*;
import javassist.expr.Cast;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.Handler;

public class MyTranslatorExtended implements Translator {

	@Override
	public void onLoad(ClassPool cp, String className) throws NotFoundException, CannotCompileException {

		final CtClass cc = cp.get(className);	
		String packageName = cc.getPackageName();		

		if(packageName == null || !packageName.equals("ist.meic.pa")) {

			cc.instrument(new ExprEditor() {

				public void edit(Handler h) throws CannotCompileException {

					String input = h.getFileName() + "/" + h.getLineNumber();
					h.insertBefore("{ ist.meic.pa.Trace.putExceptionTrace($1," + '"' + input + '"' + "); }");
				}

				public void edit(FieldAccess f) throws CannotCompileException {
					
					String input = f.getFieldName() + "/" + f.getFileName() + "/" + f.getLineNumber();
				
					if(f.isReader()) {
						f.replace("{ $_ = $proceed($$); ist.meic.pa.Trace.putFieldTrace(($w)$_," + '"' + input + '"' + "); }");
					} else if(f.isWriter()){
						f.replace("{  $_ = $proceed($$); ist.meic.pa.Trace.putFieldTrace(($w)$1," + '"' + input + '"' + "); }");
					} 
				}		

				public void edit(Cast c) throws CannotCompileException {

					try {
						String input = c.getType().getName() + "/" + c.getFileName() + "/" + c.getLineNumber();
						c.replace("{ $_ = $proceed($$); ist.meic.pa.Trace.putCastTrace(($w)$_," + '"' + input + '"' + "); }");
					} catch (NotFoundException e) {
						e.printStackTrace();
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
