package main.Const;

/**
 * Created by kp on 16/8/13.
 */
public class Url {
    /**
     * 获取的数据格式说明：
     *
     * 10:44,5.52,43700,241537,5.52,5.53,5.52：
     * （1）时间
     * （2）此时刻的价格
     * （3）成交量
     * （4）成交额
     * （5）均价
     * （6）kp
     *  (7)kp
     *
     *
     * 最后一个数据是最后交易之后的信息：
     * 时间点15：00不变
     * 最新价格
     * 总手数
     * 金额
     * 均价
     * 最高
     * 最低
     * 今开
     * 昨收
     * 今日的日期
     *
     * */
    public static String urlstock = "http://hq2fls.eastmoney.com/EM_Quote2010PictureApplication/Flash.aspx?Type=CR&ID=";
    //万科A，华电能源，ST新集，宝泰隆
    public static String[] id = {"000002"};
    //public static String[] id = {"601918"};
   //public static String[] id = {"601011"};
}
