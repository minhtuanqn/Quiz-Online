/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MINH TUAN
 */
public class FilterTeacherFunction implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public FilterTeacherFunction() {
    }    
    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        ServletContext servletContext = request.getServletContext();
        Map<String, String> map = (Map<String, String>) servletContext.getAttribute("MAP");
        try {
            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
            String url = null;
            if(resource.endsWith("TC")) {
                url = map.get(resource);
                if(url != null) {
                    HttpSession session = req.getSession(false);
                    if(session != null) {
                        String role = (String) session.getAttribute("ROLE");
                        if(role != null && role.equals("admin")) {
                            
                        }
                        else {
                            url = "cannotAcess.jsp";
                        }
                    }
                    else {
                        url = "cannotAcess.jsp";
                    }
                }
                else {
                    url = "cannotAcess.jsp";
                }
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            else {
                chain.doFilter(request, response);
            }
            
        } 
        catch (Exception e) {
            log("FilterTeacherFunction: " + e.getMessage());
        }
        
    }


    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("FilterTeacherFunction:Initializing filter");
            }
        }
    }


    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
