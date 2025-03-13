package com.project.person.ports.input;

import java.util.Map;

public interface UpdatePersonInputPort {
    void update(final String id, final Map<String, Object> request);
}
