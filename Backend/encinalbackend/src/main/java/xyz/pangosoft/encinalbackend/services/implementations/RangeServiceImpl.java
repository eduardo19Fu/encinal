package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Range;
import xyz.pangosoft.encinalbackend.repositories.IRangeRepository;
import xyz.pangosoft.encinalbackend.services.IRangeService;

import java.util.List;

@Service
public class RangeServiceImpl implements IRangeService {

    @Autowired
    private IRangeRepository rangeRepository;

    @Override
    public List<Range> getAllRanges() {
        return rangeRepository.findAll();
    }

    @Override
    public Range getSingleRange(Integer id) {
        return rangeRepository.findById(id).orElse(null);
    }

    @Override
    public Range save(Range range) {
        return rangeRepository.save(range);
    }
}
