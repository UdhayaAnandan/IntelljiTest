package com.springbootDB.cruddemo.entity;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeFailureResponse {
        private String statusCd;
        private int status;
        private String message;
        private long timeStamp;

}
