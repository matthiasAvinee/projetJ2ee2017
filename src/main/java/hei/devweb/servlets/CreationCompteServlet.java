package hei.devweb.servlets;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import hei.devweb.Utilis.MotdePasseUtilis;
import hei.devweb.daos.AnnonceDaoImpl;
import hei.devweb.daos.AuteurDaoImpl;
import hei.devweb.entities.Auteur;
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
import java.util.Map;

@WebServlet("/creationcompte")
public class CreationCompteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver= new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req,resp, req.getServletContext());
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        if(req.getSession().getAttribute("connexionError") != null) {
            context.setVariable("errorMessage", req.getSession().getAttribute("connexionError"));
            req.getSession().removeAttribute("connexionError");}


        TemplateEngine templateEngine= new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.process("creationcompte",context,resp.getWriter());
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo;
        String mdp;
        Integer numerotel;
        String email;
        //MotdePasseUtilis motdePasseUtilis= new MotdePasseUtilis();
        Map<String, String> list = bibliothequeAnnonce.getInstance().listUtilisateursAutorises();


        try {

            pseudo = req.getParameter("pseudo");
            mdp = req.getParameter("motdepasse");
           // String motdepasse = motdePasseUtilis.genererMotDePasse(mdp);
            numerotel = Integer.parseInt(req.getParameter("telephone"));
            email = req.getParameter("email");


            if (list.containsKey(pseudo)) {
                req.getSession().setAttribute("connexionError", "Ce compte existe deja !");
                resp.sendRedirect("creationcompte");
            } else

                try  { Auteur newAuteur = new Auteur(pseudo, mdp, numerotel, email);
                    try{
                    bibliothequeAnnonce.getInstance().addAuteur(newAuteur);
                    req.getSession().setAttribute("utilisateurConnecte", pseudo);
                    resp.sendRedirect("index");
                } catch (MySQLIntegrityConstraintViolationException e) {
                        req.getSession().setAttribute("connexionError", "Ce compte existe !");
                        resp.sendRedirect("creationcompte");}
                } catch (IllegalArgumentException e) {
                        req.getSession().setAttribute("connexionError", "Remplissez correctement les champs !");
                        resp.sendRedirect("creationcompte");
                }
        } catch (NumberFormatException e){
            resp.sendRedirect("creationcompte");
            req.getSession().setAttribute("connexionError", "Remplissez tout les champs !");
        }
    }
}
