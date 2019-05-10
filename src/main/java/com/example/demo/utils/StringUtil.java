package com.example.demo.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

/**
 * 常用字符串操作工具类
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 返回当前年
     */
    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(1));
    }

    /**
     * 返回当前月
     */
    public static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        String temp = String.valueOf(calendar.get(2) + 1);
        if (temp.length() < 2) {
            temp = "0" + temp;
        }
        return temp;
    }

    /**
     * $&[]{}' 检查输入项是否有不合法字符 返回false代表输入字符不合法，反之相反
     *
     * @param str string
     */
    public static boolean checkStr(String str) {
        String strtmp = "%\\/()><;#-";
        boolean bl = true;
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (strtmp.indexOf(str.charAt(i)) > -1) {
                    bl = false;
                    break;
                }
            }
        } else {
            bl = false;
        }
        return bl;

    }

    /**
     * 空值转化
     *
     * @param value
     * @return 返回判断结果
     */
    public static String getNullToEmpty(String value) {
        return StringUtil.isNotEmpty(value) ? "" : value;
    }

    /**
     * 按长度分割字符串
     *
     * @param content 字符串
     * @param len     长度
     * @return 结果集
     */
    public static String[] split(String content, int len) {
        if (content == null || "".equals(content)) {
            return null;
        }
        int len2 = content.length();
        if (len2 <= len) {
            return new String[]{content};
        } else {
            int i = len2 / len + 1;
            String[] strA = new String[i];
            int j = 0;
            int begin = 0;
            int end = 0;
            while (j < i) {
                begin = j * len;
                end = (j + 1) * len;
                if (end > len2) {
                    end = len2;
                }
                strA[j] = content.substring(begin, end);
                j = j + 1;
            }
            return strA;
        }
    }

    /**
     * 检查邮箱
     *
     * @param email 邮箱地址
     * @return 检查结果
     */
    public static boolean isEmail(String email) {
        boolean retval = false;
        String emailPattern = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+[.]([a-zA-Z0-9_-])+";
        retval = email.matches(emailPattern);
        // String msg = "NO MATCH: pattern:" + email + " regex: " +
        // emailPattern;

        if (retval) {
            // msg = "MATCH : pattern:" + email + " regex: " + emailPattern;
        }
        // System.out.println(msg + " ");
        return retval;
    }

    /**
     * 字节码转换成16进制字符串 内部调试使用
     *
     * @param b 字节码
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 字节码转换成自定义字符串 内部调试使用
     *
     * @param b 字节码
     * @return 字符串
     */
    public static String byte2string(byte[] b) {
        String hs = "";
        String stmp;
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 自定义字符串转换成字节码 内部调试使用
     *
     * @param hs 字符串
     * @return 字节码
     */
    public static byte[] string2byte(String hs) {
        byte[] b = new byte[hs.length() / 2];
        for (int i = 0; i < hs.length(); i = i + 2) {
            String sub = hs.substring(i, i + 2);
            byte bb = new Integer(Integer.parseInt(sub, 16)).byteValue();
            b[i / 2] = bb;
        }
        return b;
    }

    public static boolean empty(String param) {
        return param == null || param.trim().length() < 1;
    }

    /**
     * 是英文字母或数字
     */
    public static boolean isLetterOrDigit(String str) {
        java.util.regex.Pattern p = null; // 正则表达式
        java.util.regex.Matcher m = null; // 操作的字符串
        boolean value = true;
        try {
            p = java.util.regex.Pattern.compile("[^0-9A-Za-z]");
            m = p.matcher(str);
            if (m.find()) {

                value = false;
            }
        } catch (Exception e) {
        }
        return value;
    }

    // 汉字也为true
    public static boolean isLetterorDigit(String s) {
        if ("".equals(s) || s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                // if (!Character.isLetter(s.charAt(i))){
                return false;
            }
        }
        // Character.isJavaLetter()
        return true;
    }

    /**
     * validate a int string
     *
     * @param str the Integer string.
     * @return true if the str is invalid otherwise false.
     */
    public static boolean validateInt(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }

        char c;
        for (int i = 0, l = str.length(); i < l; i++) {
            c = str.charAt(i);
            if (!((c >= '0') && (c <= '9'))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 截取字符串 多余位用 “...” 代替
     *
     * @param str    字符串
     * @param length 长度
     * @return 截取后的字符串
     */
    public static String substring(String str, int length) {
        if (str == null) {
            return null;
        }

        if (str.length() > length) {
            return (str.substring(0, length - 2) + "...");
        } else {
            return str;
        }
    }


    /**
     * delete file
     *
     * @param fileName fileName
     * @return -1 exception,1 success,0 false,2 there is no one directory of the
     * same name in system
     */
    public static int deleteFile(String fileName) {
        File file = null;
        int returnValue = -1;
        try {
            file = new File(fileName);
            if (file.exists()) {
                if (file.delete()) {
                    returnValue = 1;
                } else {
                    returnValue = 0;
                }
            } else {
                returnValue = 2;
            }

        } catch (Exception e) {
            System.out.println("Exception:e=" + e.getMessage());
        } finally {
            file = null;
            // return returnValue;
        }
        return returnValue;
    }

    /**
     * 编码转换 GB2312 To ISO8859-1
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符换
     */
    public static String gbToIso(String s) throws UnsupportedEncodingException {
        return covertCode(s, "GB2312", "ISO8859-1");
    }

    public static String covertCode(String s, String code1, String code2) throws UnsupportedEncodingException {
        if (s == null) {
            return null;
        } else if ("".equals(s.trim())) {
            return "";
        } else {
            return new String(s.getBytes(code1), code2);
        }
    }

    /**
     * 编码转换 GB To UTF-8
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符换
     */
    public static String GBToUTF8(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 2; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                out.append(str);
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                for (int j = str1.length(); j < 2; j++) {
                    out.append("0");
                }

                out.append(str1);
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] replaceAll(String[] obj, String oldString, String newString) {
        if (obj == null) {
            return null;
        }
        int length = obj.length;
        String[] returnStr = new String[length];
        // String str = null;
        for (int i = 0; i < length; i++) {
            returnStr[i] = replaceAll(obj[i], oldString, newString);
        }
        return returnStr;
    }

    public static String replaceAll(String s0, String oldstr, String newstr) {
        if (s0 == null || "".equals(s0.trim())) {
            return null;
        }
        StringBuffer sb = new StringBuffer(s0);
        int begin = 0;
        // int from = 0;
        begin = s0.indexOf(oldstr);
        while (begin > -1) {
            sb = sb.replace(begin, begin + oldstr.length(), newstr);
            s0 = sb.toString();
            begin = s0.indexOf(oldstr, begin + newstr.length());
        }
        return s0;
    }


    /**
     * 替换字符串
     *
     * @param line      字符串
     * @param oldString 需要替换的字符串
     * @param newString 新的字符串
     * @return 替换后的 line
     */
    public static String replace(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 替换字符串 忽略大小写
     *
     * @param line      字符串
     * @param oldString 需要替换的字符串
     * @param newString 新的字符串
     * @return 替换后的 line
     */
    public static String replaceIgnoreCase(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i = 0;
        if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 转义html标签
     * '<','>'
     *
     * @param input html代码
     * @return 转义后的html代码
     */
    public static String escapeHTMLTags(String input) {
        // Check if the string is null or zero length -- if so, return
        // what was sent in.
        if (input == null || input.length() == 0) {
            return input;
        }
        // Use a StringBuffer in lieu of String concatenation -- it is
        // much more efficient this way.
        StringBuffer buf = new StringBuffer(input.length());
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '<') {
                buf.append("&lt;");
            } else if (ch == '>') {
                buf.append("&gt;");
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }


    private static Random randGen = null;
    private static Object initLock = new Object();
    private static char[] numbersAndLetters = null;

    /**
     * 返回一个指定长度的由数字和英文组成的随机字符串
     * Returns a random String of numbers and letters of the specified length.
     * The method uses the Random class that is built-in to Java which is
     * suitable for low to medium grade security uses. This means that the
     * output is only pseudo random, i.e., each number is mathematically
     * generated so is not truly random.
     * <p/>
     * <p/>
     * For every character in the returned String, there is an equal chance that
     * it will be a letter or number. If a letter, there is an equal chance that
     * it will be lower or upper case.
     * <p/>
     * <p/>
     * The specified length must be at least one. If not, the method will return
     * null.
     *
     * @param length the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Init of pseudo random number generator.
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    // Also initialize the numbersAndLetters array
                    numbersAndLetters =
                            ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                                    .toCharArray();
                }
            }
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**
     * 4舍5入
     *
     * @param num 要调整的数
     * @param x   要保留的小数位
     */
    public static double myround(double num, int x) {
        BigDecimal b = new BigDecimal(num);
        return b.setScale(x, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    @SuppressWarnings("unused")
    /**
     * 英文小写字母
     */ private static boolean isLowerLetter(String str) {
        java.util.regex.Pattern p = null; // 正则表达式
        java.util.regex.Matcher m = null; // 操作的字符串
        boolean value = true;
        try {
            p = java.util.regex.Pattern.compile("[^a-z]");
            m = p.matcher(str);
            if (m.find()) {
                value = false;
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 截取指定字符后面的字符串
     *
     * @param str       原始字符串
     * @param character 指定字符
     * @return 截取后的内容
     * @since 1.5
     */
    public static String subStringByChar(String str, String character) {
        return str.substring(str.indexOf(character) + 1);
    }

    /**
     * 三元表达式（非空条件，二选一） <br/>
     *
     * @param strA 字符串A
     * @param strB 字符串B 建议传入非空的字符串
     * @return 如果 字符串A 为空，返回 字符串B，否则返回字符串A
     * @since 1.5
     */
    public static String ternaryAorB(String strA, String strB) {
        return empty(strA) ? strB : strA;
    }

    public static String ternaryA(String strA) {
        return strA;
    }

    /**
     * 不是空的字符串（字符串非空校验）
     *
     * @param str 字符串
     * @return !empty(str)
     * @see StringUtil#empty(String)
     * @since 1.5
     */
    public static boolean isNotEmpty(String str) {
        return !empty(str);
    }

//    /**
//     * 是否是移动电话号码 <br/>
//     * 不严格区分第二位，不区分运营商
//     *
//     * @param phoneNumber 被校验的号码
//     * @return 是移动号码 返回 true，否则返回 false
//     * @see RegexConstant#REGEX_MOBILEPHONE 移动号码正则表达式
//     * @since 1.5
//     */
//    public static boolean isMobilePhone(String phoneNumber) {
//        return phoneNumber.matches(RegexConstant.REGEX_MOBILEPHONE);
//    }

    /**
     * bytesToHexString:(字节转16进制字符串). <br/>
     *
     * @param
     * @return 16进制字符串
     * @author <a href="mailto:sxl_5035@.com">ShiXiaoLei</a>
     */

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}