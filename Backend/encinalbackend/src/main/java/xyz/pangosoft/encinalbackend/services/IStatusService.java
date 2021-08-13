package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Status;

import java.util.List;

public interface IStatusService {

    public List<Status> listStatus();

    public Status singleStatus(Integer idStatus);

    public Status singleStatusName(String status);
}
