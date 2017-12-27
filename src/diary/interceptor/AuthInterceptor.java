//package diary.interceptor;
//
//import diary.bean.User;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class AuthInterceptor extends HandlerInterceptorAdapter {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("handle");
//        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
//            // AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
//
//            //没有声明需要权限,或者声明不验证权限
////                return true;
//            //  else {
//            //在这里实现自己的权限验证逻辑
//
//            //   if (false)//如果验证成功返回true（这里直接写false来模拟验证失败的处理）
//            //         return true;
//            //     else//如果验证失败
//            //     {
//            //返回到登录界面
//            User user = (User) request.getSession().getAttribute("user");
//            System.out.println("[AuthFilter] user "+user);
////            if (user == null) {
////                response.sendRedirect("/returnIndex");
////                return false;
////            } else {
////                return true;
////            }
//        }
//        return true;
//    }
//
//   /* @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request=(HttpServletRequest)servletRequest;
//        HttpServletResponse response  =(HttpServletResponse) servletResponse;
//        HttpSession session = request.getSession(true);
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
//        }
//        filterChain.doFilter(servletRequest,servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//
//    }*/
//}
