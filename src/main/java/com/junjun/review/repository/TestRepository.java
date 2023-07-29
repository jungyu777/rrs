package com.junjun.review.repository;

import com.junjun.review.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Ejb3TransactionAnnotationParser;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom{

    public List<TestEntity> findAllByName(String name);
}
