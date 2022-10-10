package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Block;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;
import xyz.pangosoft.encinalbackend.repositories.ITerrainRepository;
import xyz.pangosoft.encinalbackend.services.ITerrainService;

import java.util.List;

@Service
public class TerrainServiceImpl implements ITerrainService {

    @Autowired
    private ITerrainRepository terrainRepository;

    @Override
    public List<Terrain> listTerrains() {
        return terrainRepository.findAll();
    }

    @Override
    public List<Terrain> listTerrainsOnSale(Status status) {
        return this.terrainRepository.findByStatusIs(status);
    }

    @Override
    public List<Terrain> listSoldTerrains() {
        return this.terrainRepository.findSoldTerrains();
    }

    @Override
    public List<Terrain> listTerrainsByBlock(Block block){
        return this.terrainRepository.findByBlock(block);
    }

    @Override
    public Terrain singleTerrain(Integer idTerrain) {
        return terrainRepository.findById(idTerrain).orElse(null);
    }

    @Override
    public Terrain save(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    @Override
    public void delete(Integer idTerrain) {
        terrainRepository.deleteById(idTerrain);
    }
}
