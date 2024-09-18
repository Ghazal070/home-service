package service.impl;

import entity.users.Admin;
import repository.AdminRepository;
import util.AuthHolder;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>{
    public AdminServiceImpl(AdminRepository repository, AuthHolder authHolder) {
        super(repository, authHolder);
    }
}
