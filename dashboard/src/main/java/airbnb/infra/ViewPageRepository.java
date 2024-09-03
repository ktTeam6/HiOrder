package airbnb.infra;

import airbnb.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "viewPages", path = "viewPages")
public interface ViewPageRepository
    extends PagingAndSortingRepository<ViewPage, Long> {}
