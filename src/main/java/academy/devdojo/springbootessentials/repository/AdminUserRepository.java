package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    AdminUser findByUsername(String username);
}