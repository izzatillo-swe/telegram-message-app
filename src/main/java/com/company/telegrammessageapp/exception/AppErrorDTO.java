package com.company.telegrammessageapp.exception;

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
public class AppErrorDTO {

    private String path;
    private Integer status;
    private String message;
    private String field;

    public AppErrorDTO(String path, Integer status, String message) {
        this.path = path;
        this.status = status;
        this.message = message;
    }
}
