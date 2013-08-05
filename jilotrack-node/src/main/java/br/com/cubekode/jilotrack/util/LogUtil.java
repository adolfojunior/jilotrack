package br.com.cubekode.jilotrack.util;

public class LogUtil {

	public static void log(Object s) {
		System.out.println("####" + s);
	}

	public static void log(Object... o) {
		System.out.print("####");
		for (Object os : o) {
			System.out.print(String.valueOf(os));
		}
		System.out.println();
	}

	public static void error(String message, Throwable e) {
		System.err.print("####");
		System.err.print(message);
		System.err.print(": ");
		System.err.println(e);
	}
}
