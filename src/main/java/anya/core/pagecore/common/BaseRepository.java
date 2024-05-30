package anya.core.pagecore.common;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @Query(value = "SELECT * FROM :domain limit :limit offset :offsets", nativeQuery = true)
    List<T> findPaginated(@Param("domain") String domain, @Param("offsets") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT * FROM :domain ORDER BY :field :sort limit :limit offset :offsets", nativeQuery = true)
    List<T> findPaginated(@Param("domain") String domain, @Param("offsets") Integer offset, @Param("limit") Integer limit,
                          @Param("field") String field, @Param("order") String order);
}
