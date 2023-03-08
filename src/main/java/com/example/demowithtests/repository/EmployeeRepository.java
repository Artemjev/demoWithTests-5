package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByName(String name);

    @NotNull
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByName(String name, Pageable pageable);

    Page<Employee> findByCountryContaining(String country, Pageable pageable);


    // Метод Ярослава (hw-3).
    @Query(value = "select * from users join addresses on users.id = addresses.employee_id " +
            "where users.gender = :gender and addresses.country = :country", nativeQuery = true)
    List<Employee> findByGender(String gender, String country);

    // Метод Ярослава (hw-3), который ищет активные адреса.
    // Передаем страну и получаем список работников, у которых активный адрес в этой стране.
    @Query("select e from Employee e join e.addresses a where a.addressHasActive = true and a.country = :country")
    Page<Employee> findAllWhereIsActiveAddressByCountry(String country, Pageable pageable);

    //---------------------------------------------------------------
    //    My hw-3
    @Query(value = "select * from users where users.is_deleted = false", nativeQuery = true)
    Page<Employee> findAllActive(Pageable pageable);

    @Query(value = "select * from users where users.is_deleted = true", nativeQuery = true)
    Page<Employee> findAllDeleted(Pageable pageable);

    //---------------------------------------------------------------
    //    List<Employee> queryEmployeeByIsVisibleIsNull(); - код Ярослава, относится к более поздним дз.
    List<Employee> queryEmployeeByIsDeletedIsNull();

    List<Employee> queryEmployeeByIsPrivateIsNull(); //- код Ярослава, относится к более поздним дз.


}
