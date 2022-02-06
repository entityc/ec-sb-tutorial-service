//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        Session
//   Description: Represents a session within a module. A session typically tries to
//                teach a single concept.
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
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.SessionDto;
import org.entityc.tutorial.model.Session;

@Component
public class SessionMapper
{
    @Autowired ModelMapper mapper;
    @Autowired ModuleMapper moduleMapper;

    public SessionDto toDtoFromModel(Session modelObject) {
        SessionDto dtoObject = new SessionDto();
        // primary key
        dtoObject.setId(modelObject != null ? modelObject.getId() : null);

        // attributes
        if (modelObject != null && modelObject.getCreatedOn() != null) {
            dtoObject.setCreatedOn((modelObject != null ? modelObject.getCreatedOn() : null));
        }
        if (modelObject != null && modelObject.getDiscussion() != null) {
            dtoObject.setDiscussion((modelObject != null ? modelObject.getDiscussion() : null));
        }
        if (modelObject != null && modelObject.getModifiedOn() != null) {
            dtoObject.setModifiedOn((modelObject != null ? modelObject.getModifiedOn() : null));
        }
        if (modelObject != null && modelObject.getNumber() != null) {
            dtoObject.setNumber((modelObject != null ? modelObject.getNumber() : null));
        }
        if (modelObject != null && modelObject.getObjective() != null) {
            dtoObject.setObjective((modelObject != null ? modelObject.getObjective() : null));
        }
        if (modelObject != null && modelObject.getTitle() != null) {
            dtoObject.setTitle((modelObject != null ? modelObject.getTitle() : null));
        }
        // relationships
        if (modelObject != null && modelObject.getModuleId() != null) {
            Module refObject = new Module();
            refObject.setId(modelObject.getModuleId());
            dtoObject.setModule(moduleMapper.toDtoFromModel(refObject));
        }
        return dtoObject;
    }

    public Session toModelFromDto(SessionDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, Session.class);
    }
}

