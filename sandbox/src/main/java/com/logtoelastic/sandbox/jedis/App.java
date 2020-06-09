package com.logtoelastic.sandbox.jedis;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static class Profile {
        public String name;
        public int followerCount;
        public List<String> lastActions = new ArrayList<>();
        public List<String> addresses = new ArrayList<>();

        public Profile(String name, int followerCount, List<String> lastActions, List<String> addresses) {
            this.name = name;
            this.followerCount = followerCount;
            this.lastActions = lastActions;
            this.addresses = addresses;
        }
    }

    public static void main(String... args) throws IOException {
        Jedis jedis = new Jedis("localhost");
        Gson gson = new Gson();

        var profile = new Profile("test", 10, List.of("foo"), List.of("bar"));
        jedis.set("profile", gson.toJson(profile));


        var profile2 = gson.fromJson(jedis.get("profile"), Profile.class);
        System.out.println("value: " + profile2);
    }
}
