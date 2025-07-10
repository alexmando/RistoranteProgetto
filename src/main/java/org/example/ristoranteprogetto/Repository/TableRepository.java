package org.example.ristoranteprogetto.Repository;


import org.example.ristoranteprogetto.Model.Entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Long> {
}
