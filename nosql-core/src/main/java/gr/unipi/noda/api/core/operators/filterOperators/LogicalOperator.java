package gr.unipi.noda.api.core.operators.filterOperators;

import gr.unipi.noda.api.core.operators.FilterOperator;

public abstract class LogicalOperator<T> implements FilterOperator<T> {

    private final FilterOperator[] filterOperatorChildren;

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

    protected FilterOperator[] getFilterOperatorChildren() {
        return filterOperatorChildren;
    }

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
