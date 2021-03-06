package gr.ds.unipi.noda.api.client.neo4j;

import org.neo4j.driver.AuthToken;

public class Neo4jBuilderFactory {

    public Neo4JSystem.Builder Builder(String username, String password){
        return new Neo4JSystem.Builder(username, password);
    }

    public Neo4JSystem.Builder Builder(AuthToken authToken) {
        return new Neo4JSystem.Builder(authToken);
    }
}
