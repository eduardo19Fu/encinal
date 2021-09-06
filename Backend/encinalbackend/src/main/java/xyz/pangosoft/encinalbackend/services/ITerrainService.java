package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.Terrain;

import java.util.List;

public interface ITerrainService {

    public List<Terrain> listTerrains();

    public List<Terrain> listTerrainsOnSale(Status status);

    public Terrain singleTerrain(Integer idTerrain);

    public Terrain save(Terrain terrain);

    public void delete(Integer idTerrain);
}
