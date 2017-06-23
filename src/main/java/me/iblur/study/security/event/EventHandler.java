package me.iblur.study.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;

/**
 * @author 秦欣
 * @since 2017年06月23日 8:48.
 */
@Component
public class EventHandler {

    private final Logger logger = LoggerFactory.getLogger(EventHandler.class);

    @EventListener(value = AbstractAuthenticationEvent.class)
    public void handleAuthenticationEvent(final AbstractAuthenticationEvent authenticationEvent) {
        logger.info("认证事件：{}", authenticationEvent);
    }

}
