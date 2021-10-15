package xyz.pangosoft.encinalbackend.services;

import xyz.pangosoft.encinalbackend.models.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> listAll();

    public Role singleRole(Integer id);
}
