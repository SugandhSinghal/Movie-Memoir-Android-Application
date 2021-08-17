package com.example.assignment3;

import android.net.wifi.hotspot2.pps.Credential;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Credentials {

    private String CUsername;
    private String CPassword;
    private Date CSignupDate;
    private Users userId;

    /*Constructors*/
    public Credentials() {
    }

    public Credentials(String CPassword, Date CSignupDate, String CUsername , Users userId) {
        this.CPassword = CPassword;
        this.CSignupDate= CSignupDate;
        this.CUsername = CUsername;
        this.userId = userId;
    }

    /*Getters Setters*/
    public Credentials(String CUsername){
        this.CUsername = CUsername;
    }

    public String getUsername() {
        return CUsername;
    }

    public void setUsername(String CUsername) {
        this.CUsername = CUsername;
    }

    public String getPassword() {
        return CPassword;
    }

    public void setPassword(String password) {
        this.CPassword = password;
    }

    public Date getSignupDate() {
        return CSignupDate;
    }

    public void setSignupDate(Date CSignupDate) {
        this.CSignupDate = CSignupDate;
    }

    public Users getUser() {
        return userId;
    }

    public void setUser(Users user) {
        this.userId = user;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (CUsername != null ? CUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Credentials)) {
            return false;
        }
        Credentials other  = (Credentials) object;
        if ((this.CUsername == null && other.CUsername != null) || (this.CUsername != null && !this.CUsername.equals(other.CUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Credentials[ username=" + CUsername + " ]";
    }

    /*Code to convert the password into Md5 format as it is stored in server side*/

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String HashConverterPassword(String pass){
        return getMd5(pass);
    }
}
