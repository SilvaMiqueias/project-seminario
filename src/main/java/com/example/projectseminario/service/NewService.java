package com.example.projectseminario.service;


import org.springframework.stereotype.Service;

@Service
public class NewService {

    public String getString(String ret){
        if(ret.length() > 10){
            return ret.concat(" maior que 10");
        }
        return ret.concat("volta");
    }
}
