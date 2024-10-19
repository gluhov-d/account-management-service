package com.github.gluhov.accountmanagementservice.util;

import lombok.experimental.UtilityClass;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class EntityDtoComparator {
    private static final Javers javers = JaversBuilder.javers().build();

    public static Map<String, Object> getChangedValues(Object oldObject, Object newObject) {
        Diff diff = javers.compare(oldObject, newObject);
        Map<String, Object> changesMap = new HashMap<>();

        List<ValueChange> changes = diff.getChangesByType(ValueChange.class);
        for (ValueChange change : changes) {
            String fieldName = change.getPropertyName();
            Map<String, Object> changeDetails = new HashMap<>();
            changeDetails.put("old_value", change.getLeft());
            changeDetails.put("new_value", change.getRight());
            changesMap.put(fieldName, changeDetails);
        }

        return changesMap;
    }
}