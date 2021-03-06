package gr.ds.unipi.noda.api.redisearch.sortOperators;

import gr.ds.unipi.noda.api.core.constants.StringPool;
import io.redisearch.aggregation.SortedField;

final class OperatorDesc extends SortOperator {

    private OperatorDesc(String fieldName) {
        super(fieldName);
    }

    @Override
    protected SortedField getOperatorField() {
        return SortedField.desc(StringPool.AT.concat(getFieldName()));
    }

    static OperatorDesc newOperatorDesc(String fieldName) {
        return new OperatorDesc(fieldName);
    }

}
