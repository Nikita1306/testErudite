package ru.naumen.testerudite;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricFilter implements Filter {

    @Autowired
    private MetricService metricService;

    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();
        String name = httpRequest.getParameter("name");
        if (httpRequest.getRequestURI().equals("/getAgeByName"))
            metricService.increaseCount(req, name);
        chain.doFilter(request, response);
    }
}
