package hei.devweb.servlets;

import hei.devweb.entities.Annonce;
import hei.devweb.manager.bibliothequeAnnonce;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/profil")
public class profilServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));
        String annonceAuteur = req.getParameter("auteur");
        context.setVariable("auteur", annonceAuteur);
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        List<Annonce> listOfAnnonces = bibliothequeAnnonce.getInstance().listAnnonce(null, null);
        context.setVariable("annonceList", listOfAnnonces);


        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        String identifiantUtilisateurConnecte = (String) req.getSession().getAttribute("utilisateurConnecte");

        templateEngine.process("profil", context, resp.getWriter());

    }
}
