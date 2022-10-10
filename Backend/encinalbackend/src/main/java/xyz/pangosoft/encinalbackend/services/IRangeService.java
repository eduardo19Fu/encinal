package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Range;

import java.util.List;

public interface IRangeService {

    public List<Range> getAllRanges();

    public Range getSingleRange(Integer id);

    public Range save(Range range);
}
