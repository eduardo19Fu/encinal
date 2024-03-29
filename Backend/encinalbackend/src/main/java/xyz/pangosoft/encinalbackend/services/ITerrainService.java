package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Block;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;

import java.util.List;

public interface ITerrainService {

    public List<Terrain> listTerrains();

    public List<Terrain> listTerrainsOnSale(Status status);

    public List<Terrain> listSoldTerrains();

    public List<Terrain> listTerrainsByBlock(Block block);

    public Terrain singleTerrain(Integer idTerrain);

    public Terrain save(Terrain terrain);

    public void delete(Integer idTerrain);
}
