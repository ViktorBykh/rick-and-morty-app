package rick.and.morty.app.service;

import org.springframework.data.domain.PageRequest;

public interface SortingService {
    PageRequest getSortedList(Integer count, Integer page, String sortBy);
}
