package gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geoTextualOperators;

import gr.ds.unipi.noda.api.core.operators.filterOperators.FilterOperator;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geographicalOperators.GeographicalOperator;
import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Geometry;

public abstract class GeoTextualOperator<T, U extends Geometry> implements FilterOperator<T> {

    private final GeographicalOperator<T, U> geographicalOperator;

    protected GeoTextualOperator(GeographicalOperator<T, U> geographicalOperator) {
        this.geographicalOperator = geographicalOperator;
    }

    public static BaseGeoTextualOperatorFactory geoTextualOperator;

    public GeographicalOperator<T, U> getGeographicalOperator() {
        return geographicalOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoTextualOperator<?, ?> that = (GeoTextualOperator<?, ?>) o;

        return geographicalOperator.equals(that.geographicalOperator);
    }

    @Override
    public int hashCode() {
        return geographicalOperator.hashCode();
    }
}
