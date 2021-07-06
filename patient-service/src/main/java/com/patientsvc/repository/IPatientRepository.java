package com.patientsvc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.patientsvc.models.Patient;

@Repository
public interface IPatientRepository extends PagingAndSortingRepository<Patient,Long> {

}
