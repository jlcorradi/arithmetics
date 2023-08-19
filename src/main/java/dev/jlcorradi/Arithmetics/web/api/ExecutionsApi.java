package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.service.OperationService;
import dev.jlcorradi.Arithmetics.core.service.RecordService;
import dev.jlcorradi.Arithmetics.web.HeaderMessageType;
import dev.jlcorradi.Arithmetics.web.Paths;
import dev.jlcorradi.Arithmetics.web.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.jlcorradi.Arithmetics.web.utils.DateUtils.resolveDateOrDefault;
import static dev.jlcorradi.Arithmetics.web.utils.HttpUtils.getLoggedinUser;

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.EXECUTIONS_V1)
public class ExecutionsApi {

  private final OperationService operationService;
  private final RecordService recordService;

  public record OperationRequest(
      OperationType type,
      Object[] params
  ) {
  }

  public record OperationResponse(
      Long id,
      LocalDate date,
      String operationDescription,
      BigDecimal price,
      BigDecimal userBalance,
      String result
  ) {
  }

  @PostMapping
  public ResponseEntity<OperationResponse> executeOperation(@RequestBody OperationRequest request) {
    Record result = operationService.execute(getLoggedinUser(), request.type, request.params);

    HttpUtils.addHeaderMessage(HeaderMessageType.SUCCESS, MessageConstants.OPERATION_SUCCESS_MSG);

    return ResponseEntity.ok(
        new OperationResponse(
            result.getId(),
            result.getDate(),
            result.getOperation().getType().getDescription(),
            result.getAmount(),
            result.getUserBalance(),
            result.getOperationResponse()
        )
    );
  }

  @GetMapping
  public ResponseEntity<Page<OperationResponse>> queryExecutions(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
      @RequestParam(value = "order", defaultValue = "date") String order,
      @RequestParam(value = "isAscending", defaultValue = "true") Boolean isAscending,
      @RequestParam Map<String, String> params) {
    PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(isAscending ? Sort.Order.asc(order) : Sort.Order.desc(order)));

    LocalDate initDate = resolveDateOrDefault(params.get("initDate"),
        () -> LocalDate.now().minusMonths(1L));

    LocalDate endDate = resolveDateOrDefault(params.get("endDate"), LocalDate::now);

    Page<Record> records = recordService.queryRecords(pageRequest, getLoggedinUser(), initDate, endDate);
    return ResponseEntity.ok(
        new PageImpl<>(
            records.getContent().stream()
                .map(record -> new OperationResponse(
                    record.getId(),
                    record.getDate(),
                    record.getOperation().getType().getDescription(),
                    record.getAmount(),
                    record.getUserBalance(),
                    record.getOperationResponse()))
                .collect(Collectors.toList()),
            pageRequest, records.getTotalElements())
    );
  }

}
