package com.quantacomit.practical_test.models.DataLoader;

import com.quantacomit.practical_test.Enums.ERole;
import com.quantacomit.practical_test.models.Role;
import com.quantacomit.practical_test.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(ERole.USER).isEmpty()) {
            roleRepository.save(new Role(ERole.USER,1));
        }
        if (roleRepository.findByName(ERole.ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ADMIN,1));
        }
    }
}
