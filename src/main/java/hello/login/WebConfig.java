package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 어떤 필터
        filterRegistrationBean.setOrder(1); // 체인에서 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL에 적용할지 -> */ -> 모든 URL

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean LoginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 어떤 필터
        filterRegistrationBean.setOrder(2); // 체인에서 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 URL에 적용할지 -> */ -> 모든 URL

        return filterRegistrationBean;
    }
}
