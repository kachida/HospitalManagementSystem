package com.vitalsignsvc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.vitalsignsvc.models.Vitalsign;


@Repository
public interface IVitalsignRepository extends PagingAndSortingRepository<Vitalsign,Long> {

}
