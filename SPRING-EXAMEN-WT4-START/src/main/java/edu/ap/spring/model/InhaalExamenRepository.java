package edu.ap.spring.model;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "examens", path = "examens")
public interface InhaalExamenRepository extends PagingAndSortingRepository<InhaalExamen, Long> {
	List<InhaalExamen> findByReason(@Param("reason") String reason);
}
