package com.example.demo.model.commodel;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by cathay on 2019/4/19.
 */

@EntityScan
public class Error {
   private int error_code;
    private String error_message;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }


    @Override
    public String toString() {
        return "Error{" +
                "error_code='" + error_code + '\'' +
                ", error_message='" + error_message + '\'' +
                '}';
    }
}
