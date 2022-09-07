package com.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {

    public static Pageable translate(Pageable pageable, Map<String, String> fieldMapping) {

        var orders = pageable.getSort().stream()
                .filter(order -> fieldMapping.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), fieldMapping.get(order.getProperty())))
                .collect(Collectors.toList());


        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }

}
