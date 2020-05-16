package de.lukkyz.srdc4j.users;

import java.util.Map;

public class Location {

    //TODO // FIXME: 14.04.2020 

    private SubLocation country;
    private SubLocation region;

    public Map<String, String> getNames() {
        return SubLocation.names;
    }

    public String getCode() {
        return SubLocation.code;
    }

    public SubLocation getCountry() {
        return country;
    }

    public SubLocation getRegion() {
        return region;
    }

    public static class SubLocation {

        public static String code;
        public static Map<String, String> names;

    }

}
