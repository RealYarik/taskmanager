package com.chalyk.taskmanager.repo;

import com.chalyk.taskmanager.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

    Solution findSolutionById(Long id);

    @Query("SELECT s from Solution s where s.taskAndSolution.id = :taskId order by s.id asc")
    List<Solution> findSolutionsByTaskId(@Param("taskId") Long taskId);

    @Query("SELECT COUNT(s) from Solution s where s.taskAndSolution.id = :taskId")
    Integer getSizeSolutionsByTaskId(@Param("taskId") Long taskId);
}
