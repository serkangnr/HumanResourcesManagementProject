package com.java14.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AdminResponseDto {
    private String  id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String phone;
    private String avatar;

}
