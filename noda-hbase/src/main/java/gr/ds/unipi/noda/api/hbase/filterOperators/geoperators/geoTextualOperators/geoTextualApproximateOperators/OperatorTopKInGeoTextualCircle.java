package gr.ds.unipi.noda.api.hbase.filterOperators.geoperators.geoTextualOperators.geoTextualApproximateOperators;

import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Circle;
import gr.ds.unipi.noda.api.hbase.filterOperators.geoperators.geographicalOperators.OperatorInGeoCircle;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Collection;

public class OperatorTopKInGeoTextualCircle extends GeoTextualApproximateOperator<Circle> {
    private final int topK;

    protected OperatorTopKInGeoTextualCircle(String fieldName, Circle circle, String keywordFieldName, Collection<String> keywords, int topK) {
        super(OperatorInGeoCircle.newOperatorInGeoCircle(fieldName, circle), keywordFieldName, keywords.toArray(new String[0]));
        this.topK = topK;
    }

    @Override
    public Filter getOperatorExpression() {
        return null;
    }

    public static OperatorTopKInGeoTextualCircle newOperatorTopKInGeoTextualCircle(String fieldName, Circle circle, String keywordFieldName, Collection<String> keywords, int topK) {
        return new OperatorTopKInGeoTextualCircle(fieldName, circle, keywordFieldName, keywords, topK);
    }
}
