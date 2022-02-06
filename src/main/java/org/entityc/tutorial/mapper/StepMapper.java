//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        Step
//   Description: An exercise is broken down into smaller steps where a single step has the
//                user perform a small task as part of the exercise.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.dto.StepDto;
import org.entityc.tutorial.model.Step;

@Component
public class StepMapper
{
    @Autowired ModelMapper mapper;
    @Autowired ExerciseMapper exerciseMapper;

    public StepDto toDtoFromModel(Step modelObject) {
        StepDto dtoObject = new StepDto();
        // primary key
        dtoObject.setId(modelObject != null ? modelObject.getId() : null);

        // attributes
        if (modelObject != null && modelObject.getCreatedOn() != null) {
            dtoObject.setCreatedOn((modelObject != null ? modelObject.getCreatedOn() : null));
        }
        if (modelObject != null && modelObject.getInstructions() != null) {
            dtoObject.setInstructions((modelObject != null ? modelObject.getInstructions() : null));
        }
        if (modelObject != null && modelObject.getModifiedOn() != null) {
            dtoObject.setModifiedOn((modelObject != null ? modelObject.getModifiedOn() : null));
        }
        if (modelObject != null && modelObject.getNumber() != null) {
            dtoObject.setNumber((modelObject != null ? modelObject.getNumber() : null));
        }
        // relationships
        if (modelObject != null && modelObject.getExerciseId() != null) {
            Exercise refObject = new Exercise();
            refObject.setId(modelObject.getExerciseId());
            dtoObject.setExercise(exerciseMapper.toDtoFromModel(refObject));
        }
        return dtoObject;
    }

    public Step toModelFromDto(StepDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, Step.class);
    }
}

