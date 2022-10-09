package com.mauricio.apirestsecurityjwt.dto;

import java.util.Date;

public class ErrorDetails {

    private Date timeMarc;
    private String message;
    private String details;

    public ErrorDetails(Date timeMarc, String message, String details) {
        super();
        this.timeMarc = timeMarc;
        this.message = message;
        this.details = details;
    }

    public Date getTimeMarc() {
        return timeMarc;
    }
 
    public void setTimeMarc(Date timeMarc) {
        this.timeMarc = timeMarc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }
  
    public void setDetails(String details) {
        this.details = details;
    }
}
