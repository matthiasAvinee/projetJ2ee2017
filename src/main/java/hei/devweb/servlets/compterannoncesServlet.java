package hei.devweb.servlets;

import hei.devweb.entities.Condition;
import hei.devweb.entities.Instrument;
import hei.devweb.manager.bibliothequeAnnonce;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/compterannonce")
public class compterannoncesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req, resp, req.getServletContext());

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        Instrument instrumentFilter = (Instrument) req.getSession().getAttribute("instrumentFilter");
        context.setVariable("instrumentFilter", instrumentFilter);

        Condition conditionFilter = (Condition) req.getSession().getAttribute("conditionFilter");
        context.setVariable("conditiontFilter", conditionFilter);

        int nbAnnonce= bibliothequeAnnonce.getInstance().compterAnnonce(instrumentFilter,conditionFilter);

        PrintWriter out=resp.getWriter();
        out.append(String.valueOf(nbAnnonce));

    }
}
