package com;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {

	private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789+/";
	private static final int fillchar = '=';

	/**
	 * Decodes a base64 String.
	 * 
	 * @param data
	 *            a base64 encoded String to decode.
	 * @return the decoded String.
	 */
	public static String decodeBase64(String data) {
		if (data != null) {
			return decodeBase64(data.getBytes());
		} else {
			return null;
		}
	}

	/**
	 * Decodes a base64 aray of bytes.
	 * 
	 * @param data
	 *            a base64 encode byte array to decode.
	 * @return the decoded String.
	 */
	public static String decodeBase64(byte[] data) {
		int c, c1;
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; ++i) {
			c = cvt.indexOf(data[i]);
			++i;
			c1 = cvt.indexOf(data[i]);
			c = ((c << 2) | ((c1 >> 4) & 0x3));
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (fillchar == c) {
					break;
				}

				c = cvt.indexOf((char) c);
				c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
				ret.append((char) c1);
			}

			if (++i < len) {
				c1 = data[i];
				if (fillchar == c1) {
					break;
				}

				c1 = cvt.indexOf((char) c1);
				c = ((c << 6) & 0xc0) | c1;
				ret.append((char) c);
			}
		}
		return ret.toString();
	}

	public static String encodeBase64(String data) {
		if (data != null) {
			return encodeBase64(data.getBytes());
		} else {
			return null;
		}
	}

	/**
	 * Encodes a byte array into a base64 String.
	 * 
	 * @param data
	 *            a byte array to encode.
	 * @return a base64 encode String.
	 */
	public static String encodeBase64(byte[] data) {
		int c;
		int len = data.length;
		StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
		for (int i = 0; i < len; ++i) {
			c = (data[i] >> 2) & 0x3f;
			ret.append(cvt.charAt(c));
			c = (data[i] << 4) & 0x3f;
			if (++i < len) {
				c |= (data[i] >> 4) & 0x0f;
			}

			ret.append(cvt.charAt(c));
			if (i < len) {
				c = (data[i] << 2) & 0x3f;
				if (++i < len) {
					c |= (data[i] >> 6) & 0x03;
				}

				ret.append(cvt.charAt(c));
			} else {
				++i;
				ret.append((char) fillchar);
			}

			if (i < len) {
				c = data[i] & 0x3f;
				ret.append(cvt.charAt(c));
			} else {
				ret.append((char) fillchar);
			}
		}
		return ret.toString();
	}
	 public static String gzip(String str) {
	        if (str == null || str.length() == 0) {
	            return "";
	        }
	        try {
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            GZIPOutputStream gzipos = new GZIPOutputStream(bos);
	            gzipos.write(str.getBytes("iso8859-1"), 0, str.length());
	            gzipos.flush();
	            gzipos.finish();
	            gzipos.close();
	            byte[] bytes = bos.toByteArray();
	            System.out.println("bytes:"+bytes.toString());
	            return encodeBase64(bytes).replace("+", "-").replace("/", "_").replace("=", ".");
	        } catch (Exception e) {
	        }
	        return "";
	    }
	 public static String ungzip(String str) {
	        if (str == null || str.length() == 0) {
	            return "";
	        }
	        try {
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] bytes =
	                    decodeBase64(str.replace("-", "+").replace("_", "/").replace(".", "=")).getBytes("iso8859-1");
	            GZIPInputStream gzipis = new GZIPInputStream(new ByteArrayInputStream(bytes));
	            byte[] bs = new byte[256];
	            int rn = 0;
	            while ((rn = gzipis.read(bs, 0, 256)) >= 0) {
	                bos.write(bs, 0, rn);
	            }
	            gzipis.close();
	            return new String(bos.toByteArray());
	        } catch (Exception e) {
	        }
	        return "";
	    }
	public static void main(String[] args) {
		String encode = encodeBase64("abc123?");
		System.out.println("encode：" + encode);
		String decode = decodeBase64(encode);
		System.out.println("decode：" + decode);
		String gzipEncode = gzip("abc123?");
		System.out.println("gzipEncode：" + gzipEncode);
		String gzipdecode = ungzip(gzipEncode);
		System.out.println("gzipdecode：" + gzipdecode);
	}
}
