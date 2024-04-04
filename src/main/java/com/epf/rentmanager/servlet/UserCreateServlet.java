package com.epf.rentmanager.servlet;


import com.epf.rentmanager.constraints.ClientConstraints;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

@WebServlet("/users/create")
public class UserCreateServlet extends UserListServlet{


    @Autowired
    ClientService clientService;



    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            final Client client = new Client();
            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            String datenaiss = request.getParameter("naissance");

            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(LocalDate.parse(datenaiss));

            if (ClientConstraints.isValidEmail(client)==false) {
                System.out.println("Email déjà existante !");
                response.sendRedirect("/rentmanager/users/create");
            }else if(ClientConstraints.isValidAge(client)==false){
                System.out.println("Age non valide !");
                response.sendRedirect("/rentmanager/users/create");
            }else if(ClientConstraints.isNotValidNomEtPrenom(client)==true){
                System.out.println("Nom et Prenom non valide !");
                response.sendRedirect("/rentmanager/users/create");
            }else {
                client.setNom(client.getNom().toUpperCase(Locale.ROOT));
                clientService.create(client);
                response.sendRedirect("/rentmanager/users");
            }
        }catch (ServiceException e){
            e.printStackTrace();

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }
}
