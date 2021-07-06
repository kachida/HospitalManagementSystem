package com.vitalsignsvc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.vitalsignsvc.models.Patient;

@Repository
public interface IPatientRepository extends PagingAndSortingRepository<Patient,Long> {

}
