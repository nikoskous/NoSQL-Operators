package gr.ds.unipi.noda.api.hbase.filterOperators.geoperators.geographicalOperators;

import gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geometries.Geometry;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

abstract class GeographicalOperator<T extends Geometry> extends gr.ds.unipi.noda.api.core.operators.filterOperators.geoperators.geographicalOperators.GeographicalOperator<Filter, T> {
    protected GeographicalOperator(String fieldName, T geometry) {
        super(fieldName, geometry);
    }

    private final FilterList filterList = new FilterList();

//    public Map.Entry<String, byte[]> getMatchingPattern(){
//
//        String geoHashPart = HBaseGeographicalOperatorFactory.getGeoHashPart(this.getGeometry());
//
//        byte[] digits = new byte[geoHashPart.length()+25];
//
//        //geohash
//        for(int i =0; i<geoHashPart.length();i++){
//            if(geoHashPart.charAt(i) != '?'){
//                digits[i] = 0;
//            }
//            else{
//                digits[i] = 1;
//            }
//        }
//
//        for(int i = geoHashPart.length(); i< digits.length;i++){
//            digits[i] = 1;
//        }
//        digits[geoHashPart.length()]=0;
//        digits[geoHashPart.length()+13+1] =0;
//
//        return new AbstractMap.SimpleImmutableEntry<>(geoHashPart+"-?????????????-??????????",digits);
//    }

    @Override
    public Filter getOperatorExpression() {

        //Map.Entry<String, byte[]> entry = getMatchingPattern();

//        List list = Arrays.asList(
//                new Pair<>(Bytes.toBytes(entry.getKey()), entry.getValue()
//                ));

        filterList.addFilter(new PrefixFilter(Bytes.toBytes(HBaseGeographicalOperatorFactory.getGeoHashPart(this.getGeometry()))));
        filterList.addFilter(geometryRefactor());

        return filterList;
    }

    abstract protected Filter geometryRefactor();

}
