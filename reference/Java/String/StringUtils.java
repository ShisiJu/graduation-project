/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.forestar.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;

public class StringUtils {
	public static boolean isSpace(String value) {
		if ((value == null) || ("".equals(value))) {
			return true;
		}

		return (value.trim().length() == 0);
	}

	public static boolean isNotSpace(String value) {
		return (!(isSpace(value)));
	}

	public static boolean hasLength(String value) {
		return ((null != value) && (!("".equals(value.trim()))));
	}

	public static int objectToInt(Object obj) {
		int result = 0;
		if ((null == obj) || ("".equals(obj)))
			return 0;
		try {
			result = Integer.parseInt(obj.toString());
		} catch (Exception localException) {
		}
		return result;
	}

	public static boolean getBool(String strBool) {
		if (isSpace(strBool)) {
			return false;
		}
		strBool = strBool.trim().toUpperCase();

		return ((strBool.equals("1")) || (strBool.equals("TRUE")) || (strBool.equals("是")) || (strBool.equals("YES")));
	}

	public static boolean isEmpty(String value) {
		return ((value == null) || ("".equals(value)));
	}

	public static boolean isNotEmpty(String value) {
		return (!(isEmpty(value)));
	}

	public static boolean isEmpty(Object value) {
		return ((value == null) || ("".equals(value.toString())));
	}

	public static boolean isNotEmpty(Object value) {
		return (!(isEmpty(value)));
	}

	public static String padLeft(String str, int padLength, char padLeftStr) {
		if (str.length() < padLength) {
			int padLengthIng = padLength - str.length();
			for (int i = 0; i < padLengthIng; ++i) {
				str = new StringBuilder().append(padLeftStr).append(str).toString();
			}
		}
		return str;
	}

	public static String padRight(String str, int padLength, char padLeftStr) {
		if (str.length() < padLength) {
			int padLengthIng = padLength - str.length();
			for (int i = 0; i < padLengthIng; ++i) {
				str = new StringBuilder().append(str).append(padLeftStr).toString();
			}
		}
		return str;
	}

	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if ((url.equals("")) || (url.startsWith("http://")) || (url.startsWith("https://"))) {
			return url;
		}
		return new StringBuilder().append("http://").append(url.trim()).toString();
	}

	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (org.apache.commons.lang.StringUtils.isBlank(str)) {
			return null;
		}
		if (!(org.apache.commons.lang.StringUtils.isBlank(sep2))) {
			str = org.apache.commons.lang.StringUtils.replace(str, sep2, sep);
		}
		String[] arr = org.apache.commons.lang.StringUtils.split(str, sep);

		int i = 0;
		for (int len = arr.length; i < len; ++i) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	public static String txt2htm(String txt) {
		if (org.apache.commons.lang.StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2D));

		boolean doub = false;
		for (int i = 0; i < txt.length(); ++i) {
			char c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}

	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}

		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; (count < maxCount) && (i < slen); ++i) {
			if (s.codePointAt(i) < 256)
				++count;
			else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				--i;
			}
			if (!(org.apache.commons.lang.StringUtils.isBlank(append))) {
				if (s.codePointAt(i - 1) < 256)
					i -= 2;
				else {
					--i;
				}
				return new StringBuilder().append(s.substring(0, i)).append(append).toString();
			}
			return s.substring(0, i);
		}

		return s;
	}

	public static String htmlCut(String s, int len, String append) {
		String text = html2Text(s, len * 2);
		return textCut(text, len, append);
	}

	public static String html2Text(String html, int len) {
		try {
			Lexer lexer = new Lexer(html);

			StringBuilder sb = new StringBuilder(html.length());
			do {
				Node node;
				if ((node = lexer.nextNode()) == null)
					break;
				if (node instanceof TextNode)
					sb.append(node.toHtml());
			} while (sb.length() <= len);

			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean contains(String str, String search) {
		if ((org.apache.commons.lang.StringUtils.isBlank(str))
				|| (org.apache.commons.lang.StringUtils.isBlank(search))) {
			return false;
		}
		String reg = org.apache.commons.lang.StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	public static boolean containsKeyString(String str) {
		if (org.apache.commons.lang.StringUtils.isBlank(str)) {
			return false;
		}

		return ((str.contains("'")) || (str.contains("\"")) || (str.contains("\r")) || (str.contains("\n"))
				|| (str.contains("\t")) || (str.contains("\b")) || (str.contains("\f")));
	}

	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n")
					.replace("\t", "\\t").replace("\b", "\\b").replace("\f", "\\f");
		}
		return str;
	}

	public static String getSuffix(String str) {
		int splitIndex = str.lastIndexOf(".");
		return str.substring(splitIndex + 1);
	}

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}