package ru.spshop.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class GetUrlPathFromRequest {

    /**
     * /ru-spshop-resource/service/findSameName
     *
     * @return findSameName
     */
    public String getLastUrlPath(ServerHttpRequest request) {
        String[] arrayGetPath = request.getURI().getPath().split("/");
        return arrayGetPath[arrayGetPath.length - 1];
    }

}
