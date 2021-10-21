package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Status;
import xyz.pangosoft.encinalbackend.repositories.IStatusRepository;
import xyz.pangosoft.encinalbackend.services.IStatusService;

import java.util.List;

@Service
public class StatusServiceImpl implements IStatusService {

    @Autowired
    private IStatusRepository statusRepository;

    @Override
    public List<Status> listStatus() {
        return statusRepository.findAll();
    }

    @Override
    public Status singleStatus(Integer idStatus) {
        return statusRepository.findById(idStatus).orElse(null);
    }

    @Override
    public Status singleStatusName(String status, String description) {
        return statusRepository.findByStatusAndDescription(status, description).orElse(null);
    }
}
