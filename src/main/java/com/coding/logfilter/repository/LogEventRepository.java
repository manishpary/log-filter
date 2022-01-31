package com.coding.logfilter.repository;

import com.coding.logfilter.model.LogEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventRepository extends CrudRepository<LogEvent, String> {
}
