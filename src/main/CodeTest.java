package main;

import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by kp on 16/9/5.
 */
public class CodeTest extends TestCase {
    /**
     * 代码回帖最大的感触就是虽然网页是进入的某一个帖子，但是post的数据可以是另外的帖子，要看表单中是否包含这个帖子id
     * */
    //post表单提交的形式是code=***&name=***
    public void testCode() throws Exception{
           Dataget dataget =  new Dataget();
        String str = dataget.getHtml("http://rs.xidian.edu.cn/forum.php?mod=viewthread&tid=807329","POST");
        System.out.println(str);

    }
}
