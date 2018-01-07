package diary.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import diary.bean.Checks;
import diary.bean.Clerks;
import diary.bean.Leave;
import diary.bean.Trip;
import diary.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
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
    private TripDAO tripDAO;
    private LeaveDAO leaveDAO;
    @Resource
    public void setTripDAO(TripDAO tripDAO){this.tripDAO=tripDAO;}
    @Resource
    public void setLeaveDAO(LeaveDAO leaveDAO){this.leaveDAO=leaveDAO;}
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
    @RequestMapping(value = "password",method = RequestMethod.POST)
    public void password(HttpServletRequest request,HttpServletResponse response)throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("attachTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        String oldpass=request.getParameter("oldpass");
        String newpass=request.getParameter("newpass");
        Clerks clerks=clerksDAO.findById(id);
        if(!oldpass.equals(clerks.getPassword())){
            jsonObject.put("status",400);
            writer.write(jsonObject.toJSONString());
            writer.flush();
            return;
        }
        clerks.setPassword(newpass);
        clerksDAO.attachDirty(clerks);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value ="info",method = RequestMethod.POST)
    public void info(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("attachTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        Clerks clerks=clerksDAO.findById(id);
        jsonObject.put("data",clerks);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
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
    @RequestMapping(value="listCheck",method=RequestMethod.POST)
    public void listCheck(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("listCheck");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String id=request.getParameter("id");
        String name=request.getParameter("name");
        String department_id=request.getParameter("department_id");
        String from=request.getParameter("from");
        String to=request.getParameter("to");
        ArrayList<Checks> list= (ArrayList<Checks>) checkDAO.listCheck(id,name,department_id,null,from,to);
        JSONArray array=new JSONArray();
        for(Checks checks:list){
            JSONObject json= JSON.parseObject(JSON.toJSONString(checks));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String check = sdf.format(checks.getCheckTime());
            json.put("checkTime",check);
            array.add(json);
        }
        jsonObject.put("data",array);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();

    }
    @RequestMapping(value="attachLeave",method=RequestMethod.POST)
    public void attachLeave(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("attachLeave");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String leaveId=request.getParameter("leave_id");
        String clerkid=request.getParameter("clerk_id");
        String category=request.getParameter("category");
        String content=request.getParameter("content");
        Leave l=new Leave();
        if(leaveId!=null) {
            l = leaveDAO.findLeaveById(leaveId);
        }
        l.setClerkId(Integer.parseInt(clerkid));
        l.setCategory(Integer.parseInt(category));
        l.setContent(content);
        l.setState(1);
        Date d=new Date();
        l.setApplyTime(d);
        l.setUpdateTime(d);
        leaveDAO.attachDirty(l);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
   @RequestMapping(value = "listLeave",method=RequestMethod.POST)
   public void listLeave(HttpServletRequest request,HttpServletResponse response)throws IOException{
       request.setCharacterEncoding("UTF-8");
       response.setContentType("application/json;charset=utf-8");
       response.setCharacterEncoding("utf-8");
       System.out.println("listLeave");
       PrintWriter writer=response.getWriter();
       JSONObject jsonObject=new JSONObject();
       String id=request.getParameter("id");
       String from=request.getParameter("from");
       String to=request.getParameter("to");
       ArrayList<Leave> list= (ArrayList<Leave>) leaveDAO.search(id,from,to);
       JSONArray array=new JSONArray();
       for(Leave leave:list){
           JSONObject json= JSON.parseObject(JSON.toJSONString(leave));
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String apply = sdf.format(leave.getApplyTime());
           String update=sdf.format(leave.getUpdateTime());
           json.put("updateTime",update);
           json.put("applyTime",apply);
           array.add(json);
       }
       jsonObject.put("data",array);
       jsonObject.put("status",200);
       writer.write(jsonObject.toJSONString());
       writer.flush();
   }
   @RequestMapping(value="attachTrip",method=RequestMethod.POST)
   public void attachTrip(HttpServletRequest request,HttpServletResponse response)throws IOException{
       request.setCharacterEncoding("UTF-8");
       response.setContentType("application/json;charset=utf-8");
       response.setCharacterEncoding("utf-8");
       System.out.println("attachTrip");
       PrintWriter writer=response.getWriter();
       JSONObject jsonObject=new JSONObject();
       String tripId=request.getParameter("trip_id");
       String clerkid=request.getParameter("clerk_id");
       String category=request.getParameter("category");
       String content=request.getParameter("content");
       Trip t=new Trip();
       if(tripId!=null)t=tripDAO.findTripById(tripId);
       t.setCategory(Integer.parseInt(category));
       t.setClerkId(Integer.parseInt(clerkid));
       Date d=new Date();
       t.setApplyTime(d);
       t.setUpdateTime(d);
       t.setContent(content);
       tripDAO.attachDirty(t);
       jsonObject.put("status",200);
       writer.write(jsonObject.toJSONString());
       writer.flush();
   }
   @RequestMapping(value = "listTrip",method = RequestMethod.POST)
    public void listTrip(HttpServletRequest request,HttpServletResponse response)throws IOException{
       request.setCharacterEncoding("UTF-8");
       response.setContentType("application/json;charset=utf-8");
       response.setCharacterEncoding("utf-8");
       System.out.println("listTrip");
       PrintWriter writer=response.getWriter();
       JSONObject jsonObject=new JSONObject();
       String id=request.getParameter("id");
       String from=request.getParameter("from");
       String to=request.getParameter("to");
       ArrayList<Trip> list= (ArrayList<Trip>) tripDAO.search(id,from,to);
       JSONArray array=new JSONArray();
       for(Trip trip:list){
           JSONObject json= JSON.parseObject(JSON.toJSONString(trip));
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String apply = sdf.format(trip.getApplyTime());
           String update=sdf.format(trip.getUpdateTime());
           json.put("updateTime",update);
           json.put("applyTime",apply);
           array.add(json);
       }
       jsonObject.put("data",array);
       jsonObject.put("status",200);
       writer.write(jsonObject.toJSONString());
       writer.flush();
   }
}
