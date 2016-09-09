package main;

import org.json.JSONArray;
import org.json.JSONObject;
import sun.security.util.HostnameChecker;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by kp on 16/5/2.
 *
 * function I/O流数据传输的工具箱
 *
 */
public class Dataget {

        /**
         * 获取网路数据
         *
         * @path 是网络数据中的url
         *
         * */
    String  getHtml(String path,String form) throws Exception{
        String content = "";
        String temp;
        URL url = new URL(path);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");

        urlConnection.setRequestProperty("Cookie", "Q8qA_2132_saltkey=KeP5qAJM; Q8qA_2132_lastvisit=1473072517; " +
                "Q8qA_2132_lastcheckfeed=194388%7C1473076153; Q8qA_2132_auth=a7bdwlIQ0P7laNRYsav7BU0nhZHin4r14WcIksdQDMImMAG57CC6I07wlsOca4t5v1GGwiGsH69xfVWQNhK1jYRN0MQ; " +
                "Q8qA_2132_nofavfid=1; Q8qA_2132_myrepeat_rr=R0; Q8qA_2132_home_diymode=1; " +
                "Q8qA_2132_lip=10.175.221.142%2C1473305245; " +
                "Q8qA_2132_ulastactivity=5a6a74XXbwbLi2HutSuYmtjKbrrYbTBtm9txrZUJks2IvI5MeUTz; " +
                "Q8qA_2132_visitedfid=72D551D555D217D110D554D108D157D216D21; " +
                "Q8qA_2132_st_p=194388%7C1473383107%7Ca2a641520d04d5c45e42814f71a57ce5; " +
                "Q8qA_2132_viewid=tid_807310; Q8qA_2132_sid=Ufr64R; Q8qA_2132_smile=1D1; ");
        if(form.equals("GET")){
            urlConnection.setRequestMethod("GET");
        }else if(form.equals("POST")){
            //这里面主要以睿思为例说明
            String pathcontent = "mod=post&action=reply&fid=72&tid=807310&extra=&replysubmit=yes&infloat=yes&handlekey=fastpost&inajax=1&message=%E5%B8%AE%E9%A1%B6%E4%B8%80%E4%B8%8B%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E3%80%82%E6%B0%B4%E6%B0%B4%E7%BB%8F%E9%AA%8C&posttime=1473386718&formhash=c1c47fba&usesig=1&subject=++";
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(pathcontent.getBytes());
        }
        
        //获取返回的数据
        BufferedReader Rbuff = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        while((temp=Rbuff.readLine())!=null){
            content = content +temp+"\n";
        }
        return content;
    }

    //一次性将将数据写入文件
    String writeFile(String path,String content) throws Exception{

         File file = new File(path);
         FileWriter fileWrite = new FileWriter(file,true);
         fileWrite.write(content);

        return "";
    }

    //持续的将数据写入文件
     void FileWrite2 (ArrayList<String> list,String name) throws Exception {
        File file = new File("/Users/kp/Desktop/" + name + ".txt");
        FileWriter filewrite = new FileWriter(file, true);
        for (int i = 0; i < list.size(); i++) {
            filewrite.write(list.get(i) + "\n");
        }
        filewrite.flush();
        filewrite.close();
    }

        //读取文件数据
    String readFile(String path) throws Exception{
        int i = 0;
        String finalText = "";
        String text = "";
        File file =new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((text = bufferedReader.readLine())!=null){
            finalText = finalText + "\n"+text;
            i++;
        }
        System.out.println(i);
        return finalText;
    }

    /**
    * JSON数据的解析
    * */
    void JsonParse(String content) throws Exception{

        ArrayList<String> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(content);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getJSONObject(i).getString("id"));
        }
        Date date = new Date();
        int mounth = date.getMonth();
        int day = date.getDate();
        int hour = date.getHours();
        if(list.size()!=0){
            FileWrite2(list, mounth + 1 + "-" + day + "-" + hour);
        }
        list.clear();
    }
}
