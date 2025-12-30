package com.yugurekaze.learningtracker.user.repository;
import com.yugurekaze.learningtracker.user.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User,@NonNull Long> {

    Optional<User> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.email = :newEmail WHERE u.id = :id")
    //TODO fix this method, for example with user password
    void changeUserEmail(@Param("id") Long id, @Param("newEmail") String newEmail);

}
