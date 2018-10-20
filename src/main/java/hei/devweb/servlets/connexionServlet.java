package hei.devweb.servlets;

import hei.devweb.Utilis.MotdePasseUtilis;
import hei.devweb.daos.AuteurDaoImpl;
import hei.devweb.manager.bibliothequeAnnonce;
import javafx.scene.control.Alert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/connexion")
public class connexionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        if(req.getSession().getAttribute("connexionError") != null) {
            context.setVariable("errorMessage", req.getSession().getAttribute("connexionError"));
            req.getSession().removeAttribute("connexionError");}



        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.process("connexion", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuteurDaoImpl auteurDao = new AuteurDaoImpl();
       // MotdePasseUtilis motdePasseUtilis = new MotdePasseUtilis();


        String identifiant = req.getParameter("pseudo");
        String motDePasse = req.getParameter("motdepasse");
        Map<String, String> list = bibliothequeAnnonce.getInstance().listUtilisateursAutorises();



            if (list.containsKey(identifiant)
                    && auteurDao.getMotdePasse(identifiant).equals(motDePasse)) {
                req.getSession().setAttribute("utilisateurConnecte", identifiant);
                resp.sendRedirect("index");
            } else{
                req.getSession().setAttribute("connexionError", "Le compte n'existe pas ou le mot de passe n'est pas le bon");
            resp.sendRedirect("connexion");
        }

    }
}




