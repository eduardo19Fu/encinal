package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.ClientTerrain;
import xyz.pangosoft.encinalbackend.repositories.IClientTerrainRepository;
import xyz.pangosoft.encinalbackend.services.IClientTerrainService;

@Service
public class ClientTerrainServiceImpl implements IClientTerrainService {

    @Autowired
    private IClientTerrainRepository clientTerrainRepository;

    @Override
    public ClientTerrain save(ClientTerrain clientTerrain) {
        return this.clientTerrainRepository.save(clientTerrain);
    }
}
