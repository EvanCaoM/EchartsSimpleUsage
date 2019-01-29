package com.imooc.spark;

import com.imooc.dao.DayVideoAccessTopNStatDAO;
import com.imooc.domain.DayVideoAccessTopNStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

@RestController
public class DayVideoAccessTopNStatApp {
    @Autowired
    DayVideoAccessTopNStatDAO dayVideoAccessTopNStatDAO;

    @RequestMapping(value = "queryDayVideoAccessTopNStat", method = RequestMethod.POST)
    public List<DayVideoAccessTopNStat> queryDayVideoAccessTopNStat() throws SQLException {
        List<DayVideoAccessTopNStat> list = dayVideoAccessTopNStatDAO.query();
        return list;
    }


    @RequestMapping(value = "/dayVideoAccessTopNStat", method = RequestMethod.GET)
    public ModelAndView echarts(){
        return new ModelAndView("dayVideoAccessTopNStat");
    }
}
