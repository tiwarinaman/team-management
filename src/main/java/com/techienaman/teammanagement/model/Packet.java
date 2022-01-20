package com.techienaman.teammanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Packet<T> {

    private String result;
    private String message;
    private T data;

    public Packet(String result, String message) {
        this.result = result;
        this.message = message;
    }

}
