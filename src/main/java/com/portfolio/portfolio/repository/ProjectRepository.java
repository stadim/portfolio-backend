package com.portfolio.portfolio.repository;


import com.portfolio.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Благодарение на JpaRepository, тук автоматично имаме на разположение
    // методи като findAll(), save(), deleteById() и т.н.
}