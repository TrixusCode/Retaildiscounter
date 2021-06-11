package com.retail.discounter.retaildisounter.model;

public class User<E> {
    private UserType type;
    private E data;

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
