package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.pangosoft.encinalbackend.models.Sale;

import java.util.Date;
import java.util.List;

public interface ISaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findBySaleDateBetween(Date iniDate, Date endDate);

    @Query(value = "select sa.* from sales as sa " +
            "inner join terrains as t on t.terrain_id = sa.terrain_id " +
            "inner join blocks as b on b.block_id = t.block_id " +
            "where b.block_id = ?1 order by sa.sale_id desc", nativeQuery = true)
    List<Sale> findByBlock(Integer blockId);

    @Query(value = "select sa.* from sales as sa " +
            "inner join terrains as t on t.terrain_id = sa.terrain_id " +
            "inner join blocks as b on b.block_id = t.block_id " +
            "where b.block_id = ?1 and date(sa.sale_date) between ?2 and ?3 " +
            "order by sa.sale_id desc", nativeQuery = true)
    List<Sale> findByBlockAndDate(Integer blockId, Date iniDate, Date endDate);


    @Query(value = "Select ifnull(sum(sa.total), 0.00) from sales sa " +
            "where date(sa.sale_date) = current_date()", nativeQuery = true)
    Double dailyTotalSales();

}
