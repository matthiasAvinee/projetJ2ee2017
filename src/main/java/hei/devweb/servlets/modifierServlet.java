package hei.devweb.servlets;

import hei.devweb.entities.Annonce;
import hei.devweb.entities.Instrument;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import hei.devweb.manager.bibliothequeAnnonce;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


@WebServlet("/prive/modifier")
@MultipartConfig
public class modifierServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req, resp, req.getServletContext());
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        try {

            int id = Integer.parseInt(req.getParameter("id"));

            context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));
            context.setVariable("instruments", Instrument.values());

            if (req.getSession().getAttribute("posterError") != null) {
                context.setVariable("errorMessage", req.getSession().getAttribute("posterError"));
                req.getSession().removeAttribute("posterError");
            }

            context.setVariable("annonce",bibliothequeAnnonce.getInstance().getAnnonce(id));

        }

        catch (NumberFormatException e){
            req.getSession().setAttribute("posterError", "Remplissez tous les champs");
            resp.sendRedirect("../prive/modifier");

        }


        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.process("modifier", context, resp.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String titre;
        String description;
        Integer prix;
        Integer etat;


        String identifiantUtilisateurConnecte = (String) req.getSession().getAttribute("utilisateurConnecte");

        try {
            titre = req.getParameter("titre");
            description = req.getParameter("description");
            prix = Integer.parseInt(req.getParameter("prix"));
            etat = Integer.parseInt(req.getParameter("etat"));
            String instrument = req.getParameter("instrument");
            Part picture = req.getPart("image");

                Annonce newAnnonce = new Annonce(null, titre, description, prix, etat, identifiantUtilisateurConnecte, Instrument.valueOf(instrument));

                String idString = req.getParameter("id");
                int ancienid = Integer.parseInt(idString);

                File MyFile = new File(String.valueOf(bibliothequeAnnonce.getInstance().getAnnoncePicture(ancienid)));
                bibliothequeAnnonce.getInstance().supprimerAnnonce(ancienid, identifiantUtilisateurConnecte);
                MyFile.delete();

                Annonce createdAnnonce = bibliothequeAnnonce.getInstance().addAnnonce(newAnnonce, null);

                Integer id = createdAnnonce.getId();
                resp.sendRedirect("../index");

            } catch (NumberFormatException e) {
                req.getSession().setAttribute("posterError", "Remplissez tous les champs");
                resp.sendRedirect("../prive/modifier?id="+req.getParameter("id"));

            }


    }
}

