package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Users implements Parcelable {
    private Integer userId;
    private String userName;
    private String userSurname;
    private String userGender;
    private Date userDob;
    private String userAddress;
    private String userState;
    private Integer userPostcode;

    /*Constructors*/

    public Users() {
    }

    public Users(Integer userId){
        this.userId = userId;
    }

    public Users(String userAddress, Date userDob, String userGender, Integer userId,String userName, Integer userPostcode,String userState,String userSurname){
        this.userAddress = userAddress;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userId = userId;
        this.userName = userName;
        this.userPostcode = userPostcode;
        this.userState = userState;
        this.userSurname = userSurname;
    }

    /*As Users object is sent to the home screen*/
    protected Users(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        this.userName = in.readString();
        this.userSurname = in.readString();
        this.userGender = in.readString();
        this.userAddress = in.readString();
        this.userState = in.readString();
        this.userPostcode = in.readInt();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return userSurname;
    }

    public void setSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getGender() {
        return userGender;
    }

    public void setGender(String gender) {
        this.userGender = gender;
    }

    public Date getDob() {
        return userDob;
    }

    public void setDob(Date userDob) {
        this.userDob = userDob;
    }

    public String getAddress() {
        return userAddress;
    }

    public void setAddress(String address) {
        this.userAddress = userAddress;
    }

    public String getState() {
        return userState;
    }

    public void setState(String userState) {
        this.userState = userState;
    }

    public Integer getPostcode() {
        return userPostcode;
    }

    public void setPostcode(Integer userPostcode) {
        this.userPostcode = userPostcode;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Users[ userId=" + userId + " ]";
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(userName);
        dest.writeString(userSurname);
        dest.writeString(userGender);
        dest.writeString(userAddress);
        dest.writeString(userState);
        if (userPostcode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userPostcode);
        }
    }
}
