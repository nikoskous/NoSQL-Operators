package gr.ds.unipi.noda.api.neo4j.sortOperators;

abstract class SortOperator extends gr.ds.unipi.noda.api.core.operators.sortOperators.SortOperator<StringBuilder> {

    protected SortOperator(String fieldName) {
        super(fieldName);
    }

    protected abstract String getSortCondition();

    @Override
    public StringBuilder getOperatorExpression() {

        StringBuilder sb = new StringBuilder();
        sb.append("ORDER BY s." + getFieldName() + getSortCondition())  ;
        return sb;

    }
}
