package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/details")
public class UsersDetailServlet extends UserCreateServlet{

    @Autowired
    ClientService clientService;

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
            final Client client = clientService.findById(userId);


            final ArrayList<Reservation> reservations = reservationService.findResaByClientId(userId);
            int countReservations = reservations.size();

            final Set<Vehicle> vehicles = new HashSet<>();
            for (Reservation res: reservations
            ) {
                vehicles.add(res.getVehicule());
            }

            int countVehicle = vehicles.size();

            request.setAttribute("Client",client);
            request.setAttribute("Vehicles",vehicles);
            request.setAttribute("nb_Reservations",countReservations);
            request.setAttribute("nb_Vehicles",countVehicle);
            request.setAttribute("Reservations",reservations);

        }catch (ServiceException e){
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
