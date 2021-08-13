package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Client;

import java.util.List;

public interface IClientService {

    public List<Client> listClients();

    public Client singleClient(Integer idClient);

    public Client save(Client client);

    public void delete(Integer idClient);
}
