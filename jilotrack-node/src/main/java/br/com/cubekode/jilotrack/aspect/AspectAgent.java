package br.com.cubekode.jilotrack.aspect;

import java.lang.instrument.Instrumentation;

/**
 * TODO use javaagent to execute instrumentation on JVM
 * 
 * @author adolfojunior
 */
public class AspectAgent {

	public static void premain(Instrumentation instrumentation, String... args) {
		// ClassFileTransformer transformer = TODO;
		// instrumentation.addTransformer(transformer);
	}
}
