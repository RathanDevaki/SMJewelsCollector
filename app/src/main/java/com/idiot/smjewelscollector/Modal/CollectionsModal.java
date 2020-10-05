package com.idiot.smjewelscollector.Modal;

public class CollectionsModal {

    private String Name;
    private String Phone;
    private String ProfilePhoto;
    private String PlanName;
    private String Address;


    public CollectionsModal() {
    }


    public CollectionsModal(String name, String phone, String profilePhoto, String planName, String address) {
        Name = name;
        Phone = phone;
        ProfilePhoto = profilePhoto;
        this.PlanName = planName;
        this.Address = address;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
