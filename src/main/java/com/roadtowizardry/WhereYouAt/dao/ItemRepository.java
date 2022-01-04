package com.roadtowizardry.WhereYouAt.dao;

import com.roadtowizardry.WhereYouAt.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface ItemRepository extends CrudRepository<Item, Long> {
    Page<Item> findByContainerId(Long containerId, Pageable pageable);
}
