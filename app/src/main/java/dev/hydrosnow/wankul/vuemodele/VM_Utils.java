package dev.hydrosnow.wankul.vuemodele;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class VM_Utils {
	private static final int BUFFER_SIZE = 1024;
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static String HEADER_ACCEPT = "application/json";
	private static String HEADER_CONTENT_TYPE = "application/json; utf-8";
	
	public static void stringToOutputStream(final String str, final OutputStream os) throws IOException {
		byte[] input = str.getBytes(CHARSET);
		os.write(input, 0, input.length);
	}
	
	public static String inputStreamToString(final InputStream is) throws IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final byte[] buffer = new byte[BUFFER_SIZE];
		int length;
		while ((length = is.read(buffer)) != -1) {
			os.write(buffer, 0, length);
		}
		return new String(os.toByteArray(), CHARSET);
	}
	
	public static String makeHttpConnection(final String method, final String url_str) throws IOException {
		final URL url = new URL(url_str);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("Accept", HEADER_ACCEPT);
		con.setDoOutput(false);
		final String str = inputStreamToString(con.getInputStream());
		con.disconnect();
		return str;
	}
	
	public static String makeHttpConnection(final String method, final String url_str, final String output) throws IOException {
		final URL url = new URL(url_str);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE);
		con.setRequestProperty("Accept", HEADER_ACCEPT);
		con.setDoOutput(true);
		stringToOutputStream(output, con.getOutputStream());
		final String str = inputStreamToString(con.getInputStream());
		con.disconnect();
		return str;
	}
}
