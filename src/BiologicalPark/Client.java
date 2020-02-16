/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiologicalPark;

import Documents.Ticket;
import AppDisplay.MessageDialog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contem as informações de um utilizador da aplicação.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class Client implements Serializable {
    private String name, username, password, email, address, city, country, nif, postalCode;
    private List<Ticket> tickets;
    
    public Client(String username, String password){
        this.username = username;
        this.name = "";
        this.password = password;
        email = "";
        address = "";
        city = "";
        country = "";
        postalCode = "";
        nif = "";
        tickets = new ArrayList<>();
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    
    /**
     * Reset password
     * @return valor booleano que indica se a password foi ou não alterada
     */
    public void setPassword(String oldPassword, String newPassword, String confirmNewPassword){
        if(oldPassword.equals(password) && newPassword.equals(confirmNewPassword)){
            password = newPassword;
            new MessageDialog("Password has been changed successfully");
            return;
        }
        new MessageDialog("Some error occurred and the password wasn't changed");
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public void buyTicket(Ticket ticket){
        tickets.add(ticket);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

}
