package com.example.resyoume;

public class NFCDataSingleton {
    private static NFCDataSingleton uniqueInstance = new NFCDataSingleton();
    private String message;
    private String response;

    private NFCDataSingleton(){}

    public static NFCDataSingleton getInstance(){
        return uniqueInstance;
    }

    public String getMessage(){
        return message;
    }

    public String getResponse(){
        return response;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setResponse(String response){
        this.response = response;
    }
}
