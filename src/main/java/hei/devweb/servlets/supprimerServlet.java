package hei.devweb.servlets;

import hei.devweb.daos.AnnonceDaoImpl;
import hei.devweb.daos.mock.impl.AnnonceDao;
import hei.devweb.entities.Annonce;
import hei.devweb.entities.Auteur;
import hei.devweb.manager.bibliothequeAnnonce;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@WebServlet("/prive/supprimer")
public class supprimerServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);

        WebContext context = new WebContext(req,resp, req.getServletContext());
        String user=(String) req.getSession().getAttribute("utilisateurConnecte");
        context.setVariable("pseudo", req.getSession().getAttribute("utilisateurConnecte"));
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");


            try {
                File MyFile = new File(String.valueOf(bibliothequeAnnonce.getInstance().getAnnoncePicture(id)));
                bibliothequeAnnonce.getInstance().supprimerAnnonce(id,user);
                MyFile.delete();
                resp.sendRedirect("mesannonces");
            } catch (IllegalArgumentException e) {
                resp.sendRedirect("mesannonces");
            }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
