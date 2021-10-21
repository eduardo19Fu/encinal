package xyz.pangosoft.encinalbackend.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.pangosoft.encinalbackend.models.Role;
import xyz.pangosoft.encinalbackend.repositories.IRoleRepository;
import xyz.pangosoft.encinalbackend.services.IRoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role singleRole(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }
}
