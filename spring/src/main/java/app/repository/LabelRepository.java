package app.repository;

import org.springframework.stereotype.Repository;

import app.model.Label;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface LabelRepository extends CrudRepository<Label, Long> {

}
