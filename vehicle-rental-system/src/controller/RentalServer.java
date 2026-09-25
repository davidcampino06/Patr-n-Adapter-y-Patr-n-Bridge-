package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.RentalResult;
import service.RentalService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple HTTP server (no external frameworks) that:
 *   1) serves the static frontend files (HTML, CSS, JS) from the "web" folder
 *   2) exposes a small endpoint (/api/calculate) that the frontend calls
 *      with fetch(), which uses RentalService (Adapter + Bridge) to
 *      calculate the rental and returns the result as JSON.
 *
 * This is the CONTROLLER layer: it only handles HTTP concerns
 * (reading parameters, writing responses). All the pattern logic lives
 * in the adapter, bridge and service packages.
 */
public class RentalServer {

    private final RentalService rentalService = new RentalService();
    private static final String WEB_FOLDER = "web";

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/calculate", new CalculateHandler());
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("DriveRent server started at http://localhost:" + port);
    }

    /**
     * Handles GET /api/calculate?vehicle=car&plan=daily&duration=5&customer=David&confirm=false
     */
    private class CalculateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());

            try {
                String vehicleType = params.getOrDefault("vehicle", "car");
                String planType = params.getOrDefault("plan", "daily");
                int duration = Integer.parseInt(params.getOrDefault("duration", "1"));
                String customerName = params.getOrDefault("customer", "Customer");
                boolean confirm = "true".equals(params.getOrDefault("confirm", "false"));

                RentalResult result = rentalService.calculateRental(
                        vehicleType, planType, duration, customerName, confirm);

                sendJson(exchange, 200, toJson(result));

            } catch (Exception e) {
                String errorJson = "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}";
                sendJson(exchange, 400, errorJson);
            }
        }
    }

    /**
     * Serves static files (index.html, style.css, script.js) from the web folder.
     */
    private class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            File file = new File(WEB_FOLDER, path);

            if (!file.exists() || file.isDirectory()) {
                byte[] bytes = "File not found".getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
                exchange.sendResponseHeaders(404, bytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
                return;
            }

            String contentType = guessContentType(path);
            byte[] bytes = Files.readAllBytes(file.toPath());

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private String guessContentType(String path) {
        if (path.endsWith(".html")) return "text/html; charset=UTF-8";
        if (path.endsWith(".css")) return "text/css; charset=UTF-8";
        if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
        return "text/plain; charset=UTF-8";
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            map.put(key, value);
        }
        return map;
    }

    private String toJson(RentalResult r) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"customerName\":\"").append(escapeJson(r.getCustomerName())).append("\",");
        sb.append("\"vehicleName\":\"").append(escapeJson(r.getVehicleName())).append("\",");
        sb.append("\"vehicleBrand\":\"").append(escapeJson(r.getVehicleBrand())).append("\",");
        sb.append("\"vehicleCategory\":\"").append(escapeJson(r.getVehicleCategory())).append("\",");
        sb.append("\"planName\":\"").append(escapeJson(r.getPlanName())).append("\",");
        sb.append("\"duration\":").append(r.getDuration()).append(",");
        sb.append("\"pricePerPeriod\":").append(r.getPricePerPeriod()).append(",");
        sb.append("\"totalPrice\":").append(r.getTotalPrice()).append(",");
        sb.append("\"status\":\"").append(escapeJson(r.getStatus())).append("\"");
        sb.append("}");
        return sb.toString();
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void sendJson(HttpExchange exchange, int statusCode, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
