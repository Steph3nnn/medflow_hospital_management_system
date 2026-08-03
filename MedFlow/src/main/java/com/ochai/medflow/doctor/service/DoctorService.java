package com.ochai.medflow.doctor.service;

import com.ochai.medflow.doctor.dto.CreateDoctorRequest;
import com.ochai.medflow.doctor.entity.Doctor;
import com.ochai.medflow.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public void createDoctor(CreateDoctorRequest request) {

        Doctor doctor = Doctor.builder()
                .doctorId(generateDoctorId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .specialty(request.getSpecialty())
                .licenseNumber(request.getLicenseNumber())
                .yearsOfExperience(request.getYearsOfExperience())
                .consultationFee(request.getConsultationFee())
                .build();

        doctorRepository.save(doctor);
    }

    public Doctor getDoctorById(Long id) {

        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public void updateDoctor(Long id, CreateDoctorRequest request) {

        Doctor doctor = getDoctorById(id);

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setGender(request.getGender());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setDepartment(request.getDepartment());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());

        doctorRepository.save(doctor);
    }

    public List<Doctor> searchDoctors(String search) {

        if (search == null || search.trim().isEmpty()) {
            return doctorRepository.findAll();
        }

        return doctorRepository
                .findByDoctorIdContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        search,
                        search,
                        search,
                        search
                );
    }

    public void activateDoctor(Long id) {

        Doctor doctor = getDoctorById(id);

        doctor.setActive(true);

        doctorRepository.save(doctor);
    }

    public void deactivateDoctor(Long id) {

        Doctor doctor = getDoctorById(id);

        doctor.setActive(false);

        doctorRepository.save(doctor);
    }

    private String generateDoctorId() {

        long count = doctorRepository.count() + 1;

        return String.format(
                "DOC-%d-%06d",
                Year.now().getValue(),
                count
        );
    }

    public long countDoctors() {
        return doctorRepository.count();
    }

}
