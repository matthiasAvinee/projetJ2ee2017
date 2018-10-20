package hei.devweb.servlets;

import hei.devweb.entities.Annonce;
import hei.devweb.entities.Condition;
import hei.devweb.entities.Instrument;
import hei.devweb.manager.bibliothequeAnnonce;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/index")
public class HomeServlet extends HttpServlet {

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
        context.setVariable("conditionFilter", conditionFilter);

        context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));

        List<Annonce> annonce = bibliothequeAnnonce.getInstance().listAnnonce(instrumentFilter, conditionFilter);


        context.setVariable("annonceList", annonce);
        context.setVariable("instruments", Instrument.values());
        context.setVariable("conditions", Condition.values());


        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);



            templateEngine.process("index", context, resp.getWriter());
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nominstrument=req.getParameter("instrument");
        String nomcondition=req.getParameter("condition");


        Condition condition=null;
        Instrument instrument=null;

        try {
            condition=Condition.valueOf(nomcondition);
            instrument = Instrument.valueOf(nominstrument);
        } catch (IllegalArgumentException ignored){}

        req.getSession().setAttribute("instrumentFilter",instrument);
        req.getSession().setAttribute("conditionFilter",condition);
        resp.sendRedirect("index");
    }

}
