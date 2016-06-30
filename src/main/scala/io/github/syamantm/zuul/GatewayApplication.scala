package io.github.syamantm.zuul

import io.github.syamantm.zuul.client.GithubClient
import io.github.syamantm.zuul.filters.route.{GithubFilter, HelloFilter}
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.{RequestMethod, PathVariable, RequestMapping, RestController}

object GatewayApplication extends App {
  SpringApplication.run(classOf[GatewayConfiguration])
}

@SpringBootApplication
@EnableZuulProxy
@RestController
class GatewayConfiguration {

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/ping/{name}"))
  def edit(@PathVariable("name") name: String) = {
    name
  }

  @Bean
  def helloFilter(): HelloFilter = new HelloFilter

  @Bean
  def githubFilter(): GithubFilter = new GithubFilter
}

