package br.com.cubekode.jilotrack.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IdentityUtil {

	private static String LOCAL_HOST_ADDRESS;
	private static String LOCAL_HOST_NAME;

	static {
		try {
			InetAddress address = InetAddress.getLocalHost();
			LOCAL_HOST_ADDRESS = address.getHostAddress();
			LOCAL_HOST_NAME = address.getHostName();
		} catch (UnknownHostException e) {
			// always know localhost
			LOCAL_HOST_ADDRESS = "127.0.0.1";
			LOCAL_HOST_NAME = "localhost";
		}
	}

	public static String timeMillis() {
		return number(System.currentTimeMillis());
	}

	public static String localHostAddress() {
		return LOCAL_HOST_ADDRESS;
	}

	public static String localHostName() {
		return LOCAL_HOST_NAME;
	}

	public static String threadName() {
		return Thread.currentThread().getName();
	}

	public static String object(Object o) {
		return number(System.identityHashCode(o));
	}

	public static String number(int n) {
		return Integer.toString(n, Character.MAX_RADIX).toUpperCase();
	}

	public static String number(long n) {
		return Long.toString(n, Character.MAX_RADIX).toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println("Identity ThreadName: " + threadName());
		System.out.println("Identity LocalHostName: " + localHostName());
		System.out.println("Identity LocalHostAddress: " + localHostAddress());
		System.out.println("Identity Object:" + object(System.out));
	}

	public static String apiSignature() {
		return IOUtil.getResourceAsString(IOUtil.class, "/META-INF/signature");
	}

	public static String uid(Object object) {
		// Machine + Memory Address + Time.
		return (localHostAddress() + "_" + object(object) + "_" + timeMillis()).replace(".", "_");
	}
}
