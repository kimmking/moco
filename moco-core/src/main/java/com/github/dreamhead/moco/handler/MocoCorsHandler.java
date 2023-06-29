package com.github.dreamhead.moco.handler;

import com.github.dreamhead.moco.HttpRequest;
import com.github.dreamhead.moco.MutableHttpResponse;
import com.github.dreamhead.moco.handler.cors.CorsConfig;
import com.google.common.base.Strings;

public class MocoCorsHandler extends AbstractHttpResponseHandler {
    private final CorsConfig[] configs;

    public MocoCorsHandler(final CorsConfig[] configs) {
        this.configs = configs;
    }

    @Override
    protected void doWriteToResponse(final HttpRequest httpRequest, final MutableHttpResponse httpResponse) {
        String requestOrigin = httpRequest.getHeader("Origin");
        if (Strings.isNullOrEmpty(requestOrigin)) {
            return;
        }

        if (configs.length == 0) {
            httpResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpResponse.addHeader("Access-Control-Allow-Methods", "*");
            httpResponse.addHeader("Access-Control-Allow-Headers", "*");
            return;
        }

        for (CorsConfig config : configs) {
            if (!config.configure(httpRequest, httpResponse)) {
                return;
            }
        }
    }
}
