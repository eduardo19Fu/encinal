package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Client;
import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IClientService {

    public List<Client> listClients();

    public List<Client> listActiveClients(Status status);

    public Client singleClient(Integer idClient);

    public Client singleClient(String identification);

    public Client save(Client client);

    public void delete(Integer idClient);
}
