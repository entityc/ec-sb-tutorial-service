//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        Module
//   Description: Represents a module within the tutorial. A module is a big partition of
//                the tutorial.
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
import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.dto.ModuleDto;
import org.entityc.tutorial.model.Module;

@Component
public class ModuleMapper
{
    @Autowired ModelMapper mapper;
    @Autowired TutorialMapper tutorialMapper;

    public ModuleDto toDtoFromModel(Module modelObject) {
        ModuleDto dtoObject = new ModuleDto();
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
        if (modelObject != null && modelObject.getSummary() != null) {
            dtoObject.setSummary((modelObject != null ? modelObject.getSummary() : null));
        }
        if (modelObject != null && modelObject.getTitle() != null) {
            dtoObject.setTitle((modelObject != null ? modelObject.getTitle() : null));
        }
        // relationships
        if (modelObject != null && modelObject.getTutorialId() != null) {
            Tutorial refObject = new Tutorial();
            refObject.setId(modelObject.getTutorialId());
            dtoObject.setTutorial(tutorialMapper.toDtoFromModel(refObject));
        }
        return dtoObject;
    }

    public Module toModelFromDto(ModuleDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, Module.class);
    }
}

