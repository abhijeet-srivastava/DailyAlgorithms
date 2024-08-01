package com.lld.filedownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileDownload {

    private HttpRequest buildHttpRequest(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET().build();
    }
    private HttpResponse<InputStream> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

    private void saveFile(InputStream body, String destinationFIle) throws IOException {
        FileOutputStream fos = new FileOutputStream(destinationFIle);
        fos.write(body.readAllBytes());
        fos.close();
    }
    private  void downloadImage(String imageName) throws IOException, InterruptedException {
        HttpRequest request = buildHttpRequest(imageName);

        HttpResponse<InputStream> response = getResponse(request);

        System.out.println("status code: "+response.statusCode());
        InputStream body = response.body();

        saveFile(body, "image.png");
    }
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        Map<Integer, Integer> indices = new HashMap<>();
        for(int i = 0; i < arr2.length; i++) {
            indices.put(arr2[i], i);
        }
        //Arrays.sort(arr1, (a, b) -> Integer.compare(indices.get(a), indices.get(b)));
        return Arrays.stream(arr1).boxed().sorted((a, b) -> compare(a, b, indices)).mapToInt(a -> a.intValue()).toArray();
    }


    private int compare(int num1, int num2, Map<Integer, Integer> indices) {
        num1 = indices.getOrDefault(num1, num1);
        num2 = indices.getOrDefault(num2, num2);
        return Integer.compare(num1, num2);
    }
}
