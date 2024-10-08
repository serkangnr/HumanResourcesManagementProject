package com.java14.service;

import com.java14.dto.request.*;
import com.java14.dto.response.CompanyResponseDto;
import com.java14.dto.response.EndTimeManagerResponseDto;
import com.java14.dto.response.GetManagerResponseDto;
import com.java14.dto.response.ManagerResponseDto;
import com.java14.entity.Manager;
import com.java14.exception.ErrorType;
import com.java14.exception.ManagerServiceException;
import com.java14.manager.AuthManager;
import com.java14.manager.CompanyManager;
import com.java14.manager.EmployeeManager;
import com.java14.repository.ManagerRepository;

import com.java14.utility.JwtTokenManager;
import com.java14.utility.enums.EStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;


import static com.java14.utility.enums.EGender.OTHER;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final AuthManager authManager;
    private final CompanyManager companyManager;
    private final JwtTokenManager jwtTokenManager;
    private  final EmployeeManager employeeManager;


    public Boolean saveManager(SaveManagerRequestDto dto) {
        String name= dto.getCompany();
        String companyId = companyManager.companyId(name);
        Manager manager = Manager.builder().authId(dto.getId()).name(dto.getName()).surname(dto.getSurname())
                .email(dto.getEmail()).address(dto.getAddress()).taxNumber(dto.getTaxNumber())
                .phone(dto.getPhone()).companyId( companyId).build();
        managerRepository.save(manager);
        return true;

    }

    public Boolean deleteManager(String id) {
        Manager manager = managerRepository.findById(id).get();
        Long authId = manager.getAuthId();
        manager.setStatus(EStatus.PASSIVE);
        managerRepository.save(manager);
        employeeManager.managerEmployeePasive(manager.getId());
       authManager.deleteAuth(authId);
        return true;
    }

    public List<Manager> getListManager() {
        return managerRepository.findAll();
    }

    public Boolean editManager(UpdateManagerRequestDto dto) {
        Long authId = jwtTokenManager.getIdFromToken(dto.getToken()).orElseThrow(() -> new ManagerServiceException(ErrorType.INVALID_TOKEN));

        Manager manager = managerRepository.findByAuthId(authId);

        manager.setId(manager.getId());
        manager.setName(dto.getName()!=null?dto.getName():manager.getName());
        manager.setSurname(dto.getSurname()!=null?dto.getSurname():manager.getSurname());
        manager.setEmail(dto.getEmail()!=null?dto.getEmail():manager.getEmail());
        manager.setAddress(dto.getAddress()!=null?dto.getAddress():manager.getAddress());
        manager.setPhone(dto.getPhone()!=null?dto.getPhone():manager.getPhone());
        manager.setBirthDate(dto.getBirthDate()!=null?dto.getBirthDate():manager.getBirthDate());
        manager.setAvatar(dto.getAvatar()!=null?dto.getAvatar():manager.getAvatar());
        manager.setGender(dto.getGender()!=null?dto.getGender():manager.getGender());
        managerRepository.save(manager);
        return true;

    }

    public GetManagerResponseDto getManagerByToken(@RequestParam("token")String token) {
        Long authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new ManagerServiceException(ErrorType.INVALID_TOKEN));

        Manager manager = managerRepository.findByAuthId(authId);

        return GetManagerResponseDto.builder()
                .name(manager.getName() != null ? manager.getName() : "")
                .surname(manager.getSurname() != null ? manager.getSurname() : "")
                .email(manager.getEmail() != null ? manager.getEmail() : "")
                .address(manager.getAddress() != null ? manager.getAddress() : "")
                .phone(manager.getPhone() != null ? manager.getPhone() : "")
                .gender(manager.getGender() != null ? manager.getGender() : OTHER)
                .birthDate(manager.getBirthDate() != null ? manager.getBirthDate() : "")
                .avatar(manager.getAvatar() != null ? manager.getAvatar() : "")
                .id(manager.getId() != null ? manager.getId() : "")
                .build();

    }

    public String getManagerIdFindByToken(String token) {
        Long authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new ManagerServiceException(ErrorType.INVALID_TOKEN));

        Manager manager = managerRepository.findByAuthId(authId);


        System.out.println(manager.getId());
        return manager.getId();

    }

    public Boolean registrationEndDate(Integer days,String mail){
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);
        Manager manager = managerRepository.findByEmail(mail);
        manager.setRegistrationEndDate(futureDate);
        managerRepository.save(manager);

        return true;
    }

    public List<EndTimeManagerResponseDto> getManagerByRegistrationEndDate(){
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusMonths(1);
        List<EndTimeManagerResponseDto> companyList = new ArrayList<>();
        List<Manager> managerList = managerRepository.findAllByRegistrationEndDateBetween(today, futureDate);
        if(managerList.isEmpty()){
            return new ArrayList<>();
        }
        managerList.forEach(manager -> {
            CompanyResponseDto companyResponseDto=companyManager.findById(manager.getCompanyId());
            long daysBetween = ChronoUnit.DAYS.between(today, manager.getRegistrationEndDate());
            EndTimeManagerResponseDto endTimeManagerResponseDto= EndTimeManagerResponseDto.builder()
                    .name(companyResponseDto.getName())
                    .logo(companyResponseDto.getLogo())
                    .registrationEndDate(daysBetween+" gün kaldı").build();
            companyList.add(endTimeManagerResponseDto);
            System.out.println("bugün :"+ today+" son gün : "+manager.getRegistrationEndDate()+"kalan gün :"+daysBetween);
        });
        return companyList;

    }

    public List<ManagerResponseDto> getManagerList(){
        List<Manager> managerList = managerRepository.findAll();
        List<ManagerResponseDto> managerResponseDtoList = new ArrayList<>();
        managerList.forEach(manager -> {
            CompanyResponseDto companyResponseDto=companyManager.findById(manager.getCompanyId());
            ManagerResponseDto managerResponseDto = ManagerResponseDto.builder()
                    .name(manager.getName() != null ? manager.getName() : "")
                    .surname(manager.getSurname() != null ? manager.getSurname() : "")
                    .email(manager.getEmail() != null ? manager.getEmail() : "")
                    .avatar(manager.getAvatar() != null ? manager.getAvatar() : "")
                    .birthDate(manager.getBirthDate() != null ? manager.getBirthDate() : "")
                    .phone(manager.getPhone() != null ? manager.getPhone() : "")
                    .address(manager.getAddress() != null ? manager.getAddress() : "")
                    .companyName(companyResponseDto.getName() != null ? companyResponseDto.getName() : "")
                    .gender(manager.getGender() != null ? manager.getGender() : OTHER)
                    .registrationEndDate(manager.getRegistrationEndDate() != null ? manager.getRegistrationEndDate() : null)
                    .build();

            managerResponseDtoList.add(managerResponseDto);

        });
        return managerResponseDtoList;
    }

    public Integer getSubscriptionRemovalDay(String token){
        Long authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new ManagerServiceException(ErrorType.INVALID_TOKEN));
        Manager manager = managerRepository.findByAuthId(authId);
        LocalDate registrationEndDate = manager.getRegistrationEndDate();
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(today, registrationEndDate);
        return (int) daysBetween;}


        public ManagerResponseDto getManager(String managerId){
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND));
        ManagerResponseDto managerResponseDto = ManagerResponseDto.builder()
                .name(manager.getName())
                .surname(manager.getSurname())
                .address(manager.getAddress())
                .phone(manager.getPhone())
                .email(manager.getEmail())
                .avatar(manager.getAvatar())
                .birthDate(manager.getBirthDate())
                .gender(manager.getGender())
                .registrationEndDate(manager.getRegistrationEndDate())


                .build();
         return  managerResponseDto;

        }

    public String getCompanyIdByToken(String token) {
        Long authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new ManagerServiceException(ErrorType.INVALID_TOKEN));

        Manager manager = managerRepository.findByAuthId(authId);
        return manager.getCompanyId();

    }


}
