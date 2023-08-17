package dev.jlcorradi.Arithmetics.core.webclient.randomdotnet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "randomOrgClient", url = "${randomDotOrg.apiUrl}")
public interface RandomOrgClient {
  // TODO: Add Hystrix support

  @RequestMapping(method = RequestMethod.POST, value = "/json-rpc/2/invoke", consumes = "application/json")
  RandomDotNetGenerateRandomStringResponse generateRandomString(@RequestBody RandomDotNetGenerateRandomStringRequest request);
}