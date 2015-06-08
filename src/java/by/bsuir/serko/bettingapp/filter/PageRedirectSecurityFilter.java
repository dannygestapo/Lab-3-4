
package by.bsuir.serko.bettingapp.filter;

import by.bsuir.serko.bettingapp.constant.PageType;
import by.bsuir.serko.bettingapp.utility.PathManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class PageRedirectSecurityFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String indexPage = PageType.INDEX.getPageName();
        httpResponse.sendRedirect(request.getServletContext().getContextPath() + PathManager.getPagePath(indexPage));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}