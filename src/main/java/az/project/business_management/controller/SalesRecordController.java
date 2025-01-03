package az.project.business_management.controller;

import az.project.business_management.model.request.RecordsFilter;
import az.project.business_management.model.response.SalesRecordsResponse;
import az.project.business_management.service.SalesRecordService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sales-records")
@RequiredArgsConstructor
public class SalesRecordController {
    private final SalesRecordService salesRecordService;


    @GetMapping("/filter")
    private Page<SalesRecordsResponse> getSalesRecordsPage(@ParameterObject RecordsFilter recordsFilter,
                                                           @ParameterObject Pageable pageable) {
        return salesRecordService.getSalesRecordsPage(recordsFilter, pageable);

    }
}
