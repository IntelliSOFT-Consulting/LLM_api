package com.intellisoft.llm.service;

import com.intellisoft.llm.model.Role;
import com.intellisoft.llm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return  roleRepository.save(role);
    }

}
