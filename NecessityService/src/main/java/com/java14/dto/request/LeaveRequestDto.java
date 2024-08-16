package com.java14.dto.request;

import com.java14.utility.enums.ELeaveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LeaveRequestDto {

    private String token;
    private String startDate;
    private String endDate;
    private ELeaveType leaveType;
    private String description;
}