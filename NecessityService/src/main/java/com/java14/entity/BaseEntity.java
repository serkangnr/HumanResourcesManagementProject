package com.java14.entity;

import com.java14.utility.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class BaseEntity implements Serializable {
    private Long createDate;
    private Long updateDate;
    private EStatus status;
}
