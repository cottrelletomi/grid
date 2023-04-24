package com.example.notificationchannelmanager.repositories;

import org.example.core.models.UserRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedisRepository extends CrudRepository<UserRedis, String> {}
