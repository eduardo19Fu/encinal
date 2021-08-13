package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.IdentificationType;
import xyz.pangosoft.encinalbackend.repositories.IIdentificationTypeRepository;
import xyz.pangosoft.encinalbackend.services.IIdentificationTypeService;

import java.util.List;

@Service
public class IdentificationTypeServiceImpl implements IIdentificationTypeService {

    @Autowired
    private IIdentificationTypeRepository identificationRepository;

    @Override
    public List<IdentificationType> listIdentifications() {
        return identificationRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public IdentificationType singleIdentification(Integer id) {
        return identificationRepository.findById(id).orElse(null);
    }

    @Override
    public IdentificationType save(IdentificationType identificationType) {
        return identificationRepository.save(identificationType);
    }

    @Override
    public void delete(Integer id) {
        identificationRepository.deleteById(id);
    }
}
