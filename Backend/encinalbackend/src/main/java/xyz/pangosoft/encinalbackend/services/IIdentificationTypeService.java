package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.IdentificationType;

import java.util.List;

public interface IIdentificationTypeService {

    public List<IdentificationType> listIdentifications();

    public IdentificationType singleIdentification(Integer id);

    public IdentificationType save(IdentificationType identificationType);

    public void delete(Integer id);
}
