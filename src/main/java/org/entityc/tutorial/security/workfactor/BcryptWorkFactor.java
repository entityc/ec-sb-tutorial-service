package org.entityc.tutorial.security.workfactor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class BcryptWorkFactor {

    private int strength;
    private long duration;
}
