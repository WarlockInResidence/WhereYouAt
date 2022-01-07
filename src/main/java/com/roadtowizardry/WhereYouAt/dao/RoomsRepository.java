package com.roadtowizardry.WhereYouAt.dao;

import com.roadtowizardry.WhereYouAt.model.Containers;
import com.roadtowizardry.WhereYouAt.model.Rooms;
import org.springframework.data.repository.CrudRepository;

public interface RoomsRepository extends CrudRepository<Rooms, Long> {

}
