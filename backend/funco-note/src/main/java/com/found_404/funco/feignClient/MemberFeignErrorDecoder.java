package com.found_404.funco.feignClient;

import feign.Response;
import feign.codec.ErrorDecoder;

//@Component
public class MemberFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 401 -> throw new RuntimeException("잔액 부족");
            case 500 -> throw new RuntimeException("서버 에러");
            default -> new Exception(response.reason());
        };
    }
}
