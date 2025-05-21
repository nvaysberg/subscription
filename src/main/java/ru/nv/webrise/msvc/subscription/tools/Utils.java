package ru.nv.webrise.msvc.subscription.tools;

import java.util.UUID;

public class Utils {

    /**
     * Generate unique ID String (random UUID)
     * @return random UUID String
     */
    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

}
