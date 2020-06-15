package lab9.lab.repository;

import lab9.lab.domain.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface Repository<T extends Entity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}

