package gr.ds.unipi.noda.api.neo4j;

import gr.ds.unipi.noda.api.core.nosqldb.NoSqlDbConnector;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.net.ServerAddress;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Neo4jConnector implements NoSqlDbConnector<Driver> {

    private final List<Map.Entry<String,Integer>> addresses;
    private final AuthToken authToken;
    private final Config config;
    private String typeOfConnection;
    private Boolean encryptionWithoutLocalhost;


    private Neo4jConnector(List<Map.Entry<String,Integer>> addresses, AuthToken authToken, Config config) {
        this.addresses = addresses;
        this.authToken = authToken;
        this.config = config;
        System.out.println(addresses.get(0).getValue());
        System.out.println(authToken);
    }

    @Override
    public Driver createConnection() {
        //typeOfConnection => neo4j means server connection
        //typeOfConnection => bolt means localhost
        StringBuilder sb = new StringBuilder();
        encryptionWithoutLocalhost = true;
        String firstHost = addresses.get(0).getKey();
        String firstPort = addresses.get(0).getValue().toString();


        for (Map.Entry<String, Integer> address : addresses) {
            if (address.getKey().equals("localhost") || address.getKey().equals("127.0.0.1")) {
                encryptionWithoutLocalhost = false;
                break;
            }
        }

        List<ServerAddress> serverAddresses = null;

        for (Map.Entry<String, Integer> address : addresses) {

            if(address.getKey() != addresses.get(0).getKey() && address.getValue() != addresses.get(0).getValue()) {
                serverAddresses.add(ServerAddress.of(address.getKey(), address.getValue()));
            }
        }


        if(encryptionWithoutLocalhost == true) {
            Config config = Config.builder().withEncryption().build();
        }



        System.out.println("bolt://" + firstHost + ":" + firstPort);


        return GraphDatabase.driver(  "bolt://" + firstHost + ":" + firstPort, authToken, config);
    }

    public static Neo4jConnector newNeo4jConnector(List<Map.Entry<String,Integer>> addresses, AuthToken authToken, Config config) {
        return new Neo4jConnector(addresses, authToken, config);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jConnector that = (Neo4jConnector) o;
        return addresses.equals(that.addresses) &&
                authToken.equals(that.authToken) &&
                config.equals(that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addresses, authToken, config);
    }
}
