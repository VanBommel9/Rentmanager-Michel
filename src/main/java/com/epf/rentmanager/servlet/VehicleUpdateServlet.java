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


@WebServlet("/cars/update")
public class VehicleUpdateServlet extends VehicleListServlet {
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
        if (value == null) {
            value = request.getParameter("id");
        }
        int vehicleId = Integer.parseInt((String) value);
        try {
            final Vehicle vehicle = vehicleService.findById(vehicleId);
            request.setAttribute("Vehicle", vehicle);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            final Vehicle vehicle = new Vehicle();

            String constructeur = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            String nb_places = request.getParameter("seats");
            String  id = request.getParameter("id");

            vehicle.setConstructeur(constructeur);
            vehicle.setModele(modele);
            vehicle.setNb_places(Integer.parseInt(nb_places));
            vehicle.setId(Integer.parseInt(id));

            vehicleService.updateVehicle(vehicle);
        } catch (ServiceException e) {
            e.printStackTrace();

        }
        response.sendRedirect("/rentmanager/cars");
    }
}
