package com.farmhousebooking.repository;

import com.farmhousebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findAllByRole(String role);
    
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId AND :role MEMBER OF u.roles")
    boolean hasRole(Long userId, String role);
    
    @Query("SELECT u FROM User u JOIN u.bookings b WHERE b.farmhouse.id = :farmhouseId")
    List<User> findAllUsersByFarmhouseId(Long farmhouseId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.enabled = true")
    long countActiveUsers();
}