package com.liwm.sk.order.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    public static final String exchange = "sk.exchange";
    public static final String queue = "sk.order.queue";
    public static final String key = "sk.order";

    @Bean
    public DirectExchange seckillExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Queue seckillQueue() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", "dlx.exchange")
                .withArgument("x-dead-letter-routing-key", "dlx.route")
                .build();
    }
    //绑定队列和交换机
    @Bean
    public Binding seckillBinding(@Qualifier("seckillQueue") Queue queue,
                                  @Qualifier("seckillExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(key);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
