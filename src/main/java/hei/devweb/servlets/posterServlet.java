package hei.devweb.servlets;

import hei.devweb.entities.Annonce;
import hei.devweb.entities.Instrument;
import hei.devweb.manager.bibliothequeAnnonce;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.security.cert.X509Certificate;

import java.util.Date;

@WebServlet("/prive/poster")
@MultipartConfig
public class posterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver= new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        WebContext context = new WebContext(req,resp, req.getServletContext());
        context.setVariable("instruments",Instrument.values());
        context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        if(req.getSession().getAttribute("posterError") != null) {
            context.setVariable("errorMessage", req.getSession().getAttribute("posterError"));
            req.getSession().removeAttribute("posterError");}




        TemplateEngine templateEngine= new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.process("poster",context,resp.getWriter());
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
        String instrument=req.getParameter("instrument");
        Part picture= req.getPart("image");


        Annonce newAnnonce = new Annonce(null, titre, description, prix,etat,identifiantUtilisateurConnecte,Instrument.valueOf(instrument));

            Annonce createdAnnonce = bibliothequeAnnonce.getInstance().addAnnonce(newAnnonce, null);


            Integer id = createdAnnonce.getId();
            resp.sendRedirect("../index");

        } catch (NumberFormatException e){
            req.getSession().setAttribute("posterError", "Remplissez tous les champs");
            resp.sendRedirect("/prive/poster");

        }
    }
    }



