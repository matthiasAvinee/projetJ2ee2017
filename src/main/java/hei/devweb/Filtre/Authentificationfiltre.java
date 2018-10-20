package hei.devweb.Filtre;

import com.sun.net.httpserver.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Authentificationfiltre implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String identifiant = (String) httpRequest.getSession().getAttribute("utilisateurConnecte");
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if(identifiant == null || "".equals(identifiant)) {

            httpResponse.sendRedirect("../redirect");
            return;

        }else

        chain.doFilter(request, response);

    }



    @Override
    public void destroy() {

    }
}
