package gr.ds.unipi.noda.api.redis.filterOperators.comparisonOperators;

import java.util.Date;

public final class OperatorGreaterThan<T> extends ComparisonOperator<T> {

    private OperatorGreaterThan(String fieldName, T fieldValue) {
        super(fieldName, fieldValue);
    }

    @Override
    protected String minumumRangeValue(){
        if(getFieldValue() instanceof Date){
            return "("+String.valueOf(((Date) getFieldValue()).getTime());
        }
        return "("+getFieldValue().toString();
    }

    public static OperatorGreaterThan<Double> newOperatorGreaterThan(String fieldName, Double fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue);
    }

    public static OperatorGreaterThan<Integer> newOperatorGreaterThan(String fieldName, Integer fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue);
    }

    public static OperatorGreaterThan<Float> newOperatorGreaterThan(String fieldName, Float fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue);
    }

    public static OperatorGreaterThan<Short> newOperatorGreaterThan(String fieldName, Short fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue);
    }

    public static OperatorGreaterThan<Long> newOperatorGreaterThan(String fieldName, Long fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue);
    }

    public static OperatorGreaterThan<Long> newOperatorGreaterThan(String fieldName, Date fieldValue) {
        return new OperatorGreaterThan(fieldName, fieldValue.getTime());
    }

}
