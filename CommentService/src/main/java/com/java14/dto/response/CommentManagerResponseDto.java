package com.java14.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class CommentManagerResponseDto {


    private String comment;
    private String managerName;
    private String managerSurname;
    private String ManagerAvatar;
    private String companyName;
    private String companyLogo;
    private String sector;
}
