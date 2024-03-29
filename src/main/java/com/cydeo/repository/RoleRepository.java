package com.cydeo.repository;

import com.cydeo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    //build queries that will bring data from db
    //repository (there are 20 methods availiable from jpa)
    //derive, @Query(JPA-Native)
}
