package gr.ds.unipi.noda.api.redisearch.filterOperators.textualOperators.conditionalTextualOperator;

import gr.ds.unipi.noda.api.core.constants.StringPool;
import gr.ds.unipi.noda.api.core.operators.filterOperators.comparisonOperators.ComparisonOperator;
import gr.ds.unipi.noda.api.core.operators.filterOperators.textualOperators.TextualOperator;
import gr.ds.unipi.noda.api.redisearch.filterOperators.RediSearchPostFilterOperator;
import io.redisearch.querybuilder.Node;
import io.redisearch.querybuilder.QueryBuilder;
import io.redisearch.querybuilder.Value;
import io.redisearch.querybuilder.Values;

import java.util.Arrays;
import java.util.stream.Stream;

public final class OperatorAnyKeywords extends ConditionalTextualOperator {

    private OperatorAnyKeywords(String fieldName, String[] elements){
        super(fieldName, elements);
    }

    public static OperatorAnyKeywords newOperatorAnyKeywords(String fieldName, String keyword, String... keywords){
        return new OperatorAnyKeywords(fieldName, TextualOperator.oneKeywordsAtLeastToArray(keyword, keywords));
    }

    @Override
    protected Node getOperatorField() {
        return QueryBuilder.union(getFieldName(), Arrays.stream(getKeywords()).map(Values::value).toArray(Value[]::new));
    }

    @Override
    protected StringBuilder getPostOperatorField() {
        StringBuilder sb = new StringBuilder();
        Stream.of(getKeywords())
                .forEach(t -> sb.append(((RediSearchPostFilterOperator) ComparisonOperator.comparisonOperator.newOperatorEq(getFieldName(), t))
                        .getPostOperatorExpression()).append(StringPool.DOUBLE_PIPE));
        return sb;
    }
}
