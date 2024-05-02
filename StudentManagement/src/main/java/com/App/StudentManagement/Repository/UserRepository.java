package com.App.StudentManagement.Repository;

import com.App.StudentManagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
    User findBySchoolNo(int schoolNo);

    boolean existsByUsername(String username);
}
