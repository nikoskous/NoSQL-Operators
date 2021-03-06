package gr.ds.unipi.noda.api.neo4j.filterOperators.comparisonOperators;

import java.util.Date;

final class OperatorLessThanEqual<T> extends ComparisonOperator<T> {

    private OperatorLessThanEqual(String fieldName, T fieldValue) {

        super(fieldName, fieldValue);
    }

    @Override
    protected String getComparisonType() {
        return " <= ";
    }

    public static OperatorLessThanEqual<Double> newOperatorLessThanEqual(String fieldName, Double fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

    public static OperatorLessThanEqual<Integer> newOperatorLessThanEqual(String fieldName, Integer fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

    public static OperatorLessThanEqual<Float> newOperatorLessThanEqual(String fieldName, Float fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

    public static OperatorLessThanEqual<Short> newOperatorLessThanEqual(String fieldName, Short fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

    public static OperatorLessThanEqual<Long> newOperatorLessThanEqual(String fieldName, Long fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

    public static OperatorLessThanEqual<Date> newOperatorLessThanEqual(String fieldName, Date fieldValue) {
        return new OperatorLessThanEqual(fieldName, fieldValue);
    }

}
