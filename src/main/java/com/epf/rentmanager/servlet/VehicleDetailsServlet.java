package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends VehicleListServlet {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ReservationService reservationService;


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

            final ArrayList<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);
            int countReservations = reservations.size();

            final Set<Client> clients = new HashSet<>();
            for (Reservation res: reservations
                 ) {
                clients.add(res.getClient());
            }

            int countClients = clients.size();

            request.setAttribute("Vehicle", vehicle);
            request.setAttribute("nb_Reservations",countReservations);
            request.setAttribute("nb_Clients",countClients);
            request.setAttribute("Reservations",reservations);
            request.setAttribute("Clients",clients);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }

}
