package com.my.sharding.config;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jinguo_peng
 * @description TODO
 * @date 2020/3/17 下午8:57
 */
public class IdDataSourceShardingAlgorithm implements RangeShardingAlgorithm<Integer> {

    /**
     * 因为有两个主库
     * */
    private static final Integer MASTER_DATASOURCE_COUNT = 2;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Integer> rangeShardingValue) {

        Collection<String> collect = new ArrayList<>();
        Range<Integer> range = rangeShardingValue.getValueRange();
        for (Integer value = range.lowerEndpoint(); value < range.upperEndpoint(); value ++) {
            for (String availableTargetName:availableTargetNames) {

                if (availableTargetName.endsWith(value % MASTER_DATASOURCE_COUNT + "")) {
                    collect.add(availableTargetName);
                }
            }
        }
        return collect;
    }
}
