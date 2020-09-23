package gr.ds.unipi.noda.api.redis.filterOperator.logicalOperators;

import gr.ds.unipi.noda.api.core.operators.filterOperators.FilterOperator;

final class OperatorOr extends LogicalOperator {

    private OperatorOr(FilterOperator filterOperator1, FilterOperator filterOperator2, FilterOperator... filterOperators) {
        super(filterOperator1, filterOperator2, filterOperators);
    }

    @Override
    public String getLogicalOperatorType() {
        return "or";
    }

    public static OperatorOr newOperatorOr(FilterOperator filterOperator1, FilterOperator filterOperator2, FilterOperator... filterOperators) {
        return new OperatorOr(filterOperator1, filterOperator2, filterOperators);
    }

}
