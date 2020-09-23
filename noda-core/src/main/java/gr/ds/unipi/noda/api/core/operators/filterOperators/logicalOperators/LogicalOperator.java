package gr.ds.unipi.noda.api.core.operators.filterOperators.logicalOperators;

import gr.ds.unipi.noda.api.core.operators.filterOperators.FilterOperator;

public abstract class LogicalOperator<T> implements FilterOperator<T> {

    private FilterOperator[] filterOperatorChildren;//a set method has been added because redis module modifies children

    protected LogicalOperator(FilterOperator filterOperator1, FilterOperator filterOperator2, FilterOperator... filterOperators) {
//        checkNumberOfChildrenCondition(filterOperatorChildren);
//        this.filterOperatorChildren = filterOperatorChildren;

        if (filterOperators.length == 0) {
            filterOperatorChildren = new FilterOperator[]{filterOperator1, filterOperator2};
        } else {
            filterOperatorChildren = new FilterOperator[filterOperators.length + 2];
            filterOperatorChildren[0] = filterOperator1;
            filterOperatorChildren[1] = filterOperator2;

            int i = 2;
            for (FilterOperator fops : filterOperators) {
                filterOperatorChildren[i++] = fops;
            }
        }

    }

    protected void setFilterOperatorChildren(FilterOperator[] filterOperatorChildren) {
        this.filterOperatorChildren = filterOperatorChildren;
    }

    protected FilterOperator[] getFilterOperatorChildren() {
        return filterOperatorChildren;
    }

    public static BaseLogicalOperatorFactory logicalOperator;

//    private void checkNumberOfChildrenCondition(FilterOperator... filterOperatorChildren) {
//        if (filterOperatorChildren.length < 2) {
//            try {
//                throw new Exception("Less than two children");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
