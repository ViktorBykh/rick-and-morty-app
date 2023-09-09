package com.example.rickandmortyapp.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class SortingServiceImplTest {
    private SortingServiceImpl sortingService;

    @BeforeEach
    void setUp() {
        sortingService = new SortingServiceImpl();
    }

    @Test
    public void getSortedListWithSingleSortField() {
        PageRequest pageRequest = sortingService.getSortedList(10, 1, "name");

        assertEquals(1, pageRequest.getPageNumber());
        assertEquals(10, pageRequest.getPageSize());

        Sort.Order order = pageRequest.getSort().getOrderFor("name");
        assertEquals(Sort.Direction.DESC, order.getDirection());
    }

    @Test
    public void getSortedListWithMultipleSortFields() {
        PageRequest pageRequest = sortingService
                .getSortedList(5, 2, "name:ASC;age:DESC");

        assertEquals(2, pageRequest.getPageNumber());
        assertEquals(5, pageRequest.getPageSize());

        Sort.Order nameOrder = pageRequest.getSort().getOrderFor("name");
        assertEquals(Sort.Direction.ASC, nameOrder.getDirection());

        Sort.Order ageOrder = pageRequest.getSort().getOrderFor("age");
        assertEquals(Sort.Direction.DESC, ageOrder.getDirection());
    }
}
