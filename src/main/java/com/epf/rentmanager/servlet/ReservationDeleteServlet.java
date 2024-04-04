package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/delete")
public class ReservationDeleteServlet extends ReservationListServlet{
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Object value = request.getAttribute("id");
        if (value == null)
        {
            value = request.getParameter("id");
        }
        int reservationId = Integer.parseInt((String) value);
        try {
            reservationService.deleteByid(reservationId);
        }catch (ServiceException e){
            e.printStackTrace();
        }
        response.sendRedirect("/rentmanager/rents");
    }
}
