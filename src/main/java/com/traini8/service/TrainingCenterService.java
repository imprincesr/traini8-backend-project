package com.traini8.service;

import com.traini8.exception.DuplicateCenterCodeException;
import com.traini8.exception.InvalidTrainingCenterException;
import com.traini8.model.TrainingCenter;
import com.traini8.repository.TrainingCenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing training center operations.
 */
@Service
public class TrainingCenterService {

    // Logger for tracking service operations
    private static final Logger logger = LoggerFactory.getLogger(TrainingCenterService.class);

    private final TrainingCenterRepository repository;
    public TrainingCenterService(TrainingCenterRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new training center with a unique center code.
     *
     * @param trainingCenter The TrainingCenter object to save.
     * @return The saved TrainingCenter object.
     * @throws InvalidTrainingCenterException if the input is null or center code is null.
     * @throws DuplicateCenterCodeException if the center code already exists.
     */
    @Transactional
    public TrainingCenter createTrainingCenter(TrainingCenter trainingCenter) {
        if (trainingCenter == null) {
            logger.error("Received null training center object");
            throw new InvalidTrainingCenterException("Training center cannot be null");
        }
        if (trainingCenter.getCenterCode() == null) {
            logger.error("Center code is null for training center");
            throw new InvalidTrainingCenterException("Center code cannot be null");
        }
        logger.info("Attempting to create training center with code: {}", trainingCenter.getCenterCode());
        if (repository.existsByCenterCode(trainingCenter.getCenterCode())) {
            logger.warn("Duplicate center code detected: {}", trainingCenter.getCenterCode());
            throw new DuplicateCenterCodeException(trainingCenter.getCenterCode());
        }
        trainingCenter.setCreatedOn(System.currentTimeMillis()); // Server sets timestamp, ignoring user input
        TrainingCenter savedCenter = repository.save(trainingCenter);
        logger.info("Training center created with ID: {}", savedCenter.getId());
        return savedCenter;
    }


    /**
     * Fetches all training centers, optionally filtered by name.
     *
     * @param centerName The name to filter by (case-insensitive), or null for all centers.
     * @return A list of TrainingCenter objects, empty if no centers are found.
     */
    public List<TrainingCenter> getAllTrainingCenters(String centerName) {
        logger.info("Fetching training centers with filter: {}", centerName);
        if (centerName != null && !centerName.trim().isEmpty()) { // Trim to avoid whitespace-only filters
            List<TrainingCenter> filteredCenters = repository.findByCenterNameContainingIgnoreCase(centerName);
            logger.info("Found {} centers matching filter: {}", filteredCenters.size(), centerName);
            return filteredCenters;
        }
        List<TrainingCenter> allCenters = repository.findAll();
        logger.info("Returning all {} training centers", allCenters.size());
        return allCenters; // Returns empty list if no centers exist, per assignment
    }
}