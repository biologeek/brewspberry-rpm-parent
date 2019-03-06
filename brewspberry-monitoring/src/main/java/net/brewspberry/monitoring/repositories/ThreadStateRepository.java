package net.brewspberry.monitoring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.brewspberry.monitoring.model.ThreadState;

@Repository
public interface ThreadStateRepository extends JpaRepository<ThreadState, Long>{

	ThreadState findByUuid(String uuid);

}
