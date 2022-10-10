package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Correlative;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.User;

import java.util.List;

public interface ICorrelativeService {

    public List<Correlative> listAll();

    public Correlative singleCorrelative(Integer id);

    public Correlative singleCorrelative(User user, Status status);

    public Correlative save(Correlative correlative);

    public Correlative delete(Correlative correlative);
}
