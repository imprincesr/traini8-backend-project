package com.traini8.repository;

import com.traini8.model.TrainingCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing TrainingCenter entities.
 */
public interface TrainingCenterRepository extends JpaRepository<TrainingCenter, Long> {

    /**
     * Finds training centers whose names contain the given string, ignoring case.
     *
     * @param centerName The name substring to search for.
     * @return A list of matching TrainingCenter objects.
     */
    List<TrainingCenter> findByCenterNameContainingIgnoreCase(String centerName);

    // Explicitly declared this for clarity, I have used in service for uniqueness check
    boolean existsByCenterCode(String centerCode);
}