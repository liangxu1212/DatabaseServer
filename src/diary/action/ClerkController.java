package diary.action;

import com.alibaba.fastjson.JSONObject;
import diary.bean.Checks;
import diary.bean.Clerks;
import diary.dao.CheckDAO;
import diary.dao.ClerksDAO;
import diary.dao.SysArgDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MSI on 2018/1/4.
 */
@Controller
@RequestMapping(value = "clerk")
public class ClerkController {
    private ClerksDAO clerksDAO;
    private CheckDAO checkDAO;
    private SysArgDAO sysArgDAO;
    @Resource
    public void setSysArgDAO(SysArgDAO sysArgDAO){this.sysArgDAO=sysArgDAO;}
    @Resource
    public void setCheckDAO(CheckDAO checkDAO){this.checkDAO=checkDAO;}
    @Resource
    public void setClerksDAO(ClerksDAO clerksDAO){this.clerksDAO=clerksDAO;}

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("login");
        String id=request.getParameter("id");
        String password=request.getParameter("password");

        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        System.out.println(id+password);
        if(id==null||password==null){
            System.out.println("more args");
            jsonObject.put("status",400);
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        Clerks clerks=clerksDAO.findById(id);

        if(clerks==null||!clerks.getPassword().equals(password)){
            System.out.println("miss");
            jsonObject.put("status",400);
            writer.write(jsonObject.toJSONString());
            writer.flush();
        }
        else {
            jsonObject.put("data",clerks);
            jsonObject.put("status",200);
            writer.write(jsonObject.toJSONString());
            writer.flush();
        }
    }
    @RequestMapping(value = "checkin",method = RequestMethod.POST)
    public void checkin(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("checkin");
        String id=request.getParameter("id");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();

        if(id==null){
            jsonObject.put("status",400);
            jsonObject.put("message","no id");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        Clerks clerks=clerksDAO.findById(id);
        if(clerks==null){
            jsonObject.put("status",400);
            jsonObject.put("message","no clerk");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        SimpleDateFormat fullsdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat daysdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        String from=daysdf.format(d)+" 00:00:00";
        String to=daysdf.format(d)+" 23:59:59";
        if(checkDAO.listCheck(id,null,null,"0",from,to).size()!=0){
            jsonObject.put("status",300);
            jsonObject.put("message","already checkin");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        String time=sysArgDAO.findByCategory("checkin").getArgContent();
        time=daysdf.format(d)+" "+time;
        int state;
        System.out.println(time);
        System.out.println(fullsdf.format(d));
        if(time.compareTo(fullsdf.format(d))>0){
            state=1;
            jsonObject.put("message","on time");
        }else{
            state=0;
            jsonObject.put("message","late");
        }
        Checks check=new Checks();
        check.setCheckTime(d);
        check.setCategory(0);
        check.setClerkId(Integer.parseInt(id));
        check.setState(state);
        checkDAO.attachDirty(check);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value = "checkout",method = RequestMethod.POST)
    public void checkout(HttpServletRequest request ,HttpServletResponse response) throws ParseException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("checkin");
        String id=request.getParameter("id");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();

        if(id==null){
            jsonObject.put("status",400);
            jsonObject.put("message","no id");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        Clerks clerks=clerksDAO.findById(id);
        if(clerks==null){
            jsonObject.put("status",400);
            jsonObject.put("message","no clerk");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        SimpleDateFormat fullsdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat daysdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d=new Date();
        String from=daysdf.format(d)+" 00:00:00";
        String to=daysdf.format(d)+" 23:59:59";
        if(checkDAO.listCheck(id,null,null,"1",from,to).size()!=0){
            jsonObject.put("status",300);
            jsonObject.put("message","already checkout");
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        String time=sysArgDAO.findByCategory("checkout").getArgContent();
        time=daysdf.format(d)+" "+time;
        int state;
        System.out.println(time);
        System.out.println(fullsdf.format(d));
        if(time.compareTo(fullsdf.format(d))<0){
            state=1;
            jsonObject.put("message","on time");
        }else{
            state=0;
            jsonObject.put("message","early");
        }
        Checks check=new Checks();
        check.setCheckTime(d);
        check.setCategory(1);
        check.setClerkId(Integer.parseInt(id));
        check.setState(state);
        checkDAO.attachDirty(check);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
}
