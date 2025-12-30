package com.yugurekaze.learningtracker.user.repository;
import com.yugurekaze.learningtracker.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :id")
    void changeUserPassword(@Param("id") Long id, @Param("newPassword") String newPassword);

}
