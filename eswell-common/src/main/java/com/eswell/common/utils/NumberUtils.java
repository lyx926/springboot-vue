package com.eswell.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 字符串工具类
 *
 * @author ruoyi
 */
public class NumberUtils extends org.apache.commons.lang3.StringUtils {
    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";


    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     *
     * @param amount 报销金额
     * @return
     * @author
     * @version 1.00.00
     * @date 2022年12月09日
     */
    public static String amountConversion(double amount) {
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        if (amount <100000) {
            result = result.substring(0, 1);
            return result.replaceAll("\\.00", "");
        }
        if (amount <1000000) {
            result = result.substring(0,2);
            return result.replaceAll("\\.00", "");
            /*tempValue = amount / MILLION;
            remainder = amount % MILLION;
            System.out.println("tempValue=" + tempValue + ", remainder=" + remainder);

            //余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 0, false);
            } else {
                value = formatNumber(tempValue, 0, true);
            }
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION);
//                        + BILLION_UNIT;
            } else {
                result = zeroFill(value);
//                        + MILLION_UNIT;
            }*/
        }

        //金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;
            System.out.println("tempValue=" + tempValue + ", remainder=" + remainder);

            //余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 0, false);
            } else {
                value = formatNumber(tempValue, 0, true);
            }
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION);
//                        + BILLION_UNIT;
            } else {
                result = zeroFill(value);
//                        + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;
            System.out.println("tempValue=" + tempValue + ", remainder=" + remainder);

            //余数小于50000000则不进行四舍五入
            if (remainder < (BILLION / 2)) {
                value = formatNumber(tempValue, 0, false);
            } else {
                value = formatNumber(tempValue, 0, true);
            }
            result = zeroFill(value).replaceAll("\\.", "") + "00";
//                    + BILLION_UNIT;
        } else {
            result = zeroFill(amount);
        }
        System.out.println("result=" + result);
        return result.replaceAll("\\.00", "");
    }


    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number   要四舍五入的数字
     * @param decimal  保留的小数点数
     * @param rounding 是否四舍五入
     * @return
     * @author
     * @version 1.00.00
     * @date 2022年12月09日
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月23日
     */
    public static String zeroFill(double number) {
        String value = String.valueOf(number);

        if (value.indexOf(".") < 0) {
            value = value + ".00";
        } else {
            String decimalValue = value.substring(value.indexOf(".") + 1);

            if (decimalValue.length() < 2) {
                value = value + "0";
            }
        }
        return value;
    }

    /**
     * 测试方法入口
     *
     * @param args
     * @author
     * @version 1.00.00
     * @date 2022年12月09日
     */
    public static void main(String[] args) throws Exception {
//        amountConversion(120);
//        amountConversion(18166.35);
//        amountConversion(1222188.35);
//        amountConversion(129887783.5);
        String s = amountConversion(123456789);
        System.out.println("结果 - " + s);
    }
}
