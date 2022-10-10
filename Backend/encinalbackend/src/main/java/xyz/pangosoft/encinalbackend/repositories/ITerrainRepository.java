package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.pangosoft.encinalbackend.models.Block;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;

import java.util.List;

public interface ITerrainRepository extends JpaRepository<Terrain, Integer> {

    List<Terrain> findByStatusIs(Status status);

    @Query("Select t from Terrain t where t.status.statusId not in(10,13)")
    List<Terrain> findSoldTerrains();

    @Query("Select t from Terrain t where t.block = ?1")
    List<Terrain> findByBlock(Block block);
}
