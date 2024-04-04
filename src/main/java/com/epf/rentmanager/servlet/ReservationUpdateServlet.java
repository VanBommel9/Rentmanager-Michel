package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends ReservationListServlet{

    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            final Reservation reservation = new Reservation();


            int vehicle_id = Integer.parseInt( request.getParameter("car")) ;

            int client_id = Integer.parseInt(request.getParameter("client"));
            String debut = request.getParameter("begin");
            LocalDate date_debut = LocalDate.parse(debut);
            String fin = request.getParameter("end");
            LocalDate date_fin = LocalDate.parse(fin);


            Vehicle vehicle = vehicleService.findById(vehicle_id);
            Client client = clientService.findById(client_id);


            reservation.setVehicule(vehicle);
            reservation.setClient(client);
            reservation.setDebut(date_debut);
            reservation.setFin(date_fin);

            reservationService.create(reservation);
            System.out.println(reservation.getVehicule().getConstructeur());
            System.out.println(reservation.getVehicule().getModele());
            System.out.println(reservation);

        }catch (ServiceException e){
            e.printStackTrace();

        }
        response.sendRedirect("/rentmanager/rents");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            final ArrayList<Client> clients = clientService.findAll();
            request.setAttribute("Clients",clients);

            final ArrayList<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("Vehicles",vehicles);

        }catch (ServiceException e){
            e.printStackTrace();

        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(request, response);
    }

}
