package com.danielg.pulsar_man.utils;

public class PulsarKeyUtils {
    public static String generateKey(String topicName, String subscriptionName) {
        return topicName + "-" + subscriptionName;
    }
}
