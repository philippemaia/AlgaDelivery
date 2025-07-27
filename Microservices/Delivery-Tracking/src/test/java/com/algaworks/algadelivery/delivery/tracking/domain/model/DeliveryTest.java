package com.algaworks.algadelivery.delivery.tracking.domain.model;

import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeToPlaced() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());

        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlace() {
        Delivery delivery = Delivery.draft();
        assertThrows(DomainException.class, () -> {
            delivery.place();
        });

        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }


    private Delivery.PreparationDetails createdValidPreparationDetails() {

        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua Rio de Janeiro")
                .number("100")
                .complement("sala 401")
                .name("João da Silva")
                .phone("(21) 99999-8888")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12345-000")
                .street("Avenida Brasil")
                .number("2000")
                .complement("sala 402")
                .name("Maria da Silva")
                .phone("(21) 99999-7777")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }

}