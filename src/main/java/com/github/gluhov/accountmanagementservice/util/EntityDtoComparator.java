package com.github.gluhov.accountmanagementservice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@UtilityClass
@Slf4j
public class EntityDtoComparator {
    public static String findDifferences(Object entity, Object dto) {
        StringBuilder differences = new StringBuilder();

        var entityProps = BeanUtils.getPropertyDescriptors(entity.getClass());
        for (var prop : entityProps) {
            try {
                Object entityValue = prop.getReadMethod().invoke(entity);
                Object dtoValue = prop.getReadMethod().invoke(dto);

                if (entityValue != null && dtoValue != null && !entityValue.equals(dtoValue)) {
                    differences.append(prop.getName());
                    differences.append(":");
                    differences.append(entityValue);
                    differences.append(";");
                } else if ((entityValue == null && dtoValue != null) || (entityValue != null && dtoValue == null)) {
                    differences.append(prop.getName());
                    differences.append(":");
                    differences.append(entityValue);
                    differences.append(";");
                }
            } catch (Exception e) {
                log.error("Error while compare dto and entity: " + e.getMessage());
            }
        }

        return differences.toString();
    }
}