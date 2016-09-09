package main;



import main.Const.Url;
import org.apache.hadoop.hbase.HBaseConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class GridMatchTest{
	/*
	* 如果是本地输入,使用Jsoup.parse或者Jsoup.parseBodyFragment();
	* 如果是网络输入,使用Jsoup.connect连接到网络,然后使用get方法获取到一个Document.
	* 使用相应的方法如下列post(),的方式可以获取https下面的数据
	* 这样就可以获取到Document,然后可以跟居其属性获取到elements或者element.
	* 在element中可以根据其属性获取到相应的element或者是String.
	* 这个select 方法在Document, Element,或Elements中都可以进行使用
	*
	* */
	/**
	 * 构建出最基本的所需要的对象
	 * */
	static Dataget target = new Dataget();
	static Hbase hbase = new Hbase();
	static ArrayList<String> list = new ArrayList<String>();
	private static String id = "";
	static HBaseConfiguration configuration = null;
	static {
			configuration = new HBaseConfiguration();
			configuration.set("hbase.zookeeper.quorum","127.0.0.1");
			configuration.set("hbase.rootdir","hdfs://127.0.0.1:9000");
			configuration.set("hbase.master","hdfs://master:60000");
	}



	public static void main(String args[]) throws Exception{


		/**
		 *获取所需要爬去的连接
		 * **/
		for (int i = 0; i < Url.id.length; i++) {
		//	System.out.println(Url.id[i]);
			String url = Url.urlstock + Url.id[i]+"1";
			//functionCreateTable(url,Url.id[i]);
			functionAddData(url, Url.id[i]);
			}
		}


	static void functionCreateTable(String url,String tablename) throws Exception{

		String content = target.getHtml(url,"GET");
		String[] str = content.split("\\n");

		for (int i = 0; i < str.length-1; i++) {

			String[] infoStr = str[i].split("\\,");
			String time = infoStr[0];
			time = time.replace(":","$");
			list.add(time);
		}
		hbase.createTable(configuration, tablename, list);
		list.clear();
	}


	static void functionAddData(String url,String tablename) throws Exception{

		String content = target.getHtml(url,"GET");
		//System.out.print(content);

		/**
		 * 获取此时刻的日期
		 * */
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		String rowkey = Integer.toString(year)+"$"+Integer.toString(month)+"$"+Integer.toString(day);
		//System.out.println(rowkey);

		String[] str = content.split("\\n");

		for (int i = 0; i < str.length-1; i++) {

			String[] infoStr = str[i].split("\\,");
			String time = infoStr[0];
			time = time.replace(":","$");
			String Temprice = infoStr[1];
			String CountDeal = infoStr[2];
			String priceDeal = infoStr[3];
			String averagePrice = infoStr[4];
			String TempPara1 = infoStr[5];
			String TempPara2 = infoStr[6];
			//System.out.println(time+"$"+Temprice+"$"+CountDeal+"$"+priceDeal+"$"+averagePrice+"$"+TempPara1+"$"+TempPara2);
			hbase.addData(configuration,tablename,rowkey,time,"Temprice",Temprice);
			hbase.addData(configuration,tablename,rowkey,time,"CountDeal",CountDeal);
			hbase.addData(configuration,tablename,rowkey,time,"priceDeal",priceDeal);
			hbase.addData(configuration,tablename,rowkey,time,"averagePrice",averagePrice);
			hbase.addData(configuration,tablename,rowkey,time,"TempPara1",TempPara1);
			hbase.addData(configuration, tablename, rowkey, time, "TempPara2", TempPara2);

			System.out.println("------------------------"+Thread.activeCount());

		}

		String[] infoStr = str[str.length-1].split("\\,");
		String Temprice = infoStr[1];
		String CountDeal = infoStr[2];
		String priceDeal = infoStr[3];
		String averagePrice = infoStr[4];
		String highprice = infoStr[5];
		String lowprice = infoStr[6];
		String Todaystart = infoStr[7];
		String yesterdayPrice = infoStr[8];

		hbase.addData(configuration,tablename,rowkey,"Summary","Temprice",Temprice);
		hbase.addData(configuration,tablename,rowkey,"Summary","CountDeal",CountDeal);
		hbase.addData(configuration,tablename,rowkey,"Summary","priceDeal",priceDeal);
		hbase.addData(configuration,tablename,rowkey,"Summary","averagePrice",averagePrice);
		hbase.addData(configuration,tablename,rowkey,"Summary","highprice",highprice);
		hbase.addData(configuration,tablename,rowkey,"Summary","lowprice",lowprice);
		hbase.addData(configuration,tablename,rowkey,"Summary","Todaystart",Todaystart);
		hbase.addData(configuration,tablename,rowkey,"Summary","yesterdayPrice",yesterdayPrice);

		}

	}