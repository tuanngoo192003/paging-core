package anya.core.pagecore.common;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PageUtils<T, ID> {
    private static final String DEFAULT_SIZE = "15";
    private static final int DEFAULT_LIMIT = 50;
    private static final int DEFAULT_SIZE_INT = Integer.parseInt(DEFAULT_SIZE);
    private Class<T> entityType;
    BaseRepository<T, ID> repository;
    Pageable pageable;

    public PageUtils(BaseRepository<T, ID> repository){
        this.repository = repository;
    }

    public List<T> findPage(T entityType, Integer page, Integer perPage, Integer limit){
        String domain = entityType.toString().toLowerCase();
        return repository.findPaginated(domain, getOffset(page, perPage), limit == null ? DEFAULT_LIMIT : limit);
    }

    public Page<T> findPaginated(T entityType, Integer page, Integer perPage, Integer limit){
        String domain = entityType.toString().toLowerCase();
        List<T> list = repository.findPaginated(domain, getOffset(page, perPage), limit == null ? DEFAULT_LIMIT : limit);
        pageable = PageRequest.of(page - 1, perPage);
        return new PageImpl<>(list, pageable, getTotalPage(Integer.toUnsignedLong(list.size()), perPage));
    }

    public Page<T> findPaginated(T entityType, Integer page, Integer perPage, Integer limit, Sort sort){
        String domain = entityType.toString().toLowerCase();
        HashMap<String, String> sortType = getSort(sort);
        List<T> list = repository.findPaginated(domain, getOffset(page, perPage), limit == null ? DEFAULT_LIMIT : limit, sortType.get("field"), sortType.get("order"));
        pageable = PageRequest.of(page - 1, perPage);
        return new PageImpl<>(list, pageable, getTotalPage(Integer.toUnsignedLong(list.size()), perPage));
    }

    private static Integer getOffset(Integer page, Integer perPage) {
        return (page - 1) * (perPage == null ? DEFAULT_SIZE_INT : perPage);
    }

    private static Integer getTotalPage(Long totalRecord, Integer perPage) {
        return (int) Math.ceil((double) totalRecord / (perPage == null ? DEFAULT_SIZE_INT : perPage));
    }

    public HashMap<String, String> getSort(Sort sort){
        HashMap<String, String> sortFieldAndOrder = new HashMap<>();
        String sortOrder = sort.toString();
        String[] fieldAndOrder = sortOrder.split(":");
        sortFieldAndOrder.put("field", fieldAndOrder[0]);
        sortFieldAndOrder.put("order", fieldAndOrder[1]);
        return sortFieldAndOrder;
    }
}
