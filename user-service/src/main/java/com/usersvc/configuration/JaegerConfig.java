package com.usersvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.ReporterConfiguration;

@Configuration
public class JaegerConfig {
	
	@Bean
	public static JaegerTracer getTracer() {
	    SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType("const").withParam(1);
	    ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
	    io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("jaeger user-service").withSampler(samplerConfig).withReporter(reporterConfig);
	    return config.getTracer();
	}

}
