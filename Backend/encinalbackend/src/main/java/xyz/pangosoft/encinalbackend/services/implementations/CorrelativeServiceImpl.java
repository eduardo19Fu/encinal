package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Correlative;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.models.User;
import xyz.pangosoft.encinalbackend.repositories.ICorrelativeRepository;
import xyz.pangosoft.encinalbackend.services.ICorrelativeService;

import java.util.List;

@Service
public class CorrelativeServiceImpl implements ICorrelativeService {

    @Autowired
    private ICorrelativeRepository correlativeRepository;

    @Override
    public List<Correlative> listAll() {
        return this.correlativeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @Override
    public Correlative singleCorrelative(Integer id) {
        return this.correlativeRepository.findById(id).orElse(null);
    }

    @Override
    public Correlative singleCorrelative(User user, Status status) {
        return this.correlativeRepository.findByAssignedToAndStatus(user, status).orElse(null);
    }

    @Override
    public Correlative save(Correlative correlative) {
        return this.correlativeRepository.save(correlative);
    }

    @Override
    public Correlative delete(Correlative correlative) {
        this.correlativeRepository.deleteById(correlative.getCorrelativeId());
        return correlative;
    }
}
