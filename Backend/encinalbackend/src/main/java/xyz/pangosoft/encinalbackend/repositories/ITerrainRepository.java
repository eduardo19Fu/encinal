package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;

import java.util.List;

public interface ITerrainRepository extends JpaRepository<Terrain, Integer> {

    List<Terrain> findByStatusIs(Status status);
}
