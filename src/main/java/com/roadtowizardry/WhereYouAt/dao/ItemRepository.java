package com.roadtowizardry.WhereYouAt.dao;

import com.roadtowizardry.WhereYouAt.model.Items;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface ItemRepository extends CrudRepository<Items, Long> {
    Page<Items> findByContainersId(Long containerId, Pageable pageable);
}
