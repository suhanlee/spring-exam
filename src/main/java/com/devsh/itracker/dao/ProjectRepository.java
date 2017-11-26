package com.devsh.itracker.dao;

import com.devsh.itracker.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT x FROM Project x ORDER BY x.name")
    List<Project> findAllOrderByName(Pageable pageable);
}
