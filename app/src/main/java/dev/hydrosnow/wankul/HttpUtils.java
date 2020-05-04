package dev.hydrosnow.wankul;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpUtils {
	private static final int BUFFER_SIZE = 1024;
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static String HEADER_ACCEPT = "application/json";
	private static String HEADER_CONTENT_TYPE = "application/json; utf-8";
	
	public static byte[] inputSteamToBytes(final InputStream is) throws IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final byte[] buffer = new byte[BUFFER_SIZE];
		int length;
		while ((length = is.read(buffer)) != -1) {
			os.write(buffer, 0, length);
		}
		return os.toByteArray();
	}
	
	public static byte[] makeRawHttpConnection(final String method, final String url_str) throws IOException {
		final URL url = new URL(url_str);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("Accept", HEADER_ACCEPT);
		con.setDoOutput(false);
		final InputStream is = con.getInputStream();
		final byte[] bytes = inputSteamToBytes(is);
		con.disconnect();
		return bytes;
	}
	
	public static String makeHttpConnection(final String method, final String url_str) throws IOException {
		final byte[] bytes = makeRawHttpConnection(method, url_str);
		return new String(bytes, CHARSET);
	}
	
	public static byte[] makeRawHttpConnection(final String method, final String url_str, final byte[] data) throws IOException {
		final URL url = new URL(url_str);
		final HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method);
		con.setRequestProperty("Content-Type", HEADER_CONTENT_TYPE);
		con.setRequestProperty("Accept", HEADER_ACCEPT);
		con.setDoOutput(true);
		final OutputStream os = con.getOutputStream();
		os.write(data);
		final InputStream is = con.getInputStream();
		final byte[] bytes = inputSteamToBytes(is);
		con.disconnect();
		return bytes;
	}
	
	public static String makeHttpConnection(final String method, final String url_str, final String data) throws IOException {
		final byte[] data_bytes = data.getBytes(CHARSET);
		final byte[] bytes = makeRawHttpConnection(method, url_str, data_bytes);
		return new String(bytes, CHARSET);
	}
}
