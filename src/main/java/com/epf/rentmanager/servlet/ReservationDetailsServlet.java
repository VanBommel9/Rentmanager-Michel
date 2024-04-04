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

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends ReservationListServlet{
    @Autowired
    ReservationService reservationService;

    @Autowired
    ClientService clientService;

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
        int reservation_id = Integer.parseInt((String) value);

        try {

            final Reservation reservation = reservationService.findById(reservation_id);
            request.setAttribute("Reservation",reservation);

            final Client client = clientService.findById(reservation.getClient().getId());
            request.setAttribute("Client",client);

            final Vehicle vehicle = vehicleService.findById(reservation.getVehicule().getId());
            request.setAttribute("Vehicle",vehicle);

        }catch (ServiceException e){
            e.printStackTrace();

        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }

}
