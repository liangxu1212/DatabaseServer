package diary.action;

import com.alibaba.fastjson.JSONObject;
import diary.bean.Clerks;
import diary.dao.ClerksDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by MSI on 2018/1/4.
 */
@Controller
@RequestMapping(value = "clerk")
public class ClerkController {
    private ClerksDAO clerksDAO;
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
        System.out.println(request.getQueryString());
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
}
