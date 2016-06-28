package io.github.syamantm.zuul

import io.github.syamantm.zuul.filters.route.ForwardingFilter
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

object GatewayApplication extends App {
  SpringApplication.run(classOf[GatewayConfiguration])
}

@SpringBootApplication
@EnableZuulProxy
class GatewayConfiguration {

  @Bean
  def forwardingFilter(): ForwardingFilter = new ForwardingFilter
}

