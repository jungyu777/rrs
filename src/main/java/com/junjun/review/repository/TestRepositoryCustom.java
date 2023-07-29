package com.junjun.review.repository;

import com.junjun.review.model.TestEntity;

import java.util.List;

public interface TestRepositoryCustom {

    public List<TestEntity> findAllByNameQuerydsl(String name);
}
