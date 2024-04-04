package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/cars")
public class VehicleListServlet extends HomeServlet {

    @Autowired
    VehicleService vehicleService;


    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/vehicle/update":
                    response.sendRedirect("UserUpdateServlet");
                    break;
                case "/vehicle/delete":
                    response.sendRedirect("UserDeleteServlet");
                    break;
                default:
                    final ArrayList<Vehicle> vehicles = vehicleService.findAll();
                    request.setAttribute("vehicles", vehicles);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
                    break;
            }
        } catch (ServiceException e) {
            e.printStackTrace();

        }
    }
}
