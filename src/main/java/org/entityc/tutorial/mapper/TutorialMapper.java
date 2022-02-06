//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        Tutorial
//   Description: Represents an entire tutorial with modules and sessions.
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

import org.entityc.tutorial.dto.TutorialDto;
import org.entityc.tutorial.model.Tutorial;

@Component
public class TutorialMapper
{
    @Autowired ModelMapper mapper;


    public TutorialDto toDtoFromModel(Tutorial modelObject) {
        TutorialDto dtoObject = new TutorialDto();
        // primary key
        dtoObject.setId(modelObject != null ? modelObject.getId() : null);

        // attributes
        if (modelObject != null && modelObject.getCreatedOn() != null) {
            dtoObject.setCreatedOn((modelObject != null ? modelObject.getCreatedOn() : null));
        }
        if (modelObject != null && modelObject.getIdentifier() != null) {
            dtoObject.setIdentifier((modelObject != null ? modelObject.getIdentifier() : null));
        }
        if (modelObject != null && modelObject.getModifiedOn() != null) {
            dtoObject.setModifiedOn((modelObject != null ? modelObject.getModifiedOn() : null));
        }
        if (modelObject != null && modelObject.getOverview() != null) {
            dtoObject.setOverview((modelObject != null ? modelObject.getOverview() : null));
        }
        if (modelObject != null && modelObject.getSummary() != null) {
            dtoObject.setSummary((modelObject != null ? modelObject.getSummary() : null));
        }
        if (modelObject != null && modelObject.getTitle() != null) {
            dtoObject.setTitle((modelObject != null ? modelObject.getTitle() : null));
        }
        return dtoObject;
    }

    public Tutorial toModelFromDto(TutorialDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, Tutorial.class);
    }
}

