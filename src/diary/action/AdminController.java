package diary.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import diary.bean.*;
import diary.dao.*;
import org.json.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MSI on 2018/1/4.
 */
@Controller
@RequestMapping(value = "clerk")
public class AdminController {
    private LeaveDAO leaveDAO;
    private TripDAO tripDAO;
    private HistoryDAO historyDAO;
    private ClerksDAO clerksDAO;
    private SysArgDAO sysArgDAO;
    private DepartmentDAO departmentDAO;
    @Resource
    public void setDepartmentDAO(DepartmentDAO departmentDAO){this.departmentDAO=departmentDAO;}
    @Resource
    public void setSysArgDAO(SysArgDAO sysArgDAO){this.sysArgDAO=sysArgDAO;}
    @Resource
    public void setClerksDAO(ClerksDAO clerksDAO){this.clerksDAO=clerksDAO;}
    @Resource
    public void setHistoryDAO(HistoryDAO historyDAO){this.historyDAO=historyDAO;}
    @Resource
    public void setTripDAO(TripDAO tripDAO){this.tripDAO=tripDAO;}
    @Resource
    public void setLeaveDAO(LeaveDAO leaveDAO){this.leaveDAO=leaveDAO;}
    @RequestMapping(value = "aduitLeave",method = RequestMethod.POST)
    public void aduitLeave(HttpServletRequest request, HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("aduitLeave");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String leaveId=request.getParameter("leave_id");
        String commentId=request.getParameter("comment_id");
        String comment=request.getParameter("comment");
        String state=request.getParameter("state");

        History h=new History();
        h.setCategory(0);
        h.setClerkId(Integer.valueOf(commentId));
        h.setHistoryTime(new Date());
        if(state.equals("0"))h.setDescription("reject_leave");
        else h.setDescription("approve_leave");
        historyDAO.attachDirty(h);

        Leaves l=leaveDAO.findLeaveById(leaveId);
        l.setUpdateTime(new Date());
        l.setState(Integer.parseInt(state));
        l.setComment(comment);
        l.setCommentId(Integer.parseInt(commentId));
        leaveDAO.attachDirty(l);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value = "aduitTrip",method=RequestMethod.POST)
    public void aduitTrip(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("aduitTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String tripId=request.getParameter("trip_id");
        String commentId=request.getParameter("comment_id");
        String comment=request.getParameter("comment");
        String state=request.getParameter("state");

        History h=new History();
        h.setCategory(0);
        h.setClerkId(Integer.valueOf(commentId));
        h.setHistoryTime(new Date());
        if(state.equals("0"))h.setDescription("reject_trip");
        else h.setDescription("approve_trip");
        historyDAO.attachDirty(h);

        Trip t=tripDAO.findTripById(tripId);
        t.setUpdateTime(new Date());
        t.setState(Integer.parseInt(state));
        t.setComment(comment);
        t.setCommentId(Integer.parseInt(commentId));
        tripDAO.attachDirty(t);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value="attachClerk",method=RequestMethod.POST)
    public void attachClerk(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("attachClerk");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String departmentId=request.getParameter("department_id");
        String clerkId=request.getParameter("clerk_id");
        String name=request.getParameter("name");
        Clerks clerks=new Clerks();

        History h=new History();
        h.setCategory(0);
        h.setClerkId(0);
        h.setHistoryTime(new Date());

        if(clerkId!=null){
            clerks=clerksDAO.findById(clerkId);
            h.setDescription("modify_clerk");

        }else{
            clerks.setClerkId(clerksDAO.max()+1);
            clerks.setPassword("123456");
            clerks.setIdentity(3);
            clerks.setName(name);
            h.setDescription("add_clerk");
        }
        historyDAO.attachDirty(h);
        clerks.setDepartmentId(Integer.parseInt(departmentId));
        clerksDAO.attachDirty(clerks);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value="listClerk",method=RequestMethod.POST)
    public void listClerk(HttpServletRequest request,HttpServletResponse response)throws  IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("aduitTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String clerkId=request.getParameter("clerk_id");
        String name=request.getParameter("name");
        String identity=request.getParameter("identity");
        String departmentId=request.getParameter("department_id");
        ArrayList<Clerks> list= (ArrayList<Clerks>) clerksDAO.listClerks(clerkId,name,identity,departmentId);
        JSONArray array=new JSONArray();
        for(Clerks clerks:list){
            array.add(clerks);
        }
        jsonObject.put("data",array);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value="attachDepartment",method=RequestMethod.POST)
    public void attachDepartment(HttpServletRequest request,HttpServletResponse response)throws  IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("aduitTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String departmentId=request.getParameter("department_id");
        String departmentName=request.getParameter("department_name");
        String managerId=request.getParameter("manager_id");
        Department department=new Department();
        History h=new History();
        h.setClerkId(0);
        h.setCategory(0);
        h.setHistoryTime(new Date());
        h.setDescription("add_department");
        if(departmentId!=null){
            department=departmentDAO.findDepartmentById(departmentId);
            h.setDescription("modify_department");
        }else{
            department.setDepartmentId(departmentDAO.max()+1);
        }
        if(departmentName!=null){
            department.setDepartmentName(departmentName);
        }
        if(managerId!=null){
            department.setManagerId(Integer.parseInt(managerId));
            Clerks clerks=clerksDAO.findById(managerId);
            clerks.setIdentity(2);
            clerks.setDepartmentId(Integer.valueOf(departmentId));
            clerksDAO.attachDirty(clerks);
        }
        historyDAO.attachDirty(h);
        departmentDAO.attachDirty(department);
        System.out.println(JSON.toJSONString(department));
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value = "listDepartment",method=RequestMethod.POST)
    public void listDepartment(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("aduitTrip");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        ArrayList<Department> list= (ArrayList<Department>) departmentDAO.listDepartment();
        JSONArray array=new JSONArray();
        for(Department department:list){
            JSONObject json= JSON.parseObject(JSON.toJSONString(department));
            array.add(json);
        }
        jsonObject.put("data",array);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value = "modifyCheckTime",method=RequestMethod.POST)
    public void modifyCheckTime(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("modifyCheckTime");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String kind=request.getParameter("kind");
        String value=request.getParameter("value");
        if(kind.equals("checkin")){
            sysArgDAO.modifyArg(kind,value);
        }
        if(kind.equals("checkout")){
            sysArgDAO.modifyArg(kind,value);
        }
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value="getCheckTime",method = RequestMethod.POST)
    public void getCheckTime(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("getCheckTime");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        SysArg in=sysArgDAO.findByCategory("checkin");
        SysArg out=sysArgDAO.findByCategory("checkout");
        jsonObject.put("checkin",in.getArgContent());
        jsonObject.put("checkout",out.getArgContent());
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value="modifyVacation",method = RequestMethod.POST)
    public void modifyVacation(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("modifyVacation");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String kind=request.getParameter("kind");
        String time=request.getParameter("time");
        if(kind.equals("add")){
            SysArg sysArg=new SysArg();
            sysArg.setArgId(sysArgDAO.max()+1);
            sysArg.setArgContent(time);
            sysArg.setArgCategory("vacation");
            sysArgDAO.attachDirty(sysArg);
        }
        if(kind.equals("remove")){
            sysArgDAO.removeDay(time);
        }
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
    @RequestMapping(value = "listVacation",method = RequestMethod.POST)
    public void listVacation(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("listVacation");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();

        ArrayList<SysArg> list= (ArrayList<SysArg>) sysArgDAO.listVacation();
        JSONArray array=new JSONArray();
        for(SysArg sysArg:list){
            array.add(sysArg);
        }
        jsonObject.put("data",array);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();

    }
    @RequestMapping(value = "listHistory",method = RequestMethod.POST)
    public void listHistory(HttpServletRequest request,HttpServletResponse response)throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        System.out.println("listHistory");
        PrintWriter writer=response.getWriter();
        JSONObject jsonObject=new JSONObject();
        String from=request.getParameter("from");
        String to=request.getParameter("to");
        ArrayList<History> list= (ArrayList<History>) historyDAO.listHistory(from,to);
        JSONArray array=new JSONArray();
        for(History history:list){
            JSONObject json= JSON.parseObject(JSON.toJSONString(history));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String historyTime = sdf.format(history.getHistoryTime());
            json.put("historyTime",historyTime);
            array.add(json);
        }
        jsonObject.put("data",array);
        jsonObject.put("status",200);
        writer.write(jsonObject.toJSONString());
        writer.flush();
    }
}
