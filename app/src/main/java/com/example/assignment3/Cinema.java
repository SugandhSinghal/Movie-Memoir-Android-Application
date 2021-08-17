package com.example.assignment3;

public class Cinema {

    /*Cinema class will be used in AddToMemoirFragment*/
    private Integer cinemaId;
    private String cinemaName;
    private Integer cinemaPostcode;

    /*Constructor*/
    public Cinema(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Cinema(Integer cinemaId, String cinemaName, Integer cinemaPostcode) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.cinemaPostcode = cinemaPostcode;
    }

    /*Getters Setters*/
    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Integer getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(Integer cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cinemaId != null ? cinemaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cinema)) {
            return false;
        }
        Cinema other = (Cinema) object;
        if ((this.cinemaId == null && other.cinemaId != null) || (this.cinemaId != null && !this.cinemaId.equals(other.cinemaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.cinema[ cinemaId=" + cinemaId + " ]";
    }

}
