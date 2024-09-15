package service.impl;

import entity.users.Admin;
import repository.AdminRepository;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>{
    public AdminServiceImpl(AdminRepository repository) {
        super(repository);
    }
}
