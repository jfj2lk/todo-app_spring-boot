package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
