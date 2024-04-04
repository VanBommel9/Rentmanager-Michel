package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
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

@WebServlet("/rents")
public class ReservationListServlet extends HomeServlet{

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

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            final ArrayList<Client> clients = clientService.findAll();
            request.setAttribute("Clients",clients);

            final ArrayList<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("Vehicles",vehicles);

            switch (action){
                case "/rents/update":
                    response.sendRedirect("ReservationUpdateServlet");
                    break;
                case "/rents/delete":
                    response.sendRedirect("ReservationDeleteServlet");
                    break;
                default:
                    final ArrayList<Reservation> reservations = reservationService.findAll();
                    request.setAttribute("Reservations",reservations);
                    //System.out.println(reservations);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
                    break;
            }



        }catch (ServiceException e){
            e.printStackTrace();

        }

    }
}
