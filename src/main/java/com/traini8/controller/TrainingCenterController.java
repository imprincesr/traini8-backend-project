package com.traini8.controller;

import com.traini8.model.TrainingCenter;
import com.traini8.service.TrainingCenterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller class for training center operations.
 */
@RestController
@RequestMapping("/api/v1/training-centers")
public class TrainingCenterController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingCenterController.class);

    private final TrainingCenterService service;
    public TrainingCenterController(TrainingCenterService service) {
        this.service = service;
    }

    /**
     * Handles HTTP POST requests and create a new Training Center.
     *
     * @param trainingCenter The TrainingCenter object received from the request body.
     * @return ResponseEntity containing the created TrainingCenter object.
     * @throws Exception if an error occurs during creation.
     */
    @PostMapping
    public ResponseEntity<TrainingCenter> createTrainingCenter(@Valid @RequestBody TrainingCenter trainingCenter) {
        logger.info("Received request to create training center : {}", trainingCenter.getCenterName());
        TrainingCenter savedCenter = service.createTrainingCenter(trainingCenter);
        logger.info("Successfully created training center with ID : {}", savedCenter.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCenter);
    }

    /**
     * Handles HTTP GET requests and fetch a list of training centers.
     *
     * @param centerName (Optional) Filters the training centers by name if provided.
     * @return ResponseEntity containing a list of TrainingCenter objects.
     */
    @GetMapping
    public ResponseEntity<List<TrainingCenter>> getAllTrainingCenters(
            @RequestParam(required = false) String centerName) {
        logger.info("Fetching training centers, filter by name : {}", centerName);
        List<TrainingCenter> centers = service.getAllTrainingCenters(centerName);
        if (centers.isEmpty()) {
            logger.info("No training centers found for filter: {}", centerName);
        } else {
            logger.info("Returning {} training centers", centers.size());
        }
        return ResponseEntity.ok(centers);
    }
}