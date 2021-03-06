package gr.ds.unipi.noda.api.redis.filterOperators.geoperators.geoTemporalOperators;

import gr.ds.unipi.noda.api.core.constants.Commons;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geoTemporalOperators.BaseGeoTemporalOperatorFactory;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geoTemporalOperators.temporal.TemporalBounds;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Circle;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Point;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Polygon;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Rectangle;

import java.util.Date;

public final class RedisGeoTemporalOperatorFactory extends BaseGeoTemporalOperatorFactory {
    @Override
    public GeoTemporalOperator newOperatorInGeoTemporalPolygon(String fieldName, Polygon polygon, String temporalFieldName, TemporalBounds temporalBounds) {
        return OperatorInGeoTemporalPolygon.newOperatorInGeoTemporalPolygon(fieldName, polygon, temporalFieldName, temporalBounds);
    }

    @Override
    public GeoTemporalOperator newOperatorInGeoTemporalRectangle(String fieldName, Rectangle rectangle, String temporalFieldName, TemporalBounds temporalBounds) {
        return OperatorInGeoTemporalRectangle.newOperatorInGeoTemporalRectangle(fieldName, rectangle, temporalFieldName, temporalBounds);
    }

    @Override
    public GeoTemporalOperator newOperatorInGeoTemporalCircleKm(String fieldName, Circle circle, String temporalFieldName, TemporalBounds temporalBounds) {
        return OperatorInGeoTemporalCircle.newOperatorInGeoTemporalCircle(fieldName, circle, temporalFieldName, temporalBounds);
    }

    @Override
    public GeoTemporalOperator newOperatorInGeoTemporalCircleMeters(String fieldName, Circle circle, String temporalFieldName, TemporalBounds temporalBounds) {
        return OperatorInGeoTemporalCircle.newOperatorInGeoTemporalCircle(fieldName, circle, temporalFieldName, temporalBounds);
    }

    @Override
    public GeoTemporalOperator newOperatorInGeoTemporalCircleMiles(String fieldName, Circle circle, String temporalFieldName, TemporalBounds temporalBounds) {
        return OperatorInGeoTemporalCircle.newOperatorInGeoTemporalCircle(fieldName, circle, temporalFieldName, temporalBounds);
    }

    @Override
    public GeoTemporalOperator newOperatorGeoTemporalNearestNeighbors(String fieldName, Point point, int neighbors) {
        return null;
    }

    public static String getTemporalPart(Date lowerDate, Date upperDate){

        String lowerDateStr = String.valueOf(lowerDate.getTime());
        String upperDateStr = String.valueOf(upperDate.getTime());

        String commonPrefix = Commons.commonPrefix(String.valueOf(lowerDate.getTime()), String.valueOf(upperDate.getTime()));

        if(commonPrefix.length()<13){
            String criticalPosition = "[" + lowerDateStr.charAt(commonPrefix.length())+"-"+upperDateStr.charAt(commonPrefix.length())+"]";
            return String.format("%-17s", commonPrefix + criticalPosition).replace(' ','?');
        }else{
            return String.format("%-13s", commonPrefix).replace(' ','?');
        }
    }
}
