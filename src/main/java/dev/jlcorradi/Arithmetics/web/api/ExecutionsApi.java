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
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static dev.jlcorradi.Arithmetics.web.utils.DateUtils.resolveDateOrDefault;
import static dev.jlcorradi.Arithmetics.web.utils.HttpUtils.getLoggedinUser;

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.EXECUTIONS_V1)
public class ExecutionsApi {

  public static final String DATE_INI_PARAM = "dateIni";
  public static final String DATE_END_PARAM = "dateEnd";
  public static final String DESCRIPTION_PARAM = "description";
  private final OperationService operationService;
  private final RecordService recordService;

  public record OperationRequest(
      OperationType type,
      Object[] params
  ) {
  }

  public record OperationResponse(
      Long id,
      LocalDateTime date,
      String description,
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
            result.getDescription(),
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
      @RequestParam Map<String, String> params) {
    PageRequest pageRequest = PageRequest.of(page, pageSize, resolveSorting(order));

    LocalDateTime initDate = resolveDateOrDefault(params.get(DATE_INI_PARAM),
        () -> LocalDateTime.now().minusMonths(1L));

    LocalDateTime endDate = resolveDateOrDefault(params.get(DATE_END_PARAM), LocalDateTime::now).plusDays(1);

    String description = Optional.ofNullable(params.get(DESCRIPTION_PARAM))
        .orElse("");

    Page<Record> records = recordService.queryRecords(pageRequest, getLoggedinUser(), initDate, endDate, description);
    return ResponseEntity.ok(
        new PageImpl<>(
            records.getContent().stream()
                .map(record -> new OperationResponse(
                    record.getId(),
                    record.getDate(),
                    record.getDescription(),
                    record.getAmount(),
                    record.getUserBalance(),
                    record.getOperationResponse()))
                .collect(Collectors.toList()),
            pageRequest,
            records.getTotalElements()
        )
    );
  }

  @DeleteMapping("/{id}")
  public void deleteRecord(@PathVariable("id") Long id) {
    recordService.delete(id);
    HttpUtils.addHeaderMessage(HeaderMessageType.SUCCESS, MessageConstants.DELETED_SUCCESS_MSG);
  }

  private Sort resolveSorting(String order) {
    Pattern pattern = Pattern.compile("^(.*?)(:ASC|:DESC)?$");
    Matcher matcher = pattern.matcher(order);
    boolean isMatch = matcher.matches();

    if (!isMatch) {
      return Sort.by(order);
    }

    return ":DESC".equals(matcher.group(2)) ?
        Sort.by(matcher.group(1)).descending() :
        Sort.by(matcher.group(1)).ascending();
  }

}
