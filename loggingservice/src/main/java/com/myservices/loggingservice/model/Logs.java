package com.myservices.loggingservice.model;


public class Logs {


    private Integer id;
    private String loginfo;
    private String objectname;

    public Integer getId() {
        return id;
    }


    public String getLoginfo() {
        return loginfo;
    }


    public String getObjectname() {
        return objectname;
    }


    public Logs(Integer id, String loginfo, String objectname) {
        this.id = id;
        this.loginfo = loginfo;
        this.objectname = objectname;
    }

    public Logs() {
    }
}
