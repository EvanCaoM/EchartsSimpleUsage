package com.imooc.dao;

import com.imooc.domain.DayVideoAccessTopNStat;
import com.imooc.utils.MySQLUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DayVideoAccessTopNStatDAO {

    private static Connection connection;
    private static PreparedStatement pstmt;

    public List<DayVideoAccessTopNStat> query() throws SQLException {
        List<DayVideoAccessTopNStat> list = new ArrayList<DayVideoAccessTopNStat>();
        try {
            connection = MySQLUtils.getConnection();
            String sql = "select cms_id,times from day_video_access_topn_stat";
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            list = toList(rs, DayVideoAccessTopNStat.class);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            MySQLUtils.release(connection, pstmt);
        }
//        while (rs.next()){
//            DayVideoAccessTopNStat dayVideoAccessTopNStat = new DayVideoAccessTopNStat();
//            dayVideoAccessTopNStat.setDay(rs.getString(1));
//            dayVideoAccessTopNStat.setCmsId(rs.getLong(2));
//            dayVideoAccessTopNStat.setTimes(rs.getLong(3));
//            list.add(dayVideoAccessTopNStat);
//        }
        return list;
    }

    public static ArrayList toList(ResultSet rs,Class cls)
    {
        try
        {
            ArrayList lst=new ArrayList();
            //用于获取列数、或者列类型
            ResultSetMetaData meta=rs.getMetaData();
            Object obj=null;
            while(rs.next())
            {
                //获取formbean实例对象
                obj=Class.forName(cls.getName()).newInstance();              //用Class.forName方法实例化对象和new创建实例化对象是有很大区别的，它要求JVM首先从类加载器中查找类，然后再实例化，并且能执行类中的静态方法。而new仅仅是新建一个对象实例
                //循环获取指定行的每一列的信息
                for(int i=1;i<=meta.getColumnCount();i++)
                {
                    //当前列名
                    String colName=meta.getColumnName(i);
                    //将列名第一个字母大写（为什么加+""ne ?是大写字母比小写字母多个字节？）
                    colName=colName.replace(colName.charAt(0)+"", new String(colName.charAt(0)+"").toUpperCase());
                    //设置方法名
                    String methodName="set"+colName;
                    System.out.println(methodName);
                    //获取当前位置的值，返回Object类型
                    Object value=rs.getObject(i);
                    //利用反射获取对象
                    Method method=obj.getClass().getMethod(methodName, value.getClass());
                    method.invoke(obj, value);                 //感觉这段类似于obj.setMethodName(value)......对于静态方法的反射可以写成method.invoke(null,value),而不能把第一个参数省略，如果方法没有参数的话，第二个参数可以为空
                }
                lst.add(obj);
            }
            return lst;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) throws SQLException {
        DayVideoAccessTopNStatDAO dayVideoAccessTopNStatDAO = new DayVideoAccessTopNStatDAO();
        List<DayVideoAccessTopNStat> query = dayVideoAccessTopNStatDAO.query();
    }
}
