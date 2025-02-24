package ru.spshop.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class GetUrlPathFromRequest {
    //final Logger log = LoggerFactory.getLogger(GetUrlPathFromRequest.class);

    /**
     * /ru-spshop-resource/service/findSameName
     *
     * @return findSameName
     */
    public String getLastUrlPath(ServerHttpRequest request) {
        //    request.getURI().getPath()  =  /ru-spshop-resource/service/findSameName
        String[] arrayGetPath = request.getURI().getPath().split("/");
        return arrayGetPath[arrayGetPath.length - 1];
    }

    /**
     * /ru-spshop-resource/service/findSameName
     *
     * @return ru-spshop-resource
     */
    public String getStartUrlPath(ServerHttpRequest request) {
        //    request.getURI().getPath()  =  /ru-spshop-resource/service/findSameName
        String[] arrayGetPath = request.getURI().getPath().split("/");
        return arrayGetPath[1];
    }

    public String getNoStartUrlPath(ServerHttpRequest request) {
        //    request.getURI().getPath()  =  /ru-spshop-resource/service/findSameName
        String[] arrayGetPath = request.getURI().getPath().split("/");
        String result = "";
        for (String s : arrayGetPath) {
            if (!s.equals("")) {
                result += "/" + s;
            }
        }
        return result;
    }

}
