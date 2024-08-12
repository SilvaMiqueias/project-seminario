package com.example.projectseminario.service;

import org.springframework.stereotype.Service;

@Service
public class HomeService {

    public Integer add(Integer a, Integer b){
        return a + b;
    }

    public Integer sub(Integer a, Integer b){
        return a - b;
    }

    public Integer mult(Integer a, Integer b){
        return a * b;
    }

    public Integer div(Integer a, Integer b){
        if (a == 0 ||  b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public String getString(String ret){
        if(ret.length() > 10){
            return ret.concat(" maior que 10");
        }
        return ret.concat("volta");
    }

}
