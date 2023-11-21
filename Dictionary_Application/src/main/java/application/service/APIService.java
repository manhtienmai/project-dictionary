package application.service;

public abstract class APIService {
    protected String apiKey;
    protected String endpointUrl;
    protected String region;
    public APIService(String apiKey, String endpointUrl, String region) {
        this.apiKey = apiKey;
        this.endpointUrl = endpointUrl;
        this.region = region;
    }

    public abstract String makeRequestAPI(String inputData);
}
