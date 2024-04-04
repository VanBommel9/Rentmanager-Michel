package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private Client client;
    private Vehicle vehicule;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation() {
    }

    public Reservation(int id, Client client, Vehicle vehicule, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client = client;
        this.vehicule = vehicule;
        this.debut = debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicle vehicule) {
        this.vehicule = vehicule;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client=" + client +
                ", vehicule=" + vehicule +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
