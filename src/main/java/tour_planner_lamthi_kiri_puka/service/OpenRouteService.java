package tour_planner_lamthi_kiri_puka.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;//Yeah @Service
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import java.util.Locale;//so that the data can be fetched to e edit a log ( , ; . )

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*@Service
public class OpenRouteService {
    private static final String API_KEY = "";
    private final String BASE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";

    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);

    public JsonNode getRoute(double startLat, double startLng, double endLat, double endLng) throws IOException {
        String url = String.format("%s?api_key=%s&start=%f,%f&end=%f,%f", BASE_URL, API_KEY, startLng, startLat, endLng, endLat);
        logger.info("Requesting route with URL: " + url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(jsonResponse);
            }
        }
    }*/

@Service
public class OpenRouteService {
    private static final String API_KEY = "5b3ce3597851110001cf62486302931e26e14c5dbcbc0ac00a076c01";//API Key builds HTTP URLs using our api_key plus address text.
    private final String BASE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private final String GEOCODE_URL = "https://api.openrouteservice.org/geocode/search";

    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);

    public JsonNode getRoute(double startLat, double startLng, double endLat, double endLng) throws IOException {

        String start = String.format(Locale.US, "%f,%f", startLng, startLat);   // e.g. "13.407032,52.524932"
        String end   = String.format(Locale.US, "%f,%f", endLng,   endLat);     // e.g. "16.348388,48.198674"


        String url = String.format(Locale.US,
            "%s?api_key=%s&start=%s&end=%s",
            BASE_URL, API_KEY, start, end
        );
        
        logger.info("Requesting route with URL: " + url);//here 
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readTree(jsonResponse);
            }
        }
    }

    //GeneralTabController, RouteTabController) consume those coordinates to load images or inject routes into a WebView/Leaflet map.
    public double[] getCoordinates(String location) throws IOException {
        String url = String.format("%s?api_key=%s&text=%s", GEOCODE_URL, API_KEY, location.replace(" ", "%20"));
        logger.info("Requesting coordinates with URL: " + url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                logger.info("Received JSON response for coordinates: " + jsonResponse);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonResponse);
                JsonNode features = node.path("features");

                if (features.isArray() && features.size() > 0) {
                    JsonNode coordinates = features.get(0).path("geometry").path("coordinates");
                    double longitude = coordinates.get(0).asDouble();
                    double latitude = coordinates.get(1).asDouble();
                    return new double[]{latitude, longitude};
                } else {
                    logger.warn("No coordinates found for location: " + location);
                    return null;
                }
            }
        }
    }

    public boolean isValidLocation(String location) throws IOException {
        String url = String.format("%s?api_key=%s&text=%s", GEOCODE_URL, API_KEY, location.replace(" ", "%20"));
        logger.info("Validating location with URL: " + url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                logger.info("Received JSON response for location validation: " + jsonResponse);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonResponse);
                JsonNode features = node.path("features");

                return features.isArray() && features.size() > 0;


            }
        }
    }

    public List<String> getSuggestions(String query) {
        String url = GEOCODE_URL + "?api_key=" + API_KEY + "&text=" + query.replace(" ", "%20");
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> features = (List<Map<String, Object>>) response.get("features");
        return features.stream()
                .map(feature -> (String) ((Map<String, Object>) feature.get("properties")).get("label"))
                .collect(Collectors.toList());
    }

    private final RestTemplate restTemplate = new RestTemplate();
}
