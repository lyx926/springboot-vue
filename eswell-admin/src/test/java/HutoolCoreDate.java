import cn.hutool.core.date.*;
import cn.hutool.core.lang.Console;

import java.util.Calendar;
import java.util.Date;

/**
 * 糊涂日期工具类
 */
public class HutoolCoreDate {
    public static void main(String[] args) {

//        日期时间工具-DateUtil
//        特别赞助 by：
//
//        由来
//        考虑到Java本身对日期时间的支持有限，并且Date和Calendar对象的并存导致各种方法使用混乱和复杂，故使用此工具类做了封装。这其中的封装主要是日期和字符串之间的转换，以及提供对日期的定位（一个月前等等）。
//
//        对于Date对象，为了便捷，使用了一个DateTime类来代替之，继承自Date对象，主要的便利在于，覆盖了toString()方法，返回yyyy-MM-dd HH:mm:ss形式的字符串，方便在输出时的调用（例如日志记录等），提供了众多便捷的方法对日期对象操作，关于DateTime会在相关章节介绍。
//
//        方法
//                转换
//        Date、long、Calendar之间的相互转换
//当前时间
        Date date = DateUtil.date();
//当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
//当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
//当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
//当前日期字符串，格式：yyyy-MM-dd
        String today = DateUtil.today();
//        Copy to clipboardErrorCopied
//                字符串转日期
//        DateUtil.parse方法会自动识别一些常用格式，包括：

//        yyyy-MM-dd HH:mm:ss
//
//        yyyy/MM/dd HH:mm:ss
//        yyyy.MM.dd HH:mm:ss
//        yyyy年MM月dd日 HH时mm分ss秒
//        yyyy-MM-dd
//        yyyy/MM/dd
//        yyyy.MM.dd
//        HH:mm:ss
//                HH时mm分ss秒
//        yyyy-MM-dd HH:mm
//        yyyy-MM-dd HH:mm:ss.SSS
//                yyyyMMddHHmmss
//        yyyyMMddHHmmssSSS
//                yyyyMMdd
//        EEE, dd MMM yyyy HH:mm:ss z
//        EEE MMM dd HH:mm:ss zzz yyyy
//        yyyy-MM-dd'T'HH:mm:ss'Z'
//        yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
//        yyyy-MM-dd'T'HH:mm:ssZ
//        yyyy-MM-dd'T'HH:mm:ss.SSSZ
        String dateStr000 = "2017-03-01";
        Date date000 = DateUtil.parse(dateStr000);
//        Copy to clipboardErrorCopied
//        我们也可以使用自定义日期格式转化：

        String dateStr111 = "2017-03-01";
        Date date111 = DateUtil.parse(dateStr111, "yyyy-MM-dd");
//        Copy to clipboardErrorCopied
//                格式化日期输出
        String dateStr222 = "2017-03-01";
        Date date222 = DateUtil.parse(dateStr222);

//结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");

//常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);

//结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);

//结果：00:00:00
        String formatTime = DateUtil.formatTime(date);
//        Copy to clipboardErrorCopied
//                获取Date对象的某个部分
        Date date2221 = DateUtil.date();
//获得年的部分
        DateUtil.year(date2221);
//获得月份，从0开始计数
        DateUtil.month(date2221);
//获得月份枚举
        DateUtil.monthEnum(date2221);
//.....
//        Copy to clipboardErrorCopied
//                开始和结束时间
//        有的时候我们需要获得每天的开始时间、结束时间，每月的开始和结束时间等等，DateUtil也提供了相关方法：

        String dateStr333 = "2017-03-01 22:33:23";
        Date date333 = DateUtil.parse(dateStr333);

//一天的开始，结果：2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);

//一天的结束，结果：2017-03-01 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);
//        Copy to clipboardErrorCopied
//                日期时间偏移
//        日期或时间的偏移指针对某个日期增加或减少分、小时、天等等，达到日期变更的目的。Hutool也针对其做了大量封装

        String dateStr444 = "2017-03-01 22:33:23";
        Date date444 = DateUtil.parse(dateStr444);

//结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);

//常用偏移，结果：2017-03-04 22:33:23
        DateTime newDate2 = DateUtil.offsetDay(date, 3);

//常用偏移，结果：2017-03-01 19:33:23
        DateTime newDate3 = DateUtil.offsetHour(date, -3);
//        Copy to clipboardErrorCopied
//        针对当前时间，提供了简化的偏移方法（例如昨天、上周、上个月等）：

//昨天
        DateUtil.yesterday();
//明天
        DateUtil.tomorrow();
//上周
        DateUtil.lastWeek();
//下周
        DateUtil.nextWeek();
//上个月
        DateUtil.lastMonth();
//下个月
        DateUtil.nextMonth();
//        Copy to clipboardErrorCopied
//                日期时间差
//        有时候我们需要计算两个日期之间的时间差（相差天数、相差小时数等等），Hutool将此类方法封装为between方法：

        String dateStr1111 = "2017-03-01 22:33:23";
        Date date1111 = DateUtil.parse(dateStr1111);

        String dateStr2222 = "2017-04-01 23:33:23";
        Date date2222 = DateUtil.parse(dateStr2222);

//相差一个月，31天
        long betweenDay = DateUtil.between(date1111, date2222, DateUnit.DAY);
//        Copy to clipboardErrorCopied
//                格式化时间差
//        有时候我们希望看到易读的时间差，比如XX天XX小时XX分XX秒，此时使用DateUtil.formatBetween方法：

//Level.MINUTE表示精确到分
        String formatBetween = DateUtil.formatBetween(betweenDay, BetweenFormater.Level.MINUTE);
//输出：31天1小时
        Console.log(formatBetween);
//        Copy to clipboardErrorCopied
//                星座和属相
// "摩羯座"
        String zodiac = DateUtil.getZodiac(Month.JANUARY.getValue(), 19);

// "狗"
        String chineseZodiac = DateUtil.getChineseZodiac(1994);
//        Copy to clipboardErrorCopied
//                其它
//年龄
        DateUtil.ageOfNow("1990-01-30");

//是否闰年
        DateUtil.isLeapYear(2017);

    }
}
