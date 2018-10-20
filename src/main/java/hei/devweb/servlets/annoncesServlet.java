package hei.devweb.servlets;

import hei.devweb.entities.Annonce;
import hei.devweb.entities.Auteur;
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
import java.util.List;


@WebServlet("/annonce")
public class annoncesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req, resp, req.getServletContext());

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));
        String identifiant = req.getParameter("auteur");


        List<Auteur> info = bibliothequeAnnonce.getInstance().listAuteurChoisi(identifiant);
        context.setVariable("infoList", info);


        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        String identifiantUtilisateurConnecte = (String) req.getSession().getAttribute("utilisateurConnecte");

        templateEngine.process("profil", context, resp.getWriter());

    }
}
