package com.project.api.json;

import com.project.common.response.CommonResponse;
import com.project.domain.applications.JsonApplication;
import com.project.domain.json.dto.JsonLocaleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JSON 컨트롤러
 */
@RestController
@RequestMapping("/jsons")
@RequiredArgsConstructor
public class JsonController {
    private final JsonApplication jsonApplication;

    /**
     * 언어별로 JSON Locale 적용해서 가져오기
     * 
     * @return JSON Locale 결과
     */
    @GetMapping("/locale")
    public CommonResponse<JsonLocaleDto> getJsonLocal() {
        return CommonResponse.success(jsonApplication.getJsonLocale());
    }
}
