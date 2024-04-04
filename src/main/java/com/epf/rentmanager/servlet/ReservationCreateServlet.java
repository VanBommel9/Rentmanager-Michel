package com.epf.rentmanager.servlet;

import com.epf.rentmanager.constraints.ReservationConstraints;
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
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends ReservationListServlet{
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

            ArrayList<Reservation> reservations = reservationService.findResaByVehicleId(vehicle_id);
            System.out.println(reservations);

            try {
                boolean isValid2 = ReservationConstraints.dayReservation(reservation, vehicle_id, reservationService);
                boolean isValid7 = (ClientService.ifExists(client) && ReservationConstraints.timeReservation(reservation));
                boolean isValid30 = ReservationConstraints.frequencyReservation(reservation, vehicle_id, reservationService);

                if (isValid2==false){
                    System.out.println("Un vehicule ne peut pas être réservé deux fois le même jour");
                    response.sendRedirect("/rentmanager/rents/create");
                }else if (isValid7==false){
                    System.out.println("Un vehicule ne peut pas être réservé plus de 7 jours de suite");
                    response.sendRedirect("/rentmanager/rents/create");
                }else if(isValid30==false){
                    System.out.println("Un vehicule ne peut pas être réservé 30 jours de suite");
                    response.sendRedirect("/rentmanager/rents/create");
                }else{
                    reservationService.create(reservation);
                    response.sendRedirect("/rentmanager/rents");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // System.out.println(reservation.getVehicule().getConstructeur());
            // System.out.println(reservation.getVehicule().getModele());
            //System.out.println(reservation);

        }catch (ServiceException e){
            e.printStackTrace();

        }

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

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

}
