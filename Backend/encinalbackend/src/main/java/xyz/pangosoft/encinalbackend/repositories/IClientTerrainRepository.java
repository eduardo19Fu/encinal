package xyz.pangosoft.encinalbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.pangosoft.encinalbackend.models.ClientTerrain;

public interface IClientTerrainRepository extends JpaRepository<ClientTerrain, Integer> {
}
