package com.program.cherishtime.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author cph
 * @version 1.0
 * @since 20200521
 * <h1>讲汉字转换成其对应的拼音</h1>
 */
//@SuppressWarnings("unused")
public class ChineseToPinyinUtil {

    /**
     * <p>中汉字转换为其对应的小写拼音（不忽略英文字符）</p>
     * @param chinese 中文字符串
     * @return 中文字符对应的拼音字母
     */
    public static String getFullSpell(String chinese) {
        StringBuilder buffer = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    buffer.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * <p>中汉字转换为其对应拼音的小写首字母（不忽略英文字符）</p>
     * @param chinese 中文字符串
     * @return 中文字符对应的拼音字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuilder buffer = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (temp != null) {
                        buffer.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * <p>中汉字转换为其对应的小写拼音（忽略英文字符）</p>
     * @param chinese 中文字符串
     * @return 中文字符对应的拼音字母
     */
    public static String getFullSpellWithoutEnglish(String chinese) {
        StringBuilder buffer = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] arr = chinese.trim().toCharArray();
        for (char ch : arr) {
            if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
                try {
                   buffer.append(PinyinHelper.toHanyuPinyinStringArray(ch, format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
        }

        return buffer.toString();
    }

    /**
     * <p>中汉字转换为其对应拼音的小写首字母（忽略英文字符）</p>
     * @param chinese 中文字符串
     * @return 中文字符对应的拼音字母
     */
    public static String getFirstSpellWithoutEnglish(String chinese) {
        StringBuilder buffer = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if (temp != null) {
                        buffer.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 将字符串的大小写转换
     * @param s 要转换的字符串
     * @return 转换后的字符串
     */
    public static String toggleCase(String s) {
        StringBuilder buffer = new StringBuilder();
        char[] arr = s.toCharArray();
        for (char ch : arr) {
            buffer.append((char)(ch ^ (1 << 5)));
        }
        return buffer.toString();
    }


}
