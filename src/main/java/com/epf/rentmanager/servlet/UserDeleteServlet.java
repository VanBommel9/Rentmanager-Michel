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

@WebServlet("/users/delete")
public class UserDeleteServlet extends UserListServlet{
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
        /*
         Recupere la session
        HttpSession session = request.getSession(true);
        int userId = Integer.parseInt(session.getId());
          int userId = request.getParameter("id")
        */
        Object value = request.getAttribute("id");
        if (value == null)
        {
            value = request.getParameter("id");
        }
        int userId = Integer.parseInt((String) value);
        try {
            clientService.deleteByid(userId);
        }catch (ServiceException e){
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/users");
    }
}

