package br.com.cubekode.jilotrack.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		return out.toByteArray();
	}

	public static long copy(InputStream from, OutputStream to) throws IOException {
		long count = 0;
		byte[] buffer = new byte[1024];
		for (int readed = from.read(buffer); readed != -1; readed = from.read(buffer)) {
			to.write(buffer, 0, readed);
			count += readed;
		}
		return count;
	}

	public static byte[] getResourceAsBytes(Class<?> classForClassLoader, String name) {
		try {
			InputStream from = classForClassLoader.getResourceAsStream(name);
			if (from != null) {
				return toByteArray(from);
			}
		} catch (Exception e) {
			// shhh
		}
		return null;
	}

	public static String getResourceAsString(Class<?> classForClassLoader, String name) {
		try {
			InputStream from = classForClassLoader.getResourceAsStream(name);
			if (from != null) {
				ByteArrayOutputStream to = new ByteArrayOutputStream();
				copy(from, to);
				return to.toString();
			}
		} catch (Exception e) {
			// shhh
		}
		return null;
	}

	public static void closeQuietly(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				// shhh
			}
		}
	}
}
