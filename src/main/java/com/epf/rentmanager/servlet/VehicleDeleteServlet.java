package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends VehicleListServlet{
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

        Object value = request.getAttribute("id");
        if (value == null)
        {
            value = request.getParameter("id");
        }
        int vehicleId = Integer.parseInt((String) value);
        try {
            vehicleService.deleteByid(vehicleId);
        }catch (ServiceException e){
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/cars");
    }
}
