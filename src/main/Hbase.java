package main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kp on 16/8/14.
 */
public class Hbase {

        public static void createTable(Configuration configuration,String tableName , ArrayList<String> list){
            HBaseAdmin admin;
            try {
                admin = new HBaseAdmin(configuration);
                if(admin.tableExists(tableName)){

                    System.out.println("the "+tableName+"is exist ,please check");

                }else{
                HTableDescriptor tableDescriptor=new HTableDescriptor(TableName.valueOf(tableName));

                for(String Descriptor: list){
                    tableDescriptor.addFamily(new HColumnDescriptor(Descriptor));
                }
                    tableDescriptor.addFamily(new HColumnDescriptor("Summary"));
                    admin.createTable(tableDescriptor);
                System.out.println("end create table");

                }
            } catch (MasterNotRunningException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.err.print("please check your zookeeper!!!!!!+1111111111111111");
            } catch (ZooKeeperConnectionException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
                System.err.print("please check your zookeeper!!!!!!");
            }
            catch (IOException e) {
                //  TODO Auto-generated catch block
                e.printStackTrace();
                System.err.print("please check your zookeeper!!!!!!+222222222222222222222");
            }
        }

        /**
         * Delete the existing table
         * @param configuration Configuration
         * @param tableName String,Table's name
         * */
        public static void dropTable(Configuration configuration,String tableName){
            HBaseAdmin admin;
            try {
                admin = new HBaseAdmin(configuration);
                if(admin.tableExists(tableName)){
                    admin.disableTable(tableName);
                    admin.deleteTable(tableName);
                    System.out.println(tableName+"delete success!");
                }else{
                    System.out.println(tableName+"Table does not exist!");
                }
            } catch (MasterNotRunningException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ZooKeeperConnectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * insert a data
         * @param configuration Configuration
         * @param tableName String,Table's name
         * */
        public static void addData(Configuration configuration,String tableName,String rowkey,String family,String column,String value){
            HBaseAdmin admin;
            try {
                admin = new HBaseAdmin(configuration);
                if(admin.tableExists(tableName)){
                    @SuppressWarnings("resource")
                    HTable table=new HTable(configuration, tableName);
                    Put put=new Put(Bytes.toBytes(rowkey));
                    put.add(Bytes.toBytes(family), Bytes.toBytes(column), Bytes.toBytes(value));
                    table.put(put);
                    //System.out.println("add success!");
                }else{
                    System.out.println(tableName+"Table does not exist!");
                }
            } catch (MasterNotRunningException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ZooKeeperConnectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * Delete a data
         * @param configuration Configuration
         * @param tableName String,Table's name
         * @throws IOException
         * */
        @SuppressWarnings("resource")
        public static void deleteDate(Configuration configuration,String tableName) throws IOException{
            HBaseAdmin admin;
            //new Read_txt();
            int sum=0;
//		  ArrayList<String> del=Read_txt.read();
//		  del.add("Vernons");
            try {
                admin=new HBaseAdmin(configuration);
                if(admin.tableExists(tableName)){
//			   for(int i=0;i<del.size();i++){
                    HTable table=new HTable(configuration, tableName);
                    Delete delete=new Delete(Bytes.toBytes("108474"));
                    delete.deleteFamily(Bytes.toBytes("future"));
//		   delete.deleteFamily(Bytes.toBytes("yazhi"));
                    table.delete(delete);
                    sum++;
                    System.out.println("delete success!");
//		    }
                }else{
                    System.out.println("Table does not exist!");
                }
            } catch (MasterNotRunningException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ZooKeeperConnectionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//		  System.out.println(sum);
        }


        /**
         * get a data
         * @param configuration Configuration
         * @param tableName String,Table's name
         * @throws IOException
         * */
        public static void getData_rowkey(Configuration configuration,String tableName, String rowkey) throws IOException{
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String>  list1 = new ArrayList<String>();
            HTable table;
            String str = null;
            //new Put_txt();

            try {
                table = new HTable(configuration, tableName);
                Get get=new Get(Bytes.toBytes(rowkey));
                Result result=table.get(get);
                for(Cell cell:result.rawCells()){
                    System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");
                    System.out.println("Timetamp:"+cell.getTimestamp()+" ");
                    System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");
                    System.out.println("row Name:"+new String(CellUtil.cloneQualifier(cell))+" ");
                    System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");
                    str=new String(CellUtil.cloneValue(cell));
                    list.add(str);
                }
                // Put_txt.out_txt(list," ");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        /**
         * get a data
         * @param configuration Configuration
         * @param tableName String,Table's name
         * @throws IOException
         * */
        /*
        public static void getData_fimaly(Configuration configuration,String tableName, String txtname,String family) throws IOException{

            ArrayList<String>  rd_list = new ArrayList<String>();
            ArrayList<String>  list1 = new ArrayList<String>();
            HTable table;
            String str_con = null;
            String rowkey = null;
            new Read_txt();
            ArrayList<String> listrow =Read_txt.read(txtname);
            new Put_txt();
            table = new HTable(configuration, tableName);

            try {

                for(int i=0;i<listrow.size();i++){

                    str_con=null;
                    String key = listrow.get(i).split("null")[0];
                    Get get=new Get(Bytes.toBytes(key));
                    get.addFamily(Bytes.toBytes(family));
                    get.addColumn(Bytes.toBytes("shuju"), Bytes.toBytes("advice"));
                    Result result=table.get(get);

                    for(Cell cell:result.rawCells()){

                        str_con=str_con+new String(CellUtil.cloneQualifier(cell))+":"+new String(CellUtil.cloneValue(cell))+";  ";
                        rowkey = new String(CellUtil.cloneRow(cell));
                    }

                    str_con = rowkey + str_con;
                    rd_list.add(str_con);

                }

                System.out.println(rd_list);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
*/

        /**
         * insert all data
         * @param configuration Configuration
         * @param tableName String,Table's name
         * @throws IOException
         * @throws SQLException
         * */
        public static void getAllData(Configuration configuration,String tableName){
            HTable table;
    //        new Put_txt();
            String str = null;
            String newvalue = null;
            try {
                table=new HTable(configuration, tableName);
                Scan scan=new Scan();
                ResultScanner results=table.getScanner(scan);
                for(Result result:results){
                    for(Cell cell:result.rawCells()){
                        //    str = "RowName:"+new String(CellUtil.cloneRow(cell))+" "+"column Family:"+new String(CellUtil.cloneFamily(cell))+" "+"row "
                        //  		+ "Name:"+new String(CellUtil.cloneQualifier(cell))+" "+"value:"+new String(CellUtil.cloneValue(cell))+" ";
                        if(new String(CellUtil.cloneFamily(cell)).equals("airbnb_commentnum")){
                            if((newvalue=new String(CellUtil.cloneValue(cell))).equals(null)){
                                newvalue="0";
                            }else{
                                newvalue = newvalue.replace("(", "").replace(")", "");
                            }
      //                      Put_txt.out_txtsim(new String(CellUtil.cloneRow(cell)).split("\\$")[1]+"+"+newvalue, "3121test");
                        }

                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*
         * add a column family
         */
        public static void addfamily(Configuration conf,String tablename)throws Exception{
            HBaseAdmin admin = new HBaseAdmin(conf);
            HTable table = new HTable(conf,tablename);
            HTableDescriptor descriptor = new HTableDescriptor(table.getTableDescriptor());
            descriptor.addFamily(new HColumnDescriptor(Bytes.toBytes("1234")));
            admin.disableTable(tablename);
            admin.modifyTable(Bytes.toBytes(tablename), descriptor);
            admin.enableTable(tablename);
            Put put = new Put(Bytes.toBytes("row2"));
            put.add(Bytes.toBytes("cf3"), Bytes.toBytes("column"), Bytes.toBytes("3"));
            table.put(put);
            table.close();
            admin.close();
        }


        public static void deleteRow(Configuration conf,String tablename, String rowkey)  {
            try {
                @SuppressWarnings("resource")
                HTable table = new HTable(conf, tablename);
                List<Delete> list = new ArrayList<Delete>();
                Delete d1 = new Delete(rowkey.getBytes());
                list.add(d1);

                table.delete(list);
                System.out.println("删除行成功!");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


