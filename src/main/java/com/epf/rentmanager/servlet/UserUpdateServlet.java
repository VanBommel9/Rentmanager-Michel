package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/update")
public class UserUpdateServlet extends UserListServlet{

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Object value = request.getAttribute("id");
        if (value == null)
        {
            value = request.getParameter("id");
        }
        int userId = Integer.parseInt((String) value);
        try {
            final Client client = clientService.findById(userId);
            request.setAttribute("Client",client);
            
        }catch (ServiceException e){
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            final Client client = new Client();

            String nom = request.getParameter("last_name");
            String prenom = request.getParameter("first_name");
            String email = request.getParameter("email");
            String datenaiss = request.getParameter("naissance");
            int id = Integer.parseInt(request.getParameter("id"));

            client.setId(id);
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setNaissance(LocalDate.parse(datenaiss));

            clientService.updateClient(client);
        }catch (ServiceException e){
            e.printStackTrace();
        }

        response.sendRedirect("/rentmanager/users");
    }
}

