package gr.ds.unipi.noda.api.redisearch.filterOperators.comparisonOperators;

import gr.ds.unipi.noda.api.core.constants.StringPool;
import io.redisearch.querybuilder.Value;
import io.redisearch.querybuilder.Values;

/**
 * @author adimo on 7/10/2019
 */
final class OperatorGreaterThan<T> extends ComparisonOperator<T> {
    private OperatorGreaterThan(String fieldName, T fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    protected Value getOperatorField() {
        return Values.gt(Double.parseDouble(getFieldValue().toString()));
    }

    @Override
    protected String getPostOperatorField() {
        return StringPool.GREATER_THAN;
    }

    static OperatorGreaterThan<Double> newOperatorGreaterThan(String fieldName, Double fieldValue) {
        return new OperatorGreaterThan<>(fieldName, fieldValue);
    }

    static OperatorGreaterThan<Integer> newOperatorGreaterThan(String fieldName, Integer fieldValue) {
        return new OperatorGreaterThan<>(fieldName, fieldValue);
    }

    static OperatorGreaterThan<Float> newOperatorGreaterThan(String fieldName, Float fieldValue) {
        return new OperatorGreaterThan<>(fieldName, fieldValue);
    }

    static OperatorGreaterThan<Short> newOperatorGreaterThan(String fieldName, Short fieldValue) {
        return new OperatorGreaterThan<>(fieldName, fieldValue);
    }

    static OperatorGreaterThan<Long> newOperatorGreaterThan(String fieldName, Long fieldValue) {
        return new OperatorGreaterThan<>(fieldName, fieldValue);
    }
}
