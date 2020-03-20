package com.my.sharding.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @author jinguo_peng
 * @description 根据id取膜分表
 * @date 2020/3/17 下午2:02
 */
public class IdPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    /**
     * 因为一个库中有4个表
     * */
    private static final Integer TABLE_COUNT = 4;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String tableName:availableTargetNames) {
            if (tableName.endsWith(preciseShardingValue.getValue() % TABLE_COUNT + "")) {
                return tableName;
            }
        }
        throw new RuntimeException("未找到合适的表");
    }
}
