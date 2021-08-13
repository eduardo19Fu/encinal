package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.Terrain;

public interface ITerrainRepository extends JpaRepository<Terrain, Integer> {
}
