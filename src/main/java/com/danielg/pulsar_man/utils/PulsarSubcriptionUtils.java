package com.danielg.pulsar_man.utils;

import org.apache.pulsar.client.api.SubscriptionInitialPosition;

public class PulsarSubcriptionUtils {
    public static SubscriptionInitialPosition pulsarInitialPositionFromString(String initialPosition) {
        switch (initialPosition.toLowerCase()) {
            case "earliest":
                return SubscriptionInitialPosition.Earliest;
            case "latest":
                return SubscriptionInitialPosition.Latest;
            default:
                throw new IllegalArgumentException("Unsupported initial position: " + initialPosition);
        }
    }
}
