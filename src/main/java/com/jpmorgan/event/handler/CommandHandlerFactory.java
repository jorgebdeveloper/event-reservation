package com.jpmorgan.event.handler;

public class CommandHandlerFactory {

    public static CommandHandler createService(String userType) {
        return switch (userType.toLowerCase()) {
            case "1" -> new AdminHandler();
            case "2" -> new BuyerHandler();
            default -> throw new IllegalArgumentException("Invalid user type");
        };
    }

}
