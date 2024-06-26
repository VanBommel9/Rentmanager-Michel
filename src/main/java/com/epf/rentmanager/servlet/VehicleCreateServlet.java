package com.epf.rentmanager.servlet;

import com.epf.rentmanager.constraints.VehicleConstraints;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends VehicleListServlet{

    @Autowired
    VehicleService vehicleService;


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

            final Vehicle vehicle = new Vehicle();

            String constructeur =  request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            int nbPlace = Integer.parseInt(request.getParameter("seats"));
            vehicle.setConstructeur(constructeur);
            vehicle.setModele(modele);
            vehicle.setNb_places(nbPlace);

            if (VehicleConstraints.isNotValidVehicle(vehicle)) {
                System.out.println("informations du vehicule non valides");
                response.sendRedirect("/rentmanager/cars/create");
            }else {vehicleService.create(vehicle);
                response.sendRedirect("/rentmanager/cars");

            }

        }catch (ServiceException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }
}
