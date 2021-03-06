package gr.ds.unipi.noda.api.redisearch;

import gr.ds.unipi.noda.api.core.constants.StringPool;
import gr.ds.unipi.noda.api.redisearch.filterOperators.geoperators.geographicalOperators.ZRangeInfo;
import io.redisearch.AggregationResult;
import io.redisearch.Query;
import io.redisearch.aggregation.AggregationBuilder;
import io.redisearch.aggregation.Group;
import io.redisearch.aggregation.SortedField;
import io.redisearch.aggregation.reducers.Reducer;
import io.redisearch.client.Client;
import io.redisearch.querybuilder.Node;
import io.redisearch.querybuilder.QueryBuilder;
import io.redisearch.querybuilder.QueryNode;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.Pool;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RediSearchQueryHelper {
    private final Pool<Jedis> jedisPool;
    private final Client client;
    private Consumer<Optional<ZRangeInfo>> optionalConsumer;
    private AggregationBuilder aggregationBuilder;
    private Query query;
    private ZRangeInfo zRangeInfo;
    private boolean isAggregate;
    private QueryNode queryBuilder;
    private final ArrayDeque<Group> groups;

    public RediSearchQueryHelper(String indexName, Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
        this.isAggregate = false;
        this.client = new Client(indexName, jedisPool);
        this.groups = new ArrayDeque<>();
        initConsumers();
    }

    private RediSearchQueryHelper(Pool<Jedis> jedisPool, Client client, AggregationBuilder aggregationBuilder, Query query, ZRangeInfo zRangeInfo, boolean isAggregate, QueryNode queryBuilder, ArrayDeque<Group> groups) {
        this.jedisPool = jedisPool;
        this.client = client;
        this.aggregationBuilder = aggregationBuilder;
        this.query = query;
        this.zRangeInfo = zRangeInfo;
        this.isAggregate = isAggregate;
        this.queryBuilder = queryBuilder;
        this.groups = new ArrayDeque<>(groups);
        initConsumers();
    }

    private void initConsumers() {
        Consumer<ZRangeInfo> optionalPoolBiConsumer = (zRangeInfo) -> {
            Map<String, Set<String>> rectangleSearchMember = zRangeInfo.getKeys().stream()
                    .collect(Collectors.toMap(o -> o, o -> jedisPool.getResource().zrangeByScore(o, zRangeInfo.getLowerBoundScore(), zRangeInfo.getUpperBoundScore())));
            List<GeoCoordinate> geopos = rectangleSearchMember.entrySet().stream()
                    .map(key -> jedisPool.getResource().geopos(key.getKey(), key.getValue().toArray(StringPool.EMPTY_ARRAY)))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            geopos.forEach(pos -> System.out.println(pos.toString()));
        };
        Runnable runnable = () -> Printer.findByValue(isAggregate).print(client, isAggregate ? getAggregationBuilder() : getQuery());
        this.optionalConsumer = OptionalConsumer.of(optionalPoolBiConsumer, runnable);
    }

    private void enableAggregate() {
        if(!isAggregate)
            isAggregate = true;
    }

    private String provideQuery() {
        if (StringPool.BLANK.equalsIgnoreCase(getQueryNode().toString()))
            return StringPool.STAR;
        else return getQueryNode().toString();
    }

    private QueryNode getQueryNode() {
        if(queryBuilder == null) {
            queryBuilder = QueryBuilder.intersect();
        }
        return queryBuilder;
    }

    private AggregationBuilder getAggregationBuilder() {
        if(aggregationBuilder == null) {
            aggregationBuilder = new AggregationBuilder(provideQuery());
        }
        pendingAggregates();
        return aggregationBuilder;
    }

    private void pendingAggregates() {
        groups.forEach(group -> aggregationBuilder.groupBy(group));
        groups.clear();
    }

    private Query getQuery() {
        if(query == null) {
            query = new Query(provideQuery());
        }
        return query;
    }

    public RediSearchQueryHelper copyOf() {
        return new RediSearchQueryHelper(jedisPool, client, aggregationBuilder, query, zRangeInfo, isAggregate, queryBuilder, groups);
    }

    public Jedis getJedisResource() {
        return jedisPool.getResource();
    }

    public void setzRangeInfo(ZRangeInfo zRangeInfo) {
        this.zRangeInfo = zRangeInfo;
    }

    public boolean isAggregate() {
        return isAggregate;
    }

    public void printResults() {
        optionalConsumer.accept(Optional.ofNullable(zRangeInfo));
    }

    public void applyGroupBy(String fieldName, String... fieldNames) {
        enableAggregate();
        String[] fieldNamess = Stream.concat(Stream.of(fieldName), Arrays.stream(fieldNames)).toArray(String[]::new);
        Group group = fieldNamess.length != 0 ? new Group(fieldNamess) : new Group();
        groups.add(group);
    }

    public void applyAggregate(Reducer reducer, Reducer... reducers) {
        enableAggregate();
        Group group = Optional.ofNullable(groups.peekLast())
                .orElseGet(() -> {
                    Group newGroup = new Group();
                    groups.add(newGroup);
                    return newGroup;
                });
        Stream.concat(Stream.of(reducer), Arrays.stream(reducers)).forEach(group::reduce);
    }

    public void applySortBy(SortedField... sortedFields) {
        if (!isAggregate) {
            if (sortedFields.length == 1)
                getQuery().setSortBy(sortedFields[0].getField(), sortedFields[0].getOrder().equals(SortedField.SortOrder.ASC.toString()));
            else
                throw new IllegalArgumentException("Only one SortOperator supported with no aggregations.");
        } else {
            getAggregationBuilder().sortBy(sortedFields);
        }
    }

    public int totalResults() {
        return (int) (isAggregate
                ? client.aggregate(getAggregationBuilder()).totalResults
                : client.search(getQuery()).totalResults);
    }

    public void applyReturnFields(String... fields) {
        getAggregationBuilder().load(fields);
        getQuery().returnFields(fields);
    }

    public void applyResultLimit(int limit) {
        getAggregationBuilder().limit(0, limit);
        getQuery().limit(0, limit);
    }

    public void applyPreQuery(Node node) {
        getQueryNode().add(node);
    }

    public void applyPostQuery(String s) {
        getAggregationBuilder().filter(s);
    }

    public AggregationResult executeAggregation() {
        return client.aggregate(getAggregationBuilder());
    }
}
