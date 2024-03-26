package com.coffee.api.event;

import com.coffee.external.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreatedOrderEventListener {

    private ExternalService externalService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishCreatedOrder(CreatedOrderEvent createdOrderEvent) {
        externalService.send();
    }
}
