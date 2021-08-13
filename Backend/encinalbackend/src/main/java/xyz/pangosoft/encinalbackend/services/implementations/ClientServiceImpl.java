package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.repositories.IClientRepository;
import xyz.pangosoft.encinalbackend.services.IClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public List<Client> listClients() {
        return clientRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public Client singleClient(Integer idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void delete(Integer idClient) {
        clientRepository.deleteById(idClient);
    }
}
