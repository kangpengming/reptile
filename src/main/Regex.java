package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kp on 16/5/11.
 */
public class Regex {

    /*
		 *对于转译字符需要加入"\"将"\"进行转译,然后才可以对后面的控制字符的字符串进行转译
		 * 对于括号字符组里面的数据是或的关系,"-"是需要范围里面的数据.
		 */
    String Regextest(String html){

        String Rturn = null;
        String content = "(<a([^>]*)href=[\"|\']([^\"\']*)[\"|\']([^>]*)>)";
        Pattern pattern = Pattern.compile(content);
        Matcher matcher = pattern.matcher(html);
        if(matcher.find()){
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("你是一个大傻瓜"+ i + ": " + matcher.group(i));
            }
        }
        return Rturn;
    }
}
