//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        Exercise
//   Description: Represents an exercise within a session. A session typically only has
//                one exercise but it can have more than one if the session is big. An
//                exercise gives the student some hands on experience with the material
//                covered by its session.
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
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.ExerciseDto;
import org.entityc.tutorial.model.Exercise;

@Component
public class ExerciseMapper
{
    @Autowired ModelMapper mapper;
    @Autowired SessionMapper sessionMapper;

    public ExerciseDto toDtoFromModel(Exercise modelObject) {
        ExerciseDto dtoObject = new ExerciseDto();
        // primary key
        dtoObject.setId(modelObject != null ? modelObject.getId() : null);

        // attributes
        if (modelObject != null && modelObject.getCreatedOn() != null) {
            dtoObject.setCreatedOn((modelObject != null ? modelObject.getCreatedOn() : null));
        }
        if (modelObject != null && modelObject.getModifiedOn() != null) {
            dtoObject.setModifiedOn((modelObject != null ? modelObject.getModifiedOn() : null));
        }
        if (modelObject != null && modelObject.getNumber() != null) {
            dtoObject.setNumber((modelObject != null ? modelObject.getNumber() : null));
        }
        if (modelObject != null && modelObject.getOverview() != null) {
            dtoObject.setOverview((modelObject != null ? modelObject.getOverview() : null));
        }
        // relationships
        if (modelObject != null && modelObject.getSessionId() != null) {
            Session refObject = new Session();
            refObject.setId(modelObject.getSessionId());
            dtoObject.setSession(sessionMapper.toDtoFromModel(refObject));
        }
        return dtoObject;
    }

    public Exercise toModelFromDto(ExerciseDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, Exercise.class);
    }
}

