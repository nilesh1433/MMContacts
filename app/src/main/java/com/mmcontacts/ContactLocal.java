package com.mmcontacts;

/**
 * Created by Rahul Raj on 27-Jun-15.
 */
public class ContactLocal {


    private String path;
    private String status;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public ContactLocal(String path, String status) {
        this.path = path;
        this.status = status;
    }


}
