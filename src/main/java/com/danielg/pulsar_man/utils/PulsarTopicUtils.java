package com.danielg.pulsar_man.utils;

public class PulsarTopicUtils {
    public static String concatFullTopic(String tenant, String namespace, String topic) {
        return "persistent://" + tenant + "/" + namespace + "/" + topic;
    }
}
