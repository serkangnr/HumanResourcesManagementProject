package com.java14.controller;

import static com.java14.constants.EndPoint.*;

import com.java14.dto.request.SaveAdminRequestDto;
import com.java14.dto.response.ResponseDto;
import com.java14.entity.Admin;
import com.java14.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADMIN)
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/saveAdmin")
    public ResponseEntity<ResponseDto<Boolean>> saveAdmin(@RequestBody SaveAdminRequestDto dto) {

        return ResponseEntity.ok(ResponseDto.<Boolean>builder()
                .data(adminService.saveAdmin(dto))
                .code(200)
                .message("Succesfully registered")
                .build());
    }

    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<ResponseDto<Boolean>> deleteAdmin(@RequestParam String id) {

        return ResponseEntity.ok(ResponseDto.<Boolean>builder()
                .data(adminService.deleteAdmin(id))
                .code(200)
                .message("Succesfully registered")
                .build());
    }
    @GetMapping("/adminList")
    public ResponseEntity<ResponseDto<List<Admin>>> getAllAdmins() {
        return ResponseEntity.ok(ResponseDto.<List<Admin>>builder()
                .data(adminService.getListAdmin())
                .code(200)
                .message("Admins retrieved successfully")
                .build());
    }
}
